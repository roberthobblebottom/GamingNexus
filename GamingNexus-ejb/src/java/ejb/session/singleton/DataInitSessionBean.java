/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.SystemAdminSessionBeanLocal;
import entity.SystemAdmin;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author ufoya
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "SystemAdminSessionBeanLocal")
    private SystemAdminSessionBeanLocal systemAdminSessionBeanLocal;
    
    

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

    
    @PostConstruct
    public void postConstruct(){
        initializeData();
    }
    
    private void initializeData() {
        try{
        systemAdminSessionBeanLocal.createNewSystemAdmin(new SystemAdmin("Default", "System Admin1", "admin1", "password"));
        systemAdminSessionBeanLocal.createNewSystemAdmin(new SystemAdmin("Default", "System Admin2", "admin2", "password"));
        }
        catch(SystemAdminUsernameExistException | UnknownPersistenceException ex)
        {
            ex.printStackTrace();
        }      
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
