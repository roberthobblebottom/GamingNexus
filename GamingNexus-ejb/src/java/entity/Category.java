package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author 63216
 */
@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String description;
    
    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subCategories;
    @ManyToOne
    private Category parentCategory;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
    
    public Category() 
    {
        subCategories = new ArrayList<>();
        products = new ArrayList<>();
    }
    
    public Category(String name, String description) 
    {
        this();
        
        this.name = name;
        this.description = description;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryID != null ? categoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the categoryID fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryID == null && other.categoryID != null) || (this.categoryID != null && !this.categoryID.equals(other.categoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Category[ id=" + categoryID + " ]";
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
     * @return the subCategories
     */
    public List<Category> getSubCategories() {
        return subCategories;
    }

    /**
     * @param subCategories the subCategories to set
     */
    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * @return the parentCategory
     */
    public Category getParentCategory() {
        return parentCategory;
    }

    /**
     * @param parentCategory the parentCategory to set
     */
    public void setParentCategory(Category parentCategory) {
        if(this.parentCategory != null)
        {
            if(this.parentCategory.getSubCategories().contains(this))
            {
                this.parentCategory.getSubCategories().remove(this);
            }
        }
        
        this.parentCategory = parentCategory;
        
        if(this.parentCategory != null)
        {
            if(!this.parentCategory.getSubCategories().contains(this))
            {
                this.parentCategory.getSubCategories().add(this);
            }
        }
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

    
}
