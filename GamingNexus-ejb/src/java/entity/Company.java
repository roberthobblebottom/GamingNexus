/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author root
 */
@Entity
public class Company extends User implements Serializable {

    @OneToMany(mappedBy = "Company")
    private List<Product> products;
    @OneToMany(mappedBy = "Company")
    private List<Chat> chats;

    public Company() {
        super();
    }

    
    public Company(String phoneNumber, String address, String email, String country, String username, String password, String profilePictureURL, java.util.Date lastOnline) {
        super(phoneNumber, address, email, country, username, password, profilePictureURL, lastOnline);
        products = new ArrayList<>();
        chats = new ArrayList<>();
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

}
