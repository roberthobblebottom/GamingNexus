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
    
}
