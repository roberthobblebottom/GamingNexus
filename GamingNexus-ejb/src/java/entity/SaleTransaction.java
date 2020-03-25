/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jinyichen
 */
@Entity
public class SaleTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleTransactionId;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer totalLineItem;
    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer totalQuantity;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2) // 11 - 2 digits to the left of the decimal point
    private BigDecimal totalAmount;
    @Column(nullable = false)
    @NotNull
    private LocalDateTime transactionDateTime;    
    @OneToMany
    private List<CartItem> cartItems;    
    @Column(nullable = false)
    @NotNull
    private Boolean voidRefund;
    
    // Updated in v5.0 to optional relationship
    @ManyToOne
    private User user;

    public SaleTransaction() {
        cartItems = new ArrayList<>();
        voidRefund = false;
    }

    
    public SaleTransaction(Integer totalLineItem, Integer totalQuantity, BigDecimal totalAmount, LocalDateTime transactionDateTime, Boolean voidRefund) {
        this.totalLineItem = totalLineItem;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.transactionDateTime = transactionDateTime;
        this.voidRefund = voidRefund;
    }
    
    
    public Long getSaleTransactionId() {
        return saleTransactionId;
    }

    public void setSaleTransactionId(Long saleTransactionId) {
        this.saleTransactionId = saleTransactionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleTransactionId != null ? saleTransactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the saleTransactionId fields are not set
        if (!(object instanceof SaleTransaction)) {
            return false;
        }
        SaleTransaction other = (SaleTransaction) object;
        if ((this.saleTransactionId == null && other.saleTransactionId != null) || (this.saleTransactionId != null && !this.saleTransactionId.equals(other.saleTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SaleTransaction[ id=" + saleTransactionId + " ]";
    }

    public Integer getTotalLineItem() {
        return totalLineItem;
    }

    public void setTotalLineItem(Integer totalLineItem) {
        this.totalLineItem = totalLineItem;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Boolean getVoidRefund() {
        return voidRefund;
    }

    public void setVoidRefund(Boolean voidRefund) {
        this.voidRefund = voidRefund;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}