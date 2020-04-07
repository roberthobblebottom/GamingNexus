/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import entity.Tag;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless
public class ProductSessionBean implements ProductSessionBeanLocal {

    @EJB
    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBean;

    @EJB
    private HardwareSessionBeanLocal hardwareSessionBean;

    @EJB
    private GameSessionBeanLocal gameSessionBean;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    @Override
    public Product retrieveProductById(Long productId) {
        Product retrievedProduct = (Product) em.find(Product.class, productId);
        if (retrievedProduct instanceof Game) {
            Game retrievedGame = (Game) em.find(Game.class, productId);
            this.lazyLoadGame(retrievedGame);
            return retrievedGame;
        } else if (retrievedProduct instanceof Hardware) {
            Hardware retrievedHardware = (Hardware) em.find(Hardware.class, productId);
            this.lazyLoadHardware(retrievedHardware);
            return retrievedHardware;
        } else if (retrievedProduct instanceof OtherSoftware) {
            OtherSoftware retrivedOtherSoftware = (OtherSoftware) em.find(OtherSoftware.class, productId);
            this.lazyLoadOtherSoftware(retrivedOtherSoftware);
            return retrivedOtherSoftware;
        } 
        assert false : "Product must always be a child entity";
        return null;
    }
//    @Override
//    public List<Tag> retrievedTagsByProduct(Long productID){
//     Product retrievedProduct = em.find(Product.class, productID);
//     retrievedProduct.getTags().size();
//     return retrievedProduct.get;
//             
//    }

    @Override
    public void deleteProduct(Product productToBeDeleted) {
        Game gameToBeDeleted;
        Hardware hardwareToBeDeleted;
        OtherSoftware otherSoftwareToBeDeleted;
        if (productToBeDeleted instanceof Game) {
            gameToBeDeleted = (Game) productToBeDeleted;
            em.remove(this.retrieveProductById(gameToBeDeleted.getProductId()));
        } else if (productToBeDeleted instanceof Hardware) {
            hardwareToBeDeleted = (Hardware) productToBeDeleted;
            em.remove(this.retrieveProductById(hardwareToBeDeleted.getProductId()));
        } else if (productToBeDeleted instanceof OtherSoftware) {
            otherSoftwareToBeDeleted = (OtherSoftware) productToBeDeleted;
            em.remove(this.retrieveProductById(otherSoftwareToBeDeleted.getProductId()));
        }
        em.flush();
    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void lazyLoadGame(Game game) {
        game.getTags().size();
        game.getCartItems().size();
        game.getOwnedItems().size();
        game.getPromotions().size();
        game.getRatings().size();
        game.getRatings().size();
    }

    public void lazyLoadOtherSoftware(OtherSoftware otherSoftware) {
        otherSoftware.getTags().size();
        otherSoftware.getCartItems().size();
        otherSoftware.getOwnedItems().size();
        otherSoftware.getPromotions().size();
        otherSoftware.getRatings().size();
        otherSoftware.getRatings().size();
    }

    public void lazyLoadHardware(Hardware hardware) {
        hardware.getTags().size();
        hardware.getCartItems().size();
        hardware.getOwnedItems().size();
        hardware.getPromotions().size();
        hardware.getRatings().size();
        hardware.getRatings().size();
    }
}
