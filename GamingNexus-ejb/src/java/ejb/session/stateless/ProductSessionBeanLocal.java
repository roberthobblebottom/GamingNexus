/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import javax.ejb.Local;
import util.exception.ProductNotFoundException;

/**
 *
 * @author root
 */
@Local
public interface ProductSessionBeanLocal {

    public Product retrieveProductById(Long productId) throws ProductNotFoundException;

    public void deleteProduct(Product productToBeDeleted) throws ProductNotFoundException;
    
}
