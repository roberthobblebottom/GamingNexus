/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author root
 */
@Entity
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingCartID;
    @NotNull
    @Min(0)
    @Digits(integer = 1000000000, fraction = 2)
    private double subTotalPayable;
    @NotNull
    @Min(0)
    @Digits(integer = 1000000000, fraction = 2)
    private double discount;
    @NotNull
    @Min(0)
    @Digits(integer = 1000000000, fraction = 2)
    private double netTotalPayable;
    @OneToMany(mappedBy = "ShoppingCart")
    private List<CartItem> cartItems;
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    @NotNull
    private Customer customer;

    public ShoppingCart() {
    }

    public ShoppingCart(double subTotalPayable, double discount, double netTotalPayable, List<CartItem> cartItems, Customer customer) {
        this();
        this.subTotalPayable = subTotalPayable;
        this.discount = discount;
        this.netTotalPayable = netTotalPayable;
        this.cartItems = cartItems;
        this.customer = customer;
    }

    public Long getShoppingCartID() {
        return shoppingCartID;
    }

    public void setShoppingCartID(Long shoppingCartID) {
        this.shoppingCartID = shoppingCartID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shoppingCartID != null ? shoppingCartID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the shoppingCartID fields are not set
        if (!(object instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart other = (ShoppingCart) object;
        if ((this.shoppingCartID == null && other.shoppingCartID != null) || (this.shoppingCartID != null && !this.shoppingCartID.equals(other.shoppingCartID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.ShoppingCart[ id=" + shoppingCartID + " ]";
    }

    /**
     * @return the subTotalPayable
     */
    public double getSubTotalPayable() {
        return subTotalPayable;
    }

    /**
     * @param subTotalPayable the subTotalPayable to set
     */
    public void setSubTotalPayable(double subTotalPayable) {
        this.subTotalPayable = subTotalPayable;
    }

    /**
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * @return the netTotalPayable
     */
    public double getNetTotalPayable() {
        return netTotalPayable;
    }

    /**
     * @param netTotalPayable the netTotalPayable to set
     */
    public void setNetTotalPayable(double netTotalPayable) {
        this.netTotalPayable = netTotalPayable;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the cartItems
     */
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * @param cartItems the cartItems to set
     */
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}
