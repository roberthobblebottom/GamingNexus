/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import util.enumeration.ParentAdvisory;

/**
 *
 * @author root
 */
@Entity
public class Game extends Product implements Serializable {

    
    private ParentAdvisory parentAdvisory;


    @OneToMany(mappedBy = "game")
    private List<GameAccount> gameAccounts;

    public Game() {
        super();
        gameAccounts = new ArrayList<>();
    }

    public Game(ParentAdvisory parentAdvisory, String name, String description, String computerRequirements, 
            double price, double averageRating, LocalDate releaseDate, 
            List<String> pictureURLs, List<String> videoURLs, Company company, Category category, List<Tag> tags) {
        super(name, description, computerRequirements, price, averageRating, releaseDate,  pictureURLs, videoURLs, company, category, tags);
        this.parentAdvisory = parentAdvisory;
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
     * @return the parentAdvisory
     */
    public ParentAdvisory getParentAdvisory() {
        return parentAdvisory;
    }

    /**
     * @param parentAdvisory the parentAdvisory to set
     */
    public void setParentAdvisory(ParentAdvisory parentAdvisory) {
        this.parentAdvisory = parentAdvisory;
    }



}
