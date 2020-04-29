/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import entity.Customer;
import entity.Product;
import entity.SaleTransaction;
import entity.SaleTransactionLineItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jinyichen
 */
@Stateless
public class SaleTransactionSessionBean implements SaleTransactionSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public SaleTransactionSessionBean() {
    }

    @Override
    public SaleTransaction createNewSaleTransaction(Long customerId, SaleTransaction newSaleTransaction) throws CustomerNotFoundException, CreateNewSaleTransactionException {
        if (newSaleTransaction != null) {

            Customer customer = customerSessionBeanLocal.retrieveCustomerById(customerId);
            newSaleTransaction.setCustomer(customer);
            customer.getSaleTransactions().add(newSaleTransaction);
            em.persist(newSaleTransaction);

            for (SaleTransactionLineItem saleTransactionLineItem : newSaleTransaction.getSaleTransactionLineItems()) {
                
                em.persist(saleTransactionLineItem);
            }

            em.flush();

            return newSaleTransaction;

        } else {
            throw new CreateNewSaleTransactionException("Sale transaction information not provided");
        }
    }
    
    
    
    
    @Override
    public List<SaleTransaction> retrieveAllSaleTransactions()
    {
        Query query = em.createQuery("SELECT st FROM SaleTransaction st");
        
        return query.getResultList();
    }

    
    
    

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    
    

    
}
