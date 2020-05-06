/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.HardwareSessionBeanLocal;
import ejb.session.stateless.OtherSoftwareSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Category;
import entity.Company;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import entity.Promotion;
import entity.Tag;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

import java.util.Date;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import util.enumeration.ParentAdvisory;

/**
 *
 * @author root
 */
@Named(value = "companyProductManagedBean")
@ViewScoped
public class CompanyProductManagedBean implements Serializable {

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @EJB
    private HardwareSessionBeanLocal hardwareSessionBean;

    @EJB
    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBean;

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    @EJB
    private GameSessionBeanLocal gameSessionBean;

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    @EJB
    private TagSessionBeanLocal tagSessionBean;

    @Inject
    private ViewProductManagedBean viewProductManagedBean;
    private Company company;

    private Product productToViewInDetails, selectedProductToUpdate;
    private Game newGame, gameToBeUpdated, gameToViewInDetails = null;
    private Hardware newHardware, hardwareToViewInDetails = null;
    private OtherSoftware newOtherSoftware, otherSoftwareToViewInDetails = null;
    private Date newReleaseDate = null;

    private List<Product> listOfAllCompanysProducts, filteredProducts;
    private List<Category> listOfAllCategories;
    private List<Promotion> listOfAllCompanyPromotions;
    private List<Tag> tags;

    private Long categoryIdUpdate;
    private List<Long> tagIdsUpdate;
    private Date releaseDateToBeUpdated;

    private ParentAdvisory[] allPossibleAdvisories;

    private UploadedFile uploadedFile;

    public CompanyProductManagedBean() {
        newGame = new Game();
        gameToBeUpdated = new Game();
        allPossibleAdvisories = ParentAdvisory.values();
        newReleaseDate = new Date(System.currentTimeMillis());
        releaseDateToBeUpdated = new Date(System.currentTimeMillis());
    }

    @PostConstruct
    public void postConstruct() {
        uploadedFile = null;
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        setCompany((Company) sessionMap.get("company"));
        try {
            listOfAllCompanyPromotions = promotionSessionBean.retrivePromotionsByCompanyID(getCompany().getUserId());
        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An error has occurred while fetching company's promotions: " + ex.getMessage(), null));

        }

        listOfAllCompanysProducts = getCompany().getProducts();
        newOtherSoftware = new OtherSoftware();
        newOtherSoftware.setTags(new ArrayList<>());
        setNewHardware(new Hardware());
        getNewHardware().setTags(new ArrayList<>());
        listOfAllCategories = categorySessionBean.retrieveAllCategories();
        tags = tagSessionBean.retrieveAllTags();
    }

    public void viewProductDetailsMethod(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("productIdToView", productIdToView);
    }

    public void createNewProduct(ActionEvent event) throws SystemAdminUsernameExistException {

        String buttonID = event.getComponent().getId();

        switch (buttonID) {
            case "AddGameButton":
                try {
                    List<Long> tagIds = new ArrayList<>();
                    newGame.getTags().forEach(tag -> {
                        tagIds.add(tag.getTagId());
                    });

                    newGame.setReleaseDate(this.convertToLocalDateViaInstant(newReleaseDate));

                    Game game = gameSessionBean.createNewGame(newGame, newGame.getCategory().getCategoryId(), tagIds, getCompany().getUserId());
                    listOfAllCompanysProducts.add((Product) game);
                    newGame = new Game();
                    newReleaseDate = new Date(System.currentTimeMillis());
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newGame.getName() + " added successfully "
                                    + "(ID: " + game.getProductId() + ")", null));
                } catch (UnknownPersistenceException | ProductSkuCodeExistException | InputDataValidationException | CreateNewProductException
                        | CompanyNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while creating a new game " + ex.getMessage(), null));
                }
                break;
            case "AddOtherSoftwareButton":
                try {
                    List<Long> tagIds = new ArrayList<>();
                    getNewOtherSoftware().getTags().forEach(tag -> {
                        tagIds.add(tag.getTagId());
                    });
                    newOtherSoftware.setReleaseDate(this.convertToLocalDateViaInstant(newReleaseDate));

                    OtherSoftware otherSoftware = otherSoftwareSessionBean.createNewOtherSoftware(newOtherSoftware,
                            newOtherSoftware.getCategory().getCategoryId(), tagIds, getCompany().getUserId());
                    listOfAllCompanysProducts.add((Product) otherSoftware);
                    newOtherSoftware = new OtherSoftware();
                    newReleaseDate = new Date(System.currentTimeMillis());

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newGame.getName() + " added successfully "
                                    + "(ID: " + otherSoftware.getProductId() + ")", null));

                } catch (UnknownPersistenceException | ProductSkuCodeExistException | InputDataValidationException | CreateNewProductException
                        | CompanyNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while creating a new other software  " + ex.getMessage(), null));
                }
                break;
            case "AddHardwareButton":
                try {
                    List<Long> tagIds = new ArrayList<>();
                    getNewHardware().getTags().forEach(tag -> {
                        tagIds.add(tag.getTagId());
                    });

                    newHardware.setReleaseDate(this.convertToLocalDateViaInstant(newReleaseDate));
                    Hardware hardware = hardwareSessionBean.createNewHardware(newHardware,
                            newHardware.getCategory().getCategoryId(), tagIds, getCompany().getUserId());
                    listOfAllCompanysProducts.add((Product) hardware);
                    newHardware = new Hardware();
                    newReleaseDate = new Date(System.currentTimeMillis());

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "New Game " + newGame.getName() + " added successfully "
                                    + "(ID: " + newHardware.getProductId() + ")", null));

                } catch (UnknownPersistenceException | ProductSkuCodeExistException | InputDataValidationException | CreateNewProductException
                        | CompanyNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "An error has occurred while creating a new other software  " + ex.getMessage(), null));
                }
                break;
            default:
                break;

        }

    }
//

    public void doUpdateProduct(ActionEvent event) {
        // setSelectedProductToUpdate((Product)event.getComponent().getAttributes().get("productEntityToUpdate"));
//
        Product productToBeUpdated = (Product) event.getComponent().getAttributes().get("productToBeUpdated");
        this.viewProductManagedBean.setProductToViewInDetails(productToBeUpdated);
        this.releaseDateToBeUpdated = this.convertToDateViaInstant(this.viewProductManagedBean.getProductToViewInDetails().getReleaseDate());
    }

    public void updateProduct(ActionEvent event) {

        Product productToBeUpdated = viewProductManagedBean.getProductToViewInDetails();
        productToBeUpdated.setReleaseDate(this.convertToLocalDateViaInstant(this.releaseDateToBeUpdated));

        Hardware hardwareEntityFragment = viewProductManagedBean.getHardwareToViewInDetails();
        OtherSoftware otherSoftwareEntityFragment = viewProductManagedBean.getOtherSoftwareToViewInDetails();
        Game gameEntityFragment = viewProductManagedBean.getGameToViewInDetails();

        categoryIdUpdate = viewProductManagedBean.getProductToViewInDetails().getCategory().getCategoryId();
        try {
            if (gameToBeUpdated != null
                    && otherSoftwareEntityFragment == null
                    && hardwareEntityFragment == null) {
                Game gameToBeUpdated = (Game) productToBeUpdated;
                gameToBeUpdated.setParentAdvisory(gameEntityFragment.getParentAdvisory());
                gameToBeUpdated.setForums(gameEntityFragment.getForums());
                gameSessionBean.updateGame(gameToBeUpdated, getCategoryIdUpdate(), getTagIdsUpdate());
            } else if (viewProductManagedBean.getGameToViewInDetails() == null
                    && otherSoftwareEntityFragment != null
                    && hardwareEntityFragment == null) {
                OtherSoftware otherSoftwareToBeUpdated = (OtherSoftware) productToBeUpdated;
                otherSoftwareSessionBean.updateOtherSoftware(otherSoftwareToBeUpdated, getCategoryIdUpdate(), getTagIdsUpdate());
            } else if (viewProductManagedBean.getGameToViewInDetails() == null
                    && otherSoftwareEntityFragment == null
                    && hardwareEntityFragment != null) {
                Hardware hardwareToBeUpdated = (Hardware) productToBeUpdated;
                hardwareToBeUpdated.setDeliverables(hardwareEntityFragment.getDeliverables());
                hardwareToBeUpdated.setManufacturingCountry(hardwareEntityFragment.getManufacturingCountry());
                hardwareToBeUpdated.setTechnicalspecification(hardwareEntityFragment.getTechnicalspecification());
                hardwareToBeUpdated.setWarrantyDescription(hardwareEntityFragment.getWarrantyDescription());
                hardwareSessionBean.updateHardware(hardwareToBeUpdated, getCategoryIdUpdate(), getTagIdsUpdate());
            }

            for (Category ce : listOfAllCategories) {
                if (ce.getCategoryId().equals(getCategoryIdUpdate())) {
                    viewProductManagedBean.getProductToViewInDetails().setCategory(ce);
                    break;
                }
            }

            productToBeUpdated.getTags().clear();
            if (!tagIdsUpdate.isEmpty()) {
                System.out.println("!tagidsupdate.isempty block entered.");
                tags.forEach(tag -> {
                    if (getTagIdsUpdate().contains(tag.getTagId())) {
                        viewProductManagedBean.getProductToViewInDetails().getTags().add(tag);
                    }
                });
            }

        } catch (ProductNotFoundException | CategoryNotFoundException | TagNotFoundException
                | UpdateProductException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error for updating productEntity (ID: " + viewProductManagedBean.getProductToViewInDetails().getProductId() + ") "
                    + "Error Message: " + ex.getMessage(), null));
        }

        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully", null));
        //   viewProductManagedBean.resetManageBean();
    }

    public void deleteProduct(ActionEvent event) {
        try {
            Product productToBeDeleted = (Product) event.getComponent().getAttributes().get("productToBeDeleted");
            productSessionBean.deleteProduct(productToBeDeleted);
            listOfAllCompanysProducts.remove(productToBeDeleted);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Product deleted successfully ID: " + productToBeDeleted.getProductId(), null));
        } catch (ProductNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void uploadPhoto(FileUploadEvent e) throws IOException {

        UploadedFile uploadedPhoto = e.getFile();

        String filePath = "/home/ryan/Documents/IS3106/GamingNexus/GamingNexus-war/web/resources/images";
        byte[] bytes = null;

        if (null != uploadedPhoto) {
            bytes = uploadedPhoto.getContents();
            String filename = FilenameUtils.getName(uploadedPhoto.getFileName());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + filename)));
            stream.write(bytes);
            stream.close();
        }

        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Your Photo (File Name " + uploadedPhoto.getFileName() + " with size " + uploadedPhoto.getSize() + ")  Uploaded Successfully", ""));
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * @return the listOfAllCompanysProducts
     */
    public List<Product> getListOfAllCompanysProducts() {
        return listOfAllCompanysProducts;
    }

    /**
     * @param listOfAllCompanysProducts the listOfAllCompanysProducts to set
     */
    public void setListOfAllCompanysProducts(List<Product> listOfAllCompanysProducts) {
        this.listOfAllCompanysProducts = listOfAllCompanysProducts;
    }

    /**
     * @return the listOfAllCategories
     */
    public List<Category> getListOfAllCategories() {
        return listOfAllCategories;
    }

    /**
     * @param listOfAllCategories the listOfAllCategories to set
     */
    public void setListOfAllCategories(List<Category> listOfAllCategories) {
        this.listOfAllCategories = listOfAllCategories;
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
    }

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

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the viewProductManagedBean
     */
    public ViewProductManagedBean getViewProductManagedBean() {
        return viewProductManagedBean;
    }

    /**
     * @param viewProductManagedBean the viewProductManagedBean to set
     */
    public void setViewProductManagedBean(ViewProductManagedBean viewProductManagedBean) {
        this.viewProductManagedBean = viewProductManagedBean;
    }

    /**
     * @return the newOtherSoftware
     */
    public OtherSoftware getNewOtherSoftware() {
        return newOtherSoftware;
    }

    /**
     * @param newOtherSoftware the newOtherSoftware to set
     */
    public void setNewOtherSoftware(OtherSoftware newOtherSoftware) {
        this.newOtherSoftware = newOtherSoftware;
    }

    /**
     * @return the hardwareSessionBean
     */
    public HardwareSessionBeanLocal getHardwareSessionBean() {
        return hardwareSessionBean;
    }

    /**
     * @param hardwareSessionBean the hardwareSessionBean to set
     */
    public void setHardwareSessionBean(HardwareSessionBeanLocal hardwareSessionBean) {
        this.hardwareSessionBean = hardwareSessionBean;
    }

    /**
     * @return the newHardware
     */
    public Hardware getNewHardware() {
        return newHardware;
    }

    /**
     * @param newHardware the newHardware to set
     */
    public void setNewHardware(Hardware newHardware) {
        this.newHardware = newHardware;
    }

    /**
     * @return the selectedProductToUpdate
     */
    public Product getSelectedProductToUpdate() {
        return selectedProductToUpdate;
    }

    /**
     * @param selectedProductToUpdate the selectedProductToUpdate to set
     */
    public void setSelectedProductToUpdate(Product selectedProductToUpdate) {
        this.selectedProductToUpdate = selectedProductToUpdate;
    }

    /**
     * @return the categoryIdUpdate
     */
    public Long getCategoryIdUpdate() {
        return categoryIdUpdate;
    }

    /**
     * @param categoryIdUpdate the categoryIdUpdate to set
     */
    public void setCategoryIdUpdate(Long categoryIdUpdate) {
        this.categoryIdUpdate = categoryIdUpdate;
    }

    /**
     * @return the tagIdsUpdate
     */
    public List<Long> getTagIdsUpdate() {
        return tagIdsUpdate;
    }

    /**
     * @param tagIdsUpdate the tagIdsUpdate to set
     */
    public void setTagIdsUpdate(List<Long> tagIdsUpdate) {
        this.tagIdsUpdate = tagIdsUpdate;
    }

    /**
     * @return the allPossibleAdvisories
     */
    public ParentAdvisory[] getAllPossibleAdvisories() {
        return allPossibleAdvisories;
    }

    /**
     * @param allPossibleAdvisories the allPossibleAdvisories to set
     */
    public void setAllPossibleAdvisories(ParentAdvisory[] allPossibleAdvisories) {
        this.allPossibleAdvisories = allPossibleAdvisories;
    }

    /**
     * @return the listOfAllCompanyPromotions
     */
    public List<Promotion> getListOfAllCompanyPromotions() {
        return listOfAllCompanyPromotions;
    }

    /**
     * @param listOfAllCompanyPromotions the listOfAllCompanyPromotions to set
     */
    public void setListOfAllCompanyPromotions(List<Promotion> listOfAllCompanyPromotions) {
        this.listOfAllCompanyPromotions = listOfAllCompanyPromotions;
    }

    /**
     * @return the uploadedFile
     */
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /**
     * @return the newReleaseDate
     */
    public Date getNewReleaseDate() {
        return newReleaseDate;
    }

    /**
     * @param newReleaseDate the newReleaseDate to set
     */
    public void setNewReleaseDate(Date newReleaseDate) {
        this.newReleaseDate = newReleaseDate;
    }

    /**
     * @return the releaseDateToBeUpdated
     */
    public Date getReleaseDateToBeUpdated() {
        return releaseDateToBeUpdated;
    }

    /**
     * @param releaseDateToBeUpdated the releaseDateToBeUpdated to set
     */
    public void setReleaseDateToBeUpdated(Date releaseDateToBeUpdated) {
        this.releaseDateToBeUpdated = releaseDateToBeUpdated;
    }

}
