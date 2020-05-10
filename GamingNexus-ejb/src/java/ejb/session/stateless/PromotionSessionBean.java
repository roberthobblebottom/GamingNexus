/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Product;
import entity.Promotion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static javax.ws.rs.client.Entity.entity;
import util.exception.CompanyNotFoundException;
import util.exception.ProductNotFoundException;

/**
 *
 * @author root
 */
@Stateless
public class PromotionSessionBean implements PromotionSessionBeanLocal {
    
    @EJB
    private CompanySessionBeanLocal companySessionBean;
    
    @EJB
    private ProductSessionBeanLocal productSessionBean;
    
    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;
    
    @Override
    public Promotion createPromotion(Promotion promotion) {
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(promotion);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<Promotion>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Promotion> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                
            }
        }
        
        em.persist(promotion);
        em.flush();
        
        return promotion;
    }
    
    @Override
    public Promotion createPromotion(Promotion promotion, List<Long> listProductIDs) {
        
        System.out.println("********** createPromotion: " + listProductIDs);
        
        listProductIDs.forEach(id -> {
            Product productToBeAdded = em.find(Product.class, id);
            productToBeAdded.getPromotions().add(promotion);
            promotion.getProducts().add(productToBeAdded);
        });
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(promotion);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<Promotion>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Promotion> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                
            }
        }
        
        em.persist(promotion);
        em.flush();
        
        return promotion;
    }
    
    @Override
    public Promotion retrievePromotionById(long promotionID) {
        Promotion retrievedPromotion = em.find(Promotion.class, promotionID);
        return retrievedPromotion;
    }
    
    public List<Promotion> retrieveAllPromotions() {
        Query query = em.createQuery("SELECT p FROM Promotion p ");
        List<Promotion> promotions = query.getResultList();
        
        for (Promotion p : promotions) {
            lazyLoadPromotion(p);
        }
        
        return promotions;
    }
    
    @Override
    public List<Promotion> retrivePromotionsByCompanyID(long companyID) throws CompanyNotFoundException {
        System.out.println("***********Entered Promotion Session Bean retrieve Promotions By Company ID");
        Company company = null;
        try {
            company = companySessionBean.retrieveCompanyById(companyID);
        } catch (CompanyNotFoundException ex) {
            throw ex;
        }
        List<Product> companyProducts = company.getProducts();
        Set<Promotion> retrievedPromotions = new HashSet<>();
        companyProducts.forEach(product -> {
            
            retrievedPromotions.addAll(product.getPromotions());
        });
        System.out.println("Company : " + company.getUsername());
        
        return new ArrayList<>(retrievedPromotions);
    }
    
    @Override
    public void updatePromotion(Promotion promotion, List<Long> productsIdsListToBeUpdated) {
        Promotion promotionToBeUpdated = this.retrievePromotionById(promotion.getPromotionID());
        if (productsIdsListToBeUpdated != null && !productsIdsListToBeUpdated.isEmpty()) {
            List<Product> oldListOfProducts = promotionToBeUpdated.getProducts();            
            List<Product> newListOfProducts = new ArrayList<>();
            List<Product> listOfProductsToBeDeleted = new ArrayList<>();
            productsIdsListToBeUpdated.forEach(productId -> {
                Product productRetrieved = null;
                try {
                    productRetrieved = productSessionBean.retrieveProductById(productId);
                    
                    newListOfProducts.add(productRetrieved);
                } catch (ProductNotFoundException ex) {
                    System.out.println("**********product not found yyyaaa");
                }
            }); //populate new list of products.

            oldListOfProducts.forEach(oldProduct -> {
                if (!newListOfProducts.contains(oldProduct)) {
                    listOfProductsToBeDeleted.add(oldProduct);
                }
            });// populated list of products where the promotion to be updated need to be deleted from each of their entity

            listOfProductsToBeDeleted.forEach(productToBeDeleted -> {
                productToBeDeleted.getPromotions().remove(promotion);
            }); //the said removal process

            promotionToBeUpdated.setProducts(newListOfProducts);
        }
        
        if (productsIdsListToBeUpdated == null || productsIdsListToBeUpdated.isEmpty()) {
            promotionToBeUpdated.setProducts(new ArrayList<>());
        }
        
        promotionToBeUpdated.setName(promotion.getName());
        promotionToBeUpdated.setDescription(promotion.getDescription());
        promotionToBeUpdated.setDollarDiscount(promotion.getDollarDiscount());
        promotionToBeUpdated.setPercentageDiscount(promotion.getPercentageDiscount());
        promotionToBeUpdated.setStartDate(promotion.getStartDate());
        promotionToBeUpdated.setEndDate(promotion.getEndDate());
    }
    
    @Override
    public void deletePromotion(long promotionID) {
        Promotion promotionToBeDeleted = this.retrievePromotionById(promotionID);
        em.remove(promotionToBeDeleted);
        em.flush();
    }
    
    public void lazyLoadPromotion(Promotion promotion) {
        promotion.getProducts().size();
    }
}
