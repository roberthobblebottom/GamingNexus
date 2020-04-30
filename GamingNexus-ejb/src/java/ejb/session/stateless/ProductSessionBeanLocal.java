/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import java.util.List;
import javax.ejb.Local;
import util.exception.ProductNotFoundException;

/**
 *
 * @author root
 */
@Local
public interface ProductSessionBeanLocal {

    public Product retrieveProductById(Long productId) throws ProductNotFoundException;

    public void deleteProduct(Product product);

    public List<Product> searchProductsByName(String searchString);

    public List<Product> retrieveProductByCategoryId(Long categoryId);

    
}
