/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.SaleTransactionSessionBeanLocal;
import entity.Customer;
import entity.Product;
import entity.SaleTransaction;
import entity.SaleTransactionLineItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import util.exception.CreateNewSaleTransactionException;
import util.exception.CustomerNotFoundException;
import util.exception.ProductNotFoundException;
import ws.restful.helperClass.ProductAndQuantity;
import ws.restful.model.CreateSaleTransactionReq;

/**
 * REST Web Service
 *
 * @author ufoya
 */
@Path("SaleTransaction")
public class SaleTransactionResource {

    CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();

    SaleTransactionSessionBeanLocal saleTransactionSessionBean = lookupSaleTransactionSessionBeanLocal();

    ProductSessionBeanLocal productSessionBean = lookupProductSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SaleTransactionResource
     */
    public SaleTransactionResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.SaleTransactionResource
     *
     * @return an instance of java.lang.String
     */
    /**
     * PUT method for updating or creating an instance of
     * SaleTransactionResource
     *
     * @param content representation for the resource
     */
    @Path("createSaleTransaction")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void createSaleTransation(CreateSaleTransactionReq createSaleTransactionReq) {

        System.out.println("********** createSaleTransation() is successfully invoked");
        //System.out.println("*****" + createSaleTransactionReq.getUsername());
        //System.out.println("*****" + createSaleTransactionReq.getPassword());
        

        List<SaleTransactionLineItem> saleTransactionLineItems = new ArrayList<>();
                
        try {
            Customer c = customerSessionBean.retrieveCustomerByUsername(createSaleTransactionReq.getUsername());
            Integer totalLineItem = new Integer(createSaleTransactionReq.getList().size());
            
            Integer totalQuantity = 0;
            BigDecimal totalAmount = BigDecimal.valueOf(0);
            
            for (ProductAndQuantity productAndQuantity : createSaleTransactionReq.getList()) {
                //System.out.println("Product Id" + productAndQuantity.getProductId());
                //System.out.println("Quantity" + productAndQuantity.getQuantity());      
                
                Product product = productSessionBean.retrieveProductById(productAndQuantity.getProductId());
                BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
                BigDecimal subTotal = BigDecimal.valueOf(product.getPrice() * productAndQuantity.getQuantity());
                SaleTransactionLineItem newSaleTransactionLineItem = new SaleTransactionLineItem(product, productAndQuantity.getQuantity(), unitPrice, subTotal);
                saleTransactionLineItems.add(newSaleTransactionLineItem);
                
                totalQuantity += productAndQuantity.getQuantity();
                totalAmount.add(subTotal);
            }
            SaleTransaction newSaleTransaction = new SaleTransaction(totalLineItem, totalQuantity, totalAmount, LocalDateTime.now(), saleTransactionLineItems, false);
            SaleTransaction s = saleTransactionSessionBean.createNewSaleTransaction(c.getUserId(), newSaleTransaction);
            
        } catch (ProductNotFoundException | CustomerNotFoundException | CreateNewSaleTransactionException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private ProductSessionBeanLocal lookupProductSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProductSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/ProductSessionBean!ejb.session.stateless.ProductSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private SaleTransactionSessionBeanLocal lookupSaleTransactionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SaleTransactionSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/SaleTransactionSessionBean!ejb.session.stateless.SaleTransactionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
