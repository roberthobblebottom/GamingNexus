/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author jinyichen
 */
@Entity
public class OtherSoftware extends Product implements Serializable {


    public OtherSoftware() {
        super();
    }

    public OtherSoftware(String name, String description, String computerRequirements, double price, double averageRating, LocalDate releaseDate, List<String> pictureURLs, List<String> videoURLs) {
        super(name, description, computerRequirements, price, averageRating, releaseDate, pictureURLs, videoURLs);
    }

    
    
   
   
    
}
