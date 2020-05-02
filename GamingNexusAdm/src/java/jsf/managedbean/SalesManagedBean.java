/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SaleTransactionSessionBeanLocal;
import entity.SaleTransaction;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author 63216
 */
@Named(value = "salesManagedBean")
@RequestScoped
public class SalesManagedBean {

    @EJB(name = "SaleTransactionSessionBeanLocal")
    private SaleTransactionSessionBeanLocal saleTransactionSessionBeanLocal;

    private List<SaleTransaction> saleTransactions;
    private SaleTransaction saleTransactionToView;
    
    /**
     * Creates a new instance of SalesManagedBean
     */
    public SalesManagedBean() {
    }

    @PostConstruct
    public void postConstruct(){
        saleTransactions = saleTransactionSessionBeanLocal.retrieveAllSaleTransactions();
        saleTransactionToView = new SaleTransaction();
    }
    
    public void viewSaleTransactionDetails(ActionEvent event) throws IOException
    {
        Long saleTransactionIdToView = (Long)event.getComponent().getAttributes().get("saleTransactionId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("saleTransactionIdToView", saleTransactionIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewSaleTransactionDetails.xhtml");
    }
    
    
    
    
    
    
    /**
     * @return the saleTransactions
     */
    public List<SaleTransaction> getSaleTransactions() {
        return saleTransactions;
    }

    /**
     * @param saleTransactions the saleTransactions to set
     */
    public void setSaleTransactions(List<SaleTransaction> saleTransactions) {
        this.saleTransactions = saleTransactions;
    }

    /**
     * @return the saleTransactionToView
     */
    public SaleTransaction getSaleTransactionToView() {
        return saleTransactionToView;
    }

    /**
     * @param saleTransactionToView the saleTransactionToView to set
     */
    public void setSaleTransactionToView(SaleTransaction saleTransactionToView) {
        this.saleTransactionToView = saleTransactionToView;
    }
    
}
