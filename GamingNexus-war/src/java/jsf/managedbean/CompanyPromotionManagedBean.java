/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.HardwareSessionBeanLocal;
import ejb.session.stateless.OtherSoftwareSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import entity.Company;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import entity.Promotion;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CompanyNotFoundException;

/**
 *
 * @author root
 */
@Named(value = "companyPromotionManagedBean")
@ViewScoped
public class CompanyPromotionManagedBean implements Serializable {

    @EJB
    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBean;

    @EJB
    private HardwareSessionBeanLocal hardwareSessionBean;

    @EJB
    private GameSessionBeanLocal gameSessionBean;

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @Inject
    private ViewProductManagedBean viewProductManagedBean;

    private Game gameToViewInDetails = null;
    private Product productToViewInDetails;
    private Hardware hardwareToViewInDetails = null;
    private OtherSoftware otherSoftwareToViewInDetails = null;
    private Promotion newPromotion = null;
    private Date newPromotionStartDate = null,
            newPromotionEndDate = null, today = null;
    private List<Date> range = null;
    private List<Promotion> promotions, filteredPromotions;
    private List<Product> products, filteredProducts;
    private Company company;

    public CompanyPromotionManagedBean() {
        newPromotion = new Promotion();
        today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        range = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("**************Entered postConstruct of Company Promotion Managed Bean");
        Map<String, Object> sessionMap
                = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        setCompany((Company) sessionMap.get("company"));
        setProducts(getCompany().getProducts());
        try {
            promotions = promotionSessionBean.retrivePromotionsByCompanyID(company.getUserId());
        } catch (CompanyNotFoundException ex) {

        }
        if (promotions.isEmpty()) {
            System.out.println("**********promotions is empty");
        }
    }

    public void createNewPromotion(ActionEvent event) {
//          List<Long> productIds = new ArrayList<>();
//                    newPromotion.getProducts().forEach(product -> {
//                        productIds.add(tag.getTagId());
//                    });
    
        newPromotion.setStartDate(range.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        newPromotion.setEndDate(range.get(range.size()-1).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Promotion promotion = promotionSessionBean.createPromotion(newPromotion);
        promotions.add(promotion);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newPromotion.getName() + " added successfully "
                        + "(ID: " + newPromotion.getPromotionID() + ")", null));
        newPromotion = new Promotion();
    }

    public void updatePromotion(ActionEvent event) {
    }

    public void deletePromotion(ActionEvent event) {
        Promotion promotionToBeDeleted
                = (Promotion) event.getComponent().getAttributes().get("promotionToBeDeleted");
        promotionSessionBean.deletePromotion(promotionToBeDeleted.getPromotionID());
        promotions.remove(promotionToBeDeleted);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Prmotion deleted successfully. ID: " + promotionToBeDeleted.getPromotionID(), null));
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the otherSoftwareSessionBean
     */
    public OtherSoftwareSessionBeanLocal getOtherSoftwareSessionBean() {
        return otherSoftwareSessionBean;
    }

    /**
     * @param otherSoftwareSessionBean the otherSoftwareSessionBean to set
     */
    public void setOtherSoftwareSessionBean(OtherSoftwareSessionBeanLocal otherSoftwareSessionBean) {
        this.otherSoftwareSessionBean = otherSoftwareSessionBean;
    }

    /**
     * @return the hardwareSessionBean
     */
    public HardwareSessionBeanLocal getHardwareSessionBean() {
        return hardwareSessionBean;
    }

    /**
     * @param hardwareSessionBean the hardwareSessionBean to set
     */
    public void setHardwareSessionBean(HardwareSessionBeanLocal hardwareSessionBean) {
        this.hardwareSessionBean = hardwareSessionBean;
    }

    /**
     * @return the gameSessionBean
     */
    public GameSessionBeanLocal getGameSessionBean() {
        return gameSessionBean;
    }

    /**
     * @param gameSessionBean the gameSessionBean to set
     */
    public void setGameSessionBean(GameSessionBeanLocal gameSessionBean) {
        this.gameSessionBean = gameSessionBean;
    }

    /**
     * @return the productSessionBean
     */
    public ProductSessionBeanLocal getProductSessionBean() {
        return productSessionBean;
    }

    /**
     * @param productSessionBean the productSessionBean to set
     */
    public void setProductSessionBean(ProductSessionBeanLocal productSessionBean) {
        this.productSessionBean = productSessionBean;
    }

    /**
     * @return the promotionSessionBean
     */
    public PromotionSessionBeanLocal getPromotionSessionBean() {
        return promotionSessionBean;
    }

    /**
     * @param promotionSessionBean the promotionSessionBean to set
     */
    public void setPromotionSessionBean(PromotionSessionBeanLocal promotionSessionBean) {
        this.promotionSessionBean = promotionSessionBean;
    }

    /**
     * @return the viewProductManagedBean
     */
    public ViewProductManagedBean getViewProductManagedBean() {
        return viewProductManagedBean;
    }

    /**
     * @param viewProductManagedBean the viewProductManagedBean to set
     */
    public void setViewProductManagedBean(ViewProductManagedBean viewProductManagedBean) {
        this.viewProductManagedBean = viewProductManagedBean;
    }

    /**
     * @return the gameToViewInDetails
     */
    public Game getGameToViewInDetails() {
        return gameToViewInDetails;
    }

    /**
     * @param gameToViewInDetails the gameToViewInDetails to set
     */
    public void setGameToViewInDetails(Game gameToViewInDetails) {
        this.gameToViewInDetails = gameToViewInDetails;
    }

    /**
     * @return the productToViewInDetails
     */
    public Product getProductToViewInDetails() {
        return productToViewInDetails;
    }

    /**
     * @param productToViewInDetails the productToViewInDetails to set
     */
    public void setProductToViewInDetails(Product productToViewInDetails) {
        this.productToViewInDetails = productToViewInDetails;
    }

    /**
     * @return the hardwareToViewInDetails
     */
    public Hardware getHardwareToViewInDetails() {
        return hardwareToViewInDetails;
    }

    /**
     * @param hardwareToViewInDetails the hardwareToViewInDetails to set
     */
    public void setHardwareToViewInDetails(Hardware hardwareToViewInDetails) {
        this.hardwareToViewInDetails = hardwareToViewInDetails;
    }

    /**
     * @return the otherSoftwareToViewInDetails
     */
    public OtherSoftware getOtherSoftwareToViewInDetails() {
        return otherSoftwareToViewInDetails;
    }

    /**
     * @param otherSoftwareToViewInDetails the otherSoftwareToViewInDetails to
     * set
     */
    public void setOtherSoftwareToViewInDetails(OtherSoftware otherSoftwareToViewInDetails) {
        this.otherSoftwareToViewInDetails = otherSoftwareToViewInDetails;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * @return the filteredProducts
     */
    public List<Product> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * @param filteredProducts the filteredProducts to set
     */
    public void setFilteredProducts(List<Product> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    /**
     * @return the promotions
     */
    public List<Promotion> getPromotions() {
        return promotions;
    }

    /**
     * @param promotions the promotions to set
     */
    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    /**
     * @return the filteredPromotions
     */
    public List<Promotion> getFilteredPromotions() {
        return filteredPromotions;
    }

    /**
     * @param filteredPromotions the filteredPromotions to set
     */
    public void setFilteredPromotions(List<Promotion> filteredPromotions) {
        this.filteredPromotions = filteredPromotions;
    }

    /**
     * @return the newPromotion
     */
    public Promotion getNewPromotion() {
        return newPromotion;
    }

    /**
     * @param newPromotion the newPromotion to set
     */
    public void setNewPromotion(Promotion newPromotion) {
        this.newPromotion = newPromotion;
    }

    /**
     * @return the newPromotionStartDate
     */
    public Date getNewPromotionStartDate() {
        return newPromotionStartDate;
    }

    /**
     * @param newPromotionStartDate the newPromotionStartDate to set
     */
    public void setNewPromotionStartDate(Date newPromotionStartDate) {
        this.newPromotionStartDate = newPromotionStartDate;
        this.newPromotion.setStartDate(
                LocalDateTime.ofInstant(newPromotionStartDate.toInstant(), ZoneId.systemDefault())
        );
    }

    /**
     * @return the newPromotionEndDate
     */
    public Date getNewPromotionEndDate() {
        return newPromotionEndDate;
    }

    /**
     * @param newPromotionEndDate the newPromotionEndDate to set
     */
    public void setNewPromotionEndDate(Date newPromotionEndDate) {
        this.newPromotionEndDate = newPromotionEndDate;
        this.newPromotion.setEndDate(
                LocalDateTime.ofInstant(newPromotionEndDate.toInstant(), ZoneId.systemDefault())
        );
    }

    /**
     * @return the today
     */
    public Date getToday() {
        return today;
    }

    /**
     * @param today the today to set
     */
    public void setToday(Date today) {
        this.today = today;
    }

    /**
     * @return the range
     */
    public List<Date> getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(List<Date> range) {
        this.range = range;
    }

};
