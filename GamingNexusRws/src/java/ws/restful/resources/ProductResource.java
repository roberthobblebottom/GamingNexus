/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author yangxi
 */
@Path("Product")
public class ProductResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProductResource
     */
    public ProductResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.resources.ProductResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @Path("retrieveProductsByCategoryId")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProductsByCategoryId(@QueryParam("categoryId") Long categoryId) {
        try {
            List<Product> products = productSessionBean.retrieveProductByCategoryId(categoryId);

            for (Product product : products) {

                if (product.getCategory().getParentCategory() != null) {
                    product.getCategory().getParentCategory().getSubCategories().clear();
                }
                product.getCategory().getProducts().clear();
                for (Tag tagEntity : product.getTags()) {
                    tagEntity.getProducts().clear();
                }
                for (Promotion promotion : product.getPromotions()) {
                    promotion.getProducts().clear();
                }
                product.getCompany().getProducts().clear();
                for (Rating rating : product.getRatings()) {
                    rating.setProduct(null);
                }
                for (Forum forum : product.getForums()) {
                    forum.setProduct(null);
                }
                if (product instanceof Game) {
                    Game game = (Game) product;

                    for (GameAccount gameAccount : game.getGameAccounts()) {
                        gameAccount.setGame(null);
                    }
                } else if (product instanceof Hardware) {
                    Hardware hardware = (Hardware) product;
                    for (Deliverables deliverables : hardware.getDeliverables()) {
                        deliverables.setHardware(null);
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllProductsRsp(products)).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    /**
     * PUT method for updating or creating an instance of ProductResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
