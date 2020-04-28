/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SaleTransaction;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewSaleTransactionException;
import util.exception.CustomerNotFoundException;
import util.exception.SaleTransactionNotFoundException;


/**
 *
 * @author chenli
 */
@Local
public interface SaleTransactionSessionBeanLocal {
    

    public List<SaleTransaction> retrieveAllSaleTransactionsByCustomerId(Long customerId);

    public SaleTransaction retrieveSaleTransactionBySaleTransactionId(Long saleTransactionId) throws SaleTransactionNotFoundException;

    public List<SaleTransaction> retrieveAllSaleTransactions();


    public SaleTransaction createNewSaleTransaction(Long customerId, SaleTransaction newSaleTransaction) throws CustomerNotFoundException, CreateNewSaleTransactionException;
}

