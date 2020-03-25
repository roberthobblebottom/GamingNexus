/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CompanySessionBeanLocal;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author root
 */
@Named(value = "companyProductManagedBean")
@SessionScoped
public class CompanyProductManagedBean implements Serializable {

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    private Product newGame,
            gameToUpdate,
            newHardware,
            hardwareToUpdate,
            newOtherSoftware,
            otherSoftwareToUpdate;
    
    private List<Product> products;

    public CompanyProductManagedBean() {
        newGame = new Game();
        newHardware = new Hardware();
        newOtherSoftware = new OtherSoftware();
        
    }
    
    @PostConstruct
    public void postConstruct(){
        FacesContext.getCurrentInstance().getExternalContext()
    }

    /**
     * @return the gameToUpdate
     */
    public Product getGameToUpdate() {
        return gameToUpdate;
    }

    /**
     * @param gameToUpdate the gameToUpdate to set
     */
    public void setGameToUpdate(Product gameToUpdate) {
        this.gameToUpdate = gameToUpdate;
    }

    /**
     * @return the newHardware
     */
    public Product getNewHardware() {
        return newHardware;
    }

    /**
     * @param newHardware the newHardware to set
     */
    public void setNewHardware(Product newHardware) {
        this.newHardware = newHardware;
    }

    /**
     * @return the hardwareToUpdate
     */
    public Product getHardwareToUpdate() {
        return hardwareToUpdate;
    }

    /**
     * @param hardwareToUpdate the hardwareToUpdate to set
     */
    public void setHardwareToUpdate(Product hardwareToUpdate) {
        this.hardwareToUpdate = hardwareToUpdate;
    }

    /**
     * @return the newOtherSoftware
     */
    public Product getNewOtherSoftware() {
        return newOtherSoftware;
    }

    /**
     * @param newOtherSoftware the newOtherSoftware to set
     */
    public void setNewOtherSoftware(Product newOtherSoftware) {
        this.newOtherSoftware = newOtherSoftware;
    }

    /**
     * @return the otherSoftwareToUpdate
     */
    public Product getOtherSoftwareToUpdate() {
        return otherSoftwareToUpdate;
    }

    /**
     * @param otherSoftwareToUpdate the otherSoftwareToUpdate to set
     */
    public void setOtherSoftwareToUpdate(Product otherSoftwareToUpdate) {
        this.otherSoftwareToUpdate = otherSoftwareToUpdate;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * @return the newGame
     */
    public Product getNewGame() {
        return newGame;
    }

    /**
     * @param newGame the newGame to set
     */
    public void setNewGame(Product newGame) {
        this.newGame = newGame;
    }

}
