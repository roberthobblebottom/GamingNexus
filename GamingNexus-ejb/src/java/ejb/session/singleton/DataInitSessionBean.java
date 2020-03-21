/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.SystemAdminSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Category;
import entity.Company;
import entity.Game;
import entity.SystemAdmin;
import entity.Tag;
import entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewTagException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author fk
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "GameSessionBeanLocal")
    private GameSessionBeanLocal gameSessionBeanLocal;

    @EJB
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB(name = "SystemAdminSessionBeanLocal")
    private SystemAdminSessionBeanLocal systemAdminSessionBeanLocal;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {

        initializeData();
//        try {
//            
//            initializeData();
//            systemAdminSessionBeanLocal.retrieveSystemAdminByUsername("manager");
//            
//        } catch (SystemAdminNotFoundException ex) {
//            initializeData();
//            System.out.print("dataInit");
//        }
    }

    private void initializeData() {
        try {
            systemAdminSessionBeanLocal.createNewSystemAdmin(new SystemAdmin("Default", "System Admin1", "admin1", "password"));
            systemAdminSessionBeanLocal.createNewSystemAdmin(new SystemAdmin("Default", "System Admin2", "admin2", "password"));

            Category categoryEntitySoftware = categorySessionBeanLocal.createNewCategoryEntity(new Category("Electronics", "Electronics"), null);
            Category categoryEntityHardWare = categorySessionBeanLocal.createNewCategoryEntity(new Category("Fashions", "Fashions"), null);
            Category categoryEntityFPS = categorySessionBeanLocal.createNewCategoryEntity(new Category("Category A", "Category A"), categoryEntitySoftware.getCategoryId());

            Tag tagEntityPopular = tagSessionBeanLocal.createNewTagEntity(new Tag("popular"));
            Tag tagEntityDiscount = tagSessionBeanLocal.createNewTagEntity(new Tag("discount"));
            Tag tagEntityNew = tagSessionBeanLocal.createNewTagEntity(new Tag("new"));

            List<Long> tagIdsPopular = new ArrayList<>();
            tagIdsPopular.add(tagEntityPopular.getTagId());

            List<Long> tagIdsDiscount = new ArrayList<>();
            tagIdsDiscount.add(tagEntityDiscount.getTagId());

            List<Long> tagIdsPopularDiscount = new ArrayList<>();
            tagIdsPopularDiscount.add(tagEntityPopular.getTagId());
            tagIdsPopularDiscount.add(tagEntityDiscount.getTagId());

            List<Long> tagIdsPopularNew = new ArrayList<>();
            tagIdsPopularNew.add(tagEntityPopular.getTagId());
            tagIdsPopularNew.add(tagEntityNew.getTagId());

            List<Long> tagIdsPopularDiscountNew = new ArrayList<>();
            tagIdsPopularDiscountNew.add(tagEntityPopular.getTagId());
            tagIdsPopularDiscountNew.add(tagEntityDiscount.getTagId());
            tagIdsPopularDiscountNew.add(tagEntityNew.getTagId());

            List<Long> tagIdsEmpty = new ArrayList<>();
            
            User company1 = new Company("1231", "Singapore", "company@gmail.com", "Singapore", "company1", "password", "picture", new Date());

            gameSessionBeanLocal.createNewGame(new Game("Cs_Go", "WOrse Than CF", "No Mac Pls", 22.5, 1.5), categoryEntitySoftware.getCategoryId(), tagIdsDiscount, company1.getUserId());

        } catch (SystemAdminUsernameExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | CreateNewTagException | CreateNewProductException | ProductSkuCodeExistException | CompanyNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
