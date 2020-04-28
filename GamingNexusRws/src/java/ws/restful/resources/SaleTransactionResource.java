/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.SaleTransactionSessionBeanLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.helperClass.ProductAndQuantity;
import ws.restful.model.CreateSaleTransationReq;

/**
 * REST Web Service
 *
 * @author ufoya
 */
@Path("SaleTransaction")
public class SaleTransactionResource {

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
    @Path("createSaleTransation")
    @PUT    
    @Consumes(MediaType.APPLICATION_JSON)
    public void createSaleTransation(CreateSaleTransationReq createSaleTransationReq) {
        
        System.out.println("********** createSaleTransation() is successfully invoked");
        System.out.println("*****"+ createSaleTransationReq.getUsername());
        System.out.println("*****"+ createSaleTransationReq.getPassword());
        
        for(ProductAndQuantity productAndQuantity: createSaleTransationReq.getList()) {
             System.out.println("Product Id"+ productAndQuantity.getProductId());
             System.out.println("Quantity"+ productAndQuantity.getQuantity());
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
}
