/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CompanyNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.security.CryptographicHelper;

/**
 *
 * @author chenli
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public CustomerSessionBean() {
    }

    public Customer createCustomer(Customer newCustomer) throws UnknownPersistenceException, CustomerUsernameExistException {
        try {
            em.persist(newCustomer);
            em.flush();

            return newCustomer;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new CustomerUsernameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    public List<Customer> retrieveAllCustomers() {
        Query query = em.createQuery("SELECT c FROM Customer c");

        return query.getResultList();
    }

    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException {
        Customer customer = em.find(Customer.class, customerId);

        if (customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException("Customer ID " + customerId + " does not exist!");
        }
    }

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Customer) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Companu Username " + username + " does not exist!");
        }
    }

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Customer customer = retrieveCustomerByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + customer.getSalt()));

            if (customer.getPassword().equals(passwordHash)) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    public void updateCustomer(Customer customer) throws CustomerNotFoundException {
        if (customer != null && customer.getUserId() != null) {

            Customer customerToUpdate = retrieveCustomerById(customer.getUserId());

            customerToUpdate.setAddress(customer.getAddress());
            customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
            customerToUpdate.setEmail(customer.getEmail());
        } else {
            throw new CustomerNotFoundException("Customer ID not provided for SystemAdmin to be updated");
        }
    }

    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customerToRemove = retrieveCustomerById(customerId);

        em.remove(customerToRemove);

    }
}
