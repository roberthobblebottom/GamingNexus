/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jinyichen
 */
@Stateless
public class SaleTransactionSessionBean implements SaleTransactionSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;
    
    
    
    

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    
    

    
}
