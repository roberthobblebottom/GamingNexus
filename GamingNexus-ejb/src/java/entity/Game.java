/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
public class Game extends Product implements Serializable {

    @NotNull
    @Size(min = 1, max = 5)
    private String parentAdvisory;
    private List<String> gamePicturesURLs;
    private List<String> gameTrailersURLS;

    @OneToMany(mappedBy = "Game")
    private List<GameAccount> gameAccounts;
    @OneToMany(mappedBy = "Game")
    private List<Forum> forums;

    public Game() {
        super();
    }

    public Game(String parentAdvisory, List<String> gamePicturesURLs, List<String> gameTrailersURLS, List<GameAccount> gameAccounts, List<Forum> forums, String name, String description, String computerRequirements, double price, double averageRating, Company company, List<Tag> tags, List<Promotion> promotions, List<CartItem> cartItems, List<OwnedItem> ownedItems) {
        super(name, description, computerRequirements, price, averageRating, company, tags, promotions, cartItems, ownedItems);
        this.parentAdvisory = parentAdvisory;
        this.gamePicturesURLs = gamePicturesURLs;
        this.gameTrailersURLS = gameTrailersURLS;
        this.gameAccounts = gameAccounts;
        this.forums = forums;
    }

    

    /**
     * @return the parentAdvisory
     */
    public String getParentAdvisory() {
        return parentAdvisory;
    }

    /**
     * @param parentAdvisory the parentAdvisory to set
     */
    public void setParentAdvisory(String parentAdvisory) {
        this.parentAdvisory = parentAdvisory;
    }

    /**
     * @return the gamePicturesURLs
     */
    public List<String> getGamePicturesURLs() {
        return gamePicturesURLs;
    }

    /**
     * @param gamePicturesURLs the gamePicturesURLs to set
     */
    public void setGamePicturesURLs(List<String> gamePicturesURLs) {
        this.gamePicturesURLs = gamePicturesURLs;
    }

    /**
     * @return the gameTrailersURLS
     */
    public List<String> getGameTrailersURLS() {
        return gameTrailersURLS;
    }

    /**
     * @param gameTrailersURLS the gameTrailersURLS to set
     */
    public void setGameTrailersURLS(List<String> gameTrailersURLS) {
        this.gameTrailersURLS = gameTrailersURLS;
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
     * @return the forums
     */
    public List<Forum> getForums() {
        return forums;
    }

    /**
     * @param forums the forums to set
     */
    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

}
