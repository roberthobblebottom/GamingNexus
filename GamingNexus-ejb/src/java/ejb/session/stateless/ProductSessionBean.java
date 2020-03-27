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
            return (Game) em.find(Game.class, productId);
        } else if (retrievedProduct instanceof Hardware) {
            return (Hardware) em.find(Hardware.class, productId);
        } else if (retrievedProduct instanceof OtherSoftware) {
            return (OtherSoftware) em.find(OtherSoftware.class, productId);
        }
        return null;
    }

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
}
