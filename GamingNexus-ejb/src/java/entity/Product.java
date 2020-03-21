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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;  
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long productId;
    @NotNull
    @Size(min = 1, max = 100)
    protected String name;
    @NotNull
    @Size(min = 0, max = 5000)
    protected String description;
    //@NotNull
    @Size(min = 0, max = 5000)
    protected String computerRequirements;
    @NotNull
    @Digits(integer = 1000000000, fraction = 2)
    protected double price;
    @Digits(integer = 1, fraction = 2)
    @Min(0)
    @Max(5)
    @NotNull
    protected double averageRating;
    
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    protected Company company;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    protected Category category;
    @ManyToMany(mappedBy = "products")
    protected List<Tag> tags;
    @ManyToMany()
    protected List<Promotion> promotions;
    @OneToMany(mappedBy = "Product")
    protected List<Rating> ratings;
    @OneToMany(mappedBy = "Product")
    protected List<CartItem> cartItems;
    @OneToMany(mappedBy = "Product")
    protected List<OwnedItem> ownedItems;

    public Product() {
        tags = new ArrayList<>();
        ratings = new ArrayList<>();
        cartItems = new ArrayList<>();
        ownedItems = new ArrayList<>();
    }

    public Product(String name, String description, String computerRequirements, double price, double averageRating) {
        this();
        this.name = name;
        this.description = description;
        this.computerRequirements = computerRequirements;
        this.price = price;
        this.averageRating = averageRating;
    }
    
    

    
    
    public void addTag(Tag tagEntity)
    {
        if(tagEntity != null)
        {
            if(!this.tags.contains(tagEntity))
            {
                this.tags.add(tagEntity);
                
                if(!tagEntity.getProducts().contains(this))
                {                    
                    tagEntity.getProducts().add(this);
                }
            }
        }
    }
    
    
    
    public void removeTag(Tag tagEntity)
    {
        if(tagEntity != null)
        {
            if(this.tags.contains(tagEntity))
            {
                this.tags.remove(tagEntity);
                
                if(tagEntity.getProducts().contains(this))
                {
                    tagEntity.getProducts().remove(this);
                }
            }
        }
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productId fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.entity.Product[ id=" + productId + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the computerRequirements
     */
    public String getComputerRequirements() {
        return computerRequirements;
    }

    /**
     * @param computerRequirements the computerRequirements to set
     */
    public void setComputerRequirements(String computerRequirements) {
        this.computerRequirements = computerRequirements;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the averageRating
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * @param averageRating the averageRating to set
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the tag
     */
    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the promotions
     */
    public List<Promotion> getPromotions() {
        return promotions;
    }

    /**
     * @param promotions the promotions to set
     */
    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    /**
     * @return the ratings
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * @return the cartItems
     */
    public List<CartItem> getCartItem() {
        return cartItems;
    }

    /**
     * @param cartItems the cartItems to set
     */
    public void setCartItem(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * @return the ownedItems
     */
    public List<OwnedItem> getOwnedItems() {
        return ownedItems;
    }

    /**
     * @param ownedItems the ownedItems to set
     */
    public void setOwnedItems(List<OwnedItem> ownedItems) {
        this.ownedItems = ownedItems;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        if(this.category != null)
        {
            if(this.category.getProducts().contains(this))
            {
                this.category.getProducts().remove(this);
            }
        }
        
        this.category = category;
        
        if(this.category != null)
        {
            if(!this.category.getProducts().contains(this))
            {
                this.category.getProducts().add(this);
            }
        }
    }

}
