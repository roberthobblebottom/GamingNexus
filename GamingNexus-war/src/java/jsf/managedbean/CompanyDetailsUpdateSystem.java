/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Company;
import entity.User;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CompanyNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author root
 */
@Named(value = "companyDetailsUpdateSystem")
@ViewScoped
public class CompanyDetailsUpdateSystem implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    @EJB
    private CompanySessionBeanLocal companySessionBean;

    private Company company;

    private String oldPassword, reenteredNewPassword, newPassword;

    /**
     * Creates a new instance of CompanyDetailsUpdateSystem
     */
    public CompanyDetailsUpdateSystem() {

    }

    @PostConstruct
    public void postConstruct() {
        this.oldPassword = "";
        this.reenteredNewPassword = "";
        this.newPassword = "";
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        setCompany((Company) sessionMap.get("company"));
    }

    public void updateCompany() {
        User user;
        try {
            user = userSessionBean.userLogin(company.getUsername(), oldPassword);
        } catch (InvalidLoginCredentialException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Sorry you have entered wrong old password", null));
            return;
        }
        if (user == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Sorry you have entered wrong old password", null));
            return;
        }
        if (!newPassword.equals("")) {
            System.out.println("Setting New password: "+newPassword);
            company.setPassword(newPassword);
        }
        this.oldPassword = "";
        this.reenteredNewPassword = "";
        this.newPassword = "";

        try {
            companySessionBean.updateCompany(company);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Company details has been updated", null));

        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sorry error occured: " + ex.toString(), null));
        }
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the reenteredNewPassword
     */
    public String getReenteredNewPassword() {
        return reenteredNewPassword;
    }

    /**
     * @param reenteredNewPassword the reenteredNewPassword to set
     */
    public void setReenteredNewPassword(String reenteredNewPassword) {
        this.reenteredNewPassword = reenteredNewPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
