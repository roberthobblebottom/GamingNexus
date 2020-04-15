/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }

    @Path("customerLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerLogin(@QueryParam("username") String username, 
                                @QueryParam("password") String password)
    {
        try
        {
            Customer customer = customerSessionBeanLocal.customerLogin(username, password);
            System.out.println("********** CustomerResource.customerLogin(): Staff " + customer.getUsername() + " login remotely via web service");

            customer.setPassword(null);
            customer.setSalt(null);

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
}

