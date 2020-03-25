/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Game;
import entity.Product;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

/**
 *
 * @author 63216
 */
@Local
public interface GameSessionBeanLocal {

    public List<Game> retrieveAllGames();

    public List<Game> searchGamesByName(String searchString);

    public List<Product> filterProductsByCategory(Long categoryId) throws CategoryNotFoundException;

    public List<Product> filterProductsByTags(List<Long> tagIds, String condition);

    public Product retrieveProductByProductId(Long productId) throws ProductNotFoundException;

    public void updateProduct(Product productEntity, Long categoryId, List<Long> tagIds) throws ProductNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateProductException, InputDataValidationException;

    public Game createNewGame(Game newGame, Long categoryId, List<Long> tagIds, Long CompanyId) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException;

    
}