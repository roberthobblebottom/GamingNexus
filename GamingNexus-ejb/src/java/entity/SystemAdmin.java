/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;

/**
 *
 * @author Yang Xi
 */
@Entity
public class SystemAdmin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long systemAdminId;
    
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
 
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 4, max = 32)
    private String username;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;

    public SystemAdmin() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
    }

    public SystemAdmin(String firstName, String lastName, String username, String password) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        
        setPassword(password);
    }
    
    
    

    public Long getSystemAdminId() {
        return systemAdminId;
    }

    public void setSystemAdminId(Long systemAdminId) {
        this.systemAdminId = systemAdminId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemAdminId != null ? systemAdminId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the systemAdminId fields are not set
        if (!(object instanceof SystemAdmin)) {
            return false;
        }
        SystemAdmin other = (SystemAdmin) object;
        if ((this.systemAdminId == null && other.systemAdminId != null) || (this.systemAdminId != null && !this.systemAdminId.equals(other.systemAdminId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SystemAdmin[ id=" + systemAdminId + " ]";
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        if(password != null)
        {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        }
        else
        {
            this.password = null;
        }
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
}
