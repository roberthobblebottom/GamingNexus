/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author root
 */
@Entity
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemID;
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    @NotNull
    private Product product;
    @OneToOne
    @JoinColumn(nullable = false)
    private ShoppingCart shoppingCart;

    public CartItem() {
    }

    public CartItem(Product product, ShoppingCart shoppingCart) {
        this();
        this.product = product;
        this.shoppingCart = shoppingCart;
    }

    public Long getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(Long cartItemID) {
        this.cartItemID = cartItemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartItemID != null ? cartItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cartItemID fields are not set
        if (!(object instanceof CartItem)) {
            return false;
        }
        CartItem other = (CartItem) object;
        if ((this.cartItemID == null && other.cartItemID != null) || (this.cartItemID != null && !this.cartItemID.equals(other.cartItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.CartItem[ id=" + cartItemID + " ]";
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
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

}
