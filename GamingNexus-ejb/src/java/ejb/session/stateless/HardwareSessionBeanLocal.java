/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Hardware;
import entity.Product;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author chenli
 */
@Local
public interface HardwareSessionBeanLocal {

    public Hardware createNewHardware(Hardware newHardware, Long categoryId, List<Long> tagIds, Long CompanyId) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException;

    public List<Hardware> retrieveAllHardwares();

    public List<Product> filterProductsByCategory(Long categoryId) throws CategoryNotFoundException;

    public List<Hardware> searchHardwaresByName(String searchString);
    
}
