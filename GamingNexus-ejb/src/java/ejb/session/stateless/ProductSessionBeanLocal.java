/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import javax.ejb.Local;

/**
 *
 * @author root
 */
@Local
public interface ProductSessionBeanLocal {

    public Product retrieveProductById(Long productId);


    public void deleteProduct(Product product);

    public void deleteProduct(Product productToBeDeleted) throws ProductNotFoundException;

    public List<Product> searchProductsByName(String searchString);

    public List<Product> retrieveProductByCategoryId(Long categoryId);

    
}
