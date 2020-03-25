/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Stateless
public class UserSessionBean implements UserSessionBeanLocal {

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    @Override
    public User retrieveUserByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :inputUsername AND u.password = :inputPassword");
        query.setParameter("inputUsername", username);
        query.setParameter("inputPassword", password);
        return (User) query.getSingleResult();
    }

}
