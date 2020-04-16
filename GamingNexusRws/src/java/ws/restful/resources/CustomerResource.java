/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;
import ws.restful.model.CustomerLoginRsp;
import ws.restful.model.ErrorRsp;

/**
 * REST Web Service
 *
 * @author ufoya
 */
@Path("Customer")
public class CustomerResource {

    @Context
    private UriInfo context;
    private CustomerSessionBeanLocal customerSessionBean = lookupCustomerSessionBeanLocal();
    
    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }
    /**
     * Retrieves representation of an instance of ws.restful.resources.CustomerResource
     * @return an instance of java.lang.String
     */
    @Path("customerLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerLogin(@QueryParam("username") String username, 
                                @QueryParam("password") String password)
    {
        try
        {
            Customer customer = lookupCustomerSessionBeanLocal().customerLogin(username, password);
                    
            System.out.println("********** CustomerResource.customerLogin(): customer " + customer.getUsername() + " login remotely via web service");
    
            return Response.status(Status.OK).entity(new CustomerLoginRsp(customer)).build();
        }
        catch(InvalidLoginCredentialException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    /**
     * PUT method for updating or creating an instance of CustomerResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
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
