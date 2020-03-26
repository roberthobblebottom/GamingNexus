/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Category;
import entity.Company;
import entity.Game;
import entity.Product;
import entity.Tag;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author root
 */
@Named(value = "companyProductManagedBean")
@SessionScoped
public class CompanyProductManagedBean implements Serializable {

    @EJB
    private GameSessionBeanLocal gameSessionBean;

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    @EJB
    private TagSessionBeanLocal tagSessionBean;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    private Game newGame, gameToBeUpdated;
    private List<Product> products, filteredProducts;
    private List<Category> categories;
    private List<Tag> tags;
    private Company company;

    public CompanyProductManagedBean() {
        newGame = new Game();
        gameToBeUpdated = new Game();
    }

    @PostConstruct
    public void postConstruct() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        company = (Company) sessionMap.get("company");
        Company retrievedCompany = null;
        try {
            retrievedCompany = companySessionBeanLocal.retrieveCompanyById(company.getUserId());
        } catch (CompanyNotFoundException ex) {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while retrieving company products" + ex.getMessage(), null));
              
        }
        this.setProducts(retrievedCompany.getProducts());

        categories = categorySessionBean.retrieveAllCategories();
        tags = tagSessionBean.retrieveAllTags();
    }

    public void viewSystemAdminDetails(ActionEvent event) throws IOException {
        Long systemAdminIdToView = (Long) event.getComponent().getAttributes().get("systemAdminId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("systemAdminIdToView", systemAdminIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewSystemAdminDetails.xhtml");
    }

    public void createNewSystemAdmin(ActionEvent event) throws SystemAdminUsernameExistException {

        String buttonID = event.getComponent().getId();

        switch (buttonID) {
            case "AddGameButton":
                try {
                    List<Long> tagIds = new ArrayList<>();
                    newGame.getTags().forEach(tag -> {
                        tagIds.add(tag.getTagId());
                    });
                    Game game = gameSessionBean.createNewGame(newGame, newGame.getCategory().getCategoryId(), tagIds, company.getUserId());
                    products.add((Product) game);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newGame.getName() + " added successfully "
                                    + "(ID: " + game.getProductId() + ")", null));
                    newGame = new Game();

                } catch (UnknownPersistenceException | ProductSkuCodeExistException | InputDataValidationException | CreateNewProductException
                        | CompanyNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while creating a new system admin  " + ex.getMessage(), null));
                }
                break;
            case "AddSoftwareButton":
                break;
            case "AddHardwareButton":
                break;
            default:
                break;

        }

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
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the newGame
     */
    public Game getNewGame() {
        return newGame;
    }

    /**
     * @param newGame the newGame to set
     */
    public void setNewGame(Game newGame) {
        this.newGame = newGame;
    }

    /**
     * @return the gameToBeUpdated
     */
    public Game getGameToBeUpdated() {
        return gameToBeUpdated;
    }

    /**
     * @param gameToBeUpdated the gameToBeUpdated to set
     */
    public void setGameToBeUpdated(Game gameToBeUpdated) {
        this.gameToBeUpdated = gameToBeUpdated;
    }

    /**
     * @return the filteredProducts
     */
    public List<Product> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * @param filteredProducts the filteredProducts to set
     */
    public void setFilteredProducts(List<Product> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

}
