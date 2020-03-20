/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    @NotNull
    @Digits(integer = 15, fraction = 0)
    private int phoneNumber;
    @Size(min = 5, max = 200)
    @NotNull
    private String address;
    @NotNull
    @Size(min = 6, max = 100)
    @Pattern(regexp = "@\"^([0-9a-zA-Z](?>[-.\\w]*[0-9a-zA-Z])*@(?>[0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$\",")
    //https://stackoverflow.com/questions/13087755/can-anyone-tell-me-why-this-c-sharp-email-validation-regular-expression-regex
    private String email;
    @NotNull
    @Size(min = 1, max = 50)
    private String country;
    @Size(min = 1, max = 100)
    @NotNull
    private String username;
    @Size(min = 8, max = 100)
    @NotNull
    private String password;
    
    private String profilePictureURL;//https://stackoverflow.com/questions/29208007/what-is-the-data-type-for-images-in-java
    @Past
    @NotNull
    private Date lastOnline;
    

    public User() {
    }

    public User(int phoneNumber, String address, String email, String country, String username, String password, String profilePictureURL, Date lastOnline) {
        this();
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.country = country;
        this.username = username;
        this.password = password;
        this.profilePictureURL = profilePictureURL;
        this.lastOnline = lastOnline;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userID fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.User[ id=" + userID + " ]";
    }

    /**
     * @return the phoneNumber
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

 
    /**
     * @return the lastOnline
     */
    public Date getLastOnline() {
        return lastOnline;
    }

    /**
     * @param lastOnline the lastOnline to set
     */
    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the profilePictureURL
     */
    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    /**
     * @param profilePictureURL the profilePictureURL to set
     */
    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    /**
     * @return the birthDate
     */
}
