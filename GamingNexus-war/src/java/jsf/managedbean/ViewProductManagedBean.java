/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author root
 */
@Named(value = "viewProductManagedBean")
@ViewScoped
public class ViewProductManagedBean implements Serializable {

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    /**
     * Creates a new instance of ViewProductManagedBean
     */
    private Product productToViewInDetails;
    private Game gameToViewInDetails = null;
    private Hardware hardwareToViewInDetails = null;
    private OtherSoftware otherSoftwareToViewInDetails = null;

    public ViewProductManagedBean() {

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
        if (productToViewInDetails instanceof Game) {
            System.out.println("**********setProductToViewInDetails game if block");

            setGameToViewInDetails((Game) productToViewInDetails);
            setHardwareToViewInDetails(null);
            setOtherSoftwareToViewInDetails(null);
        } else if (productToViewInDetails instanceof Hardware) {
            System.out.println("**********setProductToViewInDetails hardware if block");

            setHardwareToViewInDetails((Hardware) productToViewInDetails);
            setGameToViewInDetails(null);
            setOtherSoftwareToViewInDetails(null);

        } else if (productToViewInDetails instanceof OtherSoftware) {
            System.out.println("**********setProductToViewInDetails othersoftware if block");

            setOtherSoftwareToViewInDetails((OtherSoftware) productToViewInDetails);
            setHardwareToViewInDetails(null);
            setGameToViewInDetails(null);

        }
        assert false:"productToViewInDetails should be always an instance of a child class";
        

    }

// 
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

}
