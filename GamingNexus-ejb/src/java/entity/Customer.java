/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
public class Customer extends User implements Serializable {

    @Size(min = 1, max = 100)
    private String currentGamePlaying;
    private boolean isBanned = false;
    @Future
    private Date unbanDate;
    @NotNull
    @Size(min = 5, max = 100)
    private String securityQuestion;
    @NotNull
    @Size(min = 5, max = 100)
    private String securityAnswer;
    @OneToMany(mappedBy = "Customer")
    private List<OwnedItem> ownedItems;
    @OneToOne(optional = false, mappedBy = "Customer")
    @JoinColumn(nullable = false)
    private ShoppingCart shoppingCart;
    @OneToMany
    private List<Rating> ratings;
    @OneToMany
    private List<Customer> customers;
    @OneToMany(mappedBy = "Customer")
    private List<GameAccount> gameAccounts;
    @OneToMany(mappedBy = "Customer")
    private List<Chat> chats;
    @OneToMany(mappedBy = "Customer")
    private List<Deliverables> listOfDeliverables;

    public Customer() {
        super();
    }

    public Customer(String currentGamePlaying, Date unbanDate, String securityQuestion, String securityAnswer, List<OwnedItem> ownedItems, ShoppingCart shoppingCart, List<Rating> ratings, List<Customer> customers, List<GameAccount> gameAccounts, List<Chat> chats, List<Deliverables> listOfDeliverables, int phoneNumber, String address, String email, String country, String username, String password, String profilePictureURL, Date lastOnline) {
        super(phoneNumber, address, email, country, username, password, profilePictureURL, lastOnline);
        this.currentGamePlaying = currentGamePlaying;
        this.unbanDate = unbanDate;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.ownedItems = ownedItems;
        this.shoppingCart = shoppingCart;
        this.ratings = ratings;
        this.customers = customers;
        this.gameAccounts = gameAccounts;
        this.chats = chats;
        this.listOfDeliverables = listOfDeliverables;
    }

 
    /**
     * @return the currentGamePlaying
     */
    public String getCurrentGamePlaying() {
        return currentGamePlaying;
    }

    /**
     * @param currentGamePlaying the currentGamePlaying to set
     */
    public void setCurrentGamePlaying(String currentGamePlaying) {
        this.currentGamePlaying = currentGamePlaying;
    }

    /**
     * @return the isBanned
     */
    public boolean isIsBanned() {
        return isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the unbanDate
     */
    public Date getUnbanDate() {
        return unbanDate;
    }

    /**
     * @param unbanDate the unbanDate to set
     */
    public void setUnbanDate(Date unbanDate) {
        this.unbanDate = unbanDate;
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * @return the securityAnswer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * @param securityAnswer the securityAnswer to set
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * @return the shoppingCart
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * @param shoppingCart the shoppingCart to set
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * @return the ratings
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * @return the ownedItems
     */
    public List<OwnedItem> getOwnedItems() {
        return ownedItems;
    }

    /**
     * @param ownedItems the ownedItems to set
     */
    public void setOwnedItems(List<OwnedItem> ownedItems) {
        this.ownedItems = ownedItems;
    }

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * @return the gameAccounts
     */
    public List<GameAccount> getGameAccounts() {
        return gameAccounts;
    }

    /**
     * @param gameAccounts the gameAccounts to set
     */
    public void setGameAccounts(List<GameAccount> gameAccounts) {
        this.gameAccounts = gameAccounts;
    }

    /**
     * @return the chats
     */
    public List<Chat> getChats() {
        return chats;
    }

    /**
     * @param chats the chats to set
     */
    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    /**
     * @return the listOfDeliverables
     */
    public List<Deliverables> getListOfDeliverables() {
        return listOfDeliverables;
    }

    /**
     * @param listOfDeliverables the listOfDeliverables to set
     */
    public void setListOfDeliverables(List<Deliverables> listOfDeliverables) {
        this.listOfDeliverables = listOfDeliverables;
    }
}
