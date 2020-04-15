/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
public class Game extends Product implements Serializable {

    
    @Size(min = 1, max = 5)
    private String parentAdvisory;
    private List<String> gamePicturesURLs;
    private List<String> gameTrailersURLS;


    @OneToMany(mappedBy = "game")
    private List<GameAccount> gameAccounts;

    public Game() {
        super();
        gameAccounts = new ArrayList<>();
    }

    public Game(String name, String description, String computerRequirements, double price, double averageRating) {
        super(name, description, computerRequirements, price, averageRating);
        gameAccounts = new ArrayList<>();
    }

    public Game(String parentAdvisory, List<String> gamePicturesURLs, List<String> gameTrailersURLS, String name, String description, String computerRequirements, double price, double averageRating) {
        super(name, description, computerRequirements, price, averageRating);
        this.parentAdvisory = parentAdvisory;
        this.gamePicturesURLs = gamePicturesURLs;
        this.gameTrailersURLS = gameTrailersURLS;
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
