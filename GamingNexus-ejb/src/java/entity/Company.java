/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "company")
    private List<Game> games;
    @OneToMany(mappedBy = "company")
    private List<Chat> chats;

    public Company() {
        super();
        games = new ArrayList<>();
        chats = new ArrayList<>();
    }

    
    public Company(String phoneNumber, String address, String email, String country, String username, String password, String profilePictureURL, LocalDateTime lastOnline) {
        super(phoneNumber, address, email, country, username, password, profilePictureURL, lastOnline);
        games = new ArrayList<>();
        chats = new ArrayList<>();
    }

    /**
     * @return the games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(List<Game> games) {
        this.games = games;
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
