/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.GameSessionBeanLocal;
import entity.Game;
import entity.Tag;
import java.util.List;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.model.ErrorRsp;
import ws.restful.model.RetrieveAllGamesRsp;

/**
 * REST Web Service
 *
 * @author chenli
 */
@Path("Game")
public class GameResource {

    @Context
    private UriInfo context;

    private GameSessionBeanLocal gameSessionBean = lookupGameSessionBeanLocal();

    /**
     * Creates a new instance of GameResource
     */
    public GameResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ws.restful.resources.GameResource
     *
     * @return an instance of java.lang.String
     */
    @Path("retrieveAllGames")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response RetrieveAllGames() {
        try {
            List<Game> games = gameSessionBean.retrieveAllGames();

            for(Game game: games)
            {
                if(game.getCategory().getParentCategory() != null)
                {
                    game.getCategory().getParentCategory().getSubCategories().clear();
                }
                
                game.getCategory().getProducts().clear();
                
                for(Tag tagEntity: game.getTags())
                {
                    tagEntity.getProducts().clear();
                }
            }
            
            RetrieveAllGamesRsp retrieveAllGamesRsp = new RetrieveAllGamesRsp(games);

            return Response.status(Status.OK).entity(retrieveAllGamesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of GameResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private GameSessionBeanLocal lookupGameSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (GameSessionBeanLocal) c.lookup("java:global/GamingNexus/GamingNexus-ejb/GameSessionBean!ejb.session.stateless.GameSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
