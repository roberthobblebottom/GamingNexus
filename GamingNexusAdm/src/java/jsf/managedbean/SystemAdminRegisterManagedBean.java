/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SystemAdminSessionBeanLocal;
import entity.SystemAdmin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.InputDataValidationException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jin yichen
 */
@Named(value = "systemAdminRegisterManagedBean")
@RequestScoped
public class SystemAdminRegisterManagedBean {

    @EJB(name = "SystemAdminSessionBeanLocal")
    private SystemAdminSessionBeanLocal systemAdminSessionBeanLocal;

    private SystemAdmin newSystemAdmin;
    
    public SystemAdminRegisterManagedBean() {
    }
    
    public void createNewSystemAdmin(javax.faces.event.ActionEvent event) {
        try {
            systemAdminSessionBeanLocal.createNewSystemAdmin(getNewSystemAdmin());
            setNewSystemAdmin(new SystemAdmin());
        } catch (SystemAdminUsernameExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred registering new system admin: " + ex.getMessage(), null));
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(SystemAdminRegisterManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SystemAdmin getNewSystemAdmin() {
        return newSystemAdmin;
    }

    public void setNewSystemAdmin(SystemAdmin newSystemAdmin) {
        this.newSystemAdmin = newSystemAdmin;
    }
    
}
