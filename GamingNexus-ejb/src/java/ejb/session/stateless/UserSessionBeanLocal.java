/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.User;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author root
 */
@Local
public interface UserSessionBeanLocal {

    User retrieveUserByUsernameAndPassword(String username, String password) throws NoResultException;
    
}
