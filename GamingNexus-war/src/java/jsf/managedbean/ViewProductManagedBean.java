/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Product;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author root
 */
@Named(value = "viewProductManagedBean")
@Dependent
public class ViewProductManagedBean {

    /**
     * Creates a new instance of ViewProductManagedBean
     */
    
    private Product productToViewInDetail;
            
    public ViewProductManagedBean() {
        
    }

    /**
     * @return the productToViewInDetail
     */
    public Product getProductToViewInDetail() {
        return productToViewInDetail;
    }

    /**
     * @param productToViewInDetail the productToViewInDetail to set
     */
    public void setProductToViewInDetail(Product productToViewInDetail) {
        this.productToViewInDetail = productToViewInDetail;
    }
    
}
