/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.HardwareSessionBeanLocal;
import ejb.session.stateless.OtherSoftwareSessionBeanLocal;
import ejb.session.stateless.SystemAdminSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Category;
import entity.Company;
import entity.Customer;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.SystemAdmin;
import entity.Tag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyUsernameExistException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewTagException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author jinyichen
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "OtherSoftwareSessionBeanLocal")
    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBeanLocal;

    @EJB(name = "HardwareSessionBeanLocal")
    private HardwareSessionBeanLocal hardwareSessionBeanLocal;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanlocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

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

            Category categoryEntitySoftwareGame = categorySessionBeanLocal.createNewCategoryEntity(new Category("SoftwareGame", "Game"), null);
            Category categoryEntitySoftwareTool = categorySessionBeanLocal.createNewCategoryEntity(new Category("SoftwareTool", "SoftwareTool"), null);
            Category categoryEntityHardWare = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hardware", "Hardware"), null);
            Category categoryEntityFPS = categorySessionBeanLocal.createNewCategoryEntity(new Category("FPS", "Like CS"), categoryEntitySoftwareGame.getCategoryId());

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

            Company company1 = companySessionBeanLocal.createNewCompany(new Company("123123", "Singapore", "company1@gmail.com", "Singapore", "company1", "password", "picture", LocalDateTime.now()));
            Company company2 = companySessionBeanLocal.createNewCompany(new Company("1231234", "Singapore", "company2@gmail.com", "Singapore", "company2", "password", "picture", LocalDateTime.now()));
            Company company3 = companySessionBeanLocal.createNewCompany(new Company("123223", "Singapore", "company3@gmail.com", "Singapore", "company3", "password", "picture", LocalDateTime.now()));
            Company company4 = companySessionBeanLocal.createNewCompany(new Company("1232234", "Singapore", "company4@gmail.com", "Singapore", "company4", "password", "picture", LocalDateTime.now()));
            Company company5 = companySessionBeanLocal.createNewCompany(new Company("123323", "Singapore", "company5@gmail.com", "Singapore", "company5", "password", "picture", LocalDateTime.now()));

            Customer customer1 = customerSessionBeanlocal.createCustomer(new Customer("7654321", "Singapore", "customer1@gmail.com", "Singapore", "customer1", "password", "portfolio", LocalDateTime.now()));
            Customer customer2 = customerSessionBeanlocal.createCustomer(new Customer("76543210", "Singapore", "customer2@gmail.com", "Singapore", "customer2", "password", "portfolio", LocalDateTime.now()));
            Customer customer3 = customerSessionBeanlocal.createCustomer(new Customer("765432101", "Singapore", "customer3@gmail.com", "Singapore", "customer3", "password", "portfolio", LocalDateTime.now()));
            Customer customer4 = customerSessionBeanlocal.createCustomer(new Customer("765432102", "Singapore", "customer4@gmail.com", "Singapore", "customer4", "password", "portfolio", LocalDateTime.now()));
            Customer customer5 = customerSessionBeanlocal.createCustomer(new Customer("765432103", "Singapore", "customer5@gmail.com", "Singapore", "customer5", "password", "portfolio", LocalDateTime.now()));

            Game game1 = gameSessionBeanLocal.createNewGame(new Game("Game1", "Worse Than CF", "No Mac Pls", 22.5, 1.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company1.getUserId());
            Game game2 = gameSessionBeanLocal.createNewGame(new Game("Game2", "Worse Than CF", "No Mac Pls", 23.5, 2.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company2.getUserId());
            Game game3 = gameSessionBeanLocal.createNewGame(new Game("Game3", "Worse Than CF", "No Mac Pls", 24.5, 3.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company3.getUserId());
            Game game4 = gameSessionBeanLocal.createNewGame(new Game("Game4", "Worse Than CF", "No Mac Pls", 25.5, 4.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company4.getUserId());
            Game game5 = gameSessionBeanLocal.createNewGame(new Game("Game5", "Worse Than CF", "No Mac Pls", 26.5, 5.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company5.getUserId());

            Hardware hardware1 = hardwareSessionBeanLocal.createNewHardware(new Hardware("1 year", "2x2x2", "America", "hardware1", "mouse", "NA", 100, 1), categoryEntityHardWare.getCategoryId(), tagIdsPopular,company1.getUserId() );
            Hardware hardware2 = hardwareSessionBeanLocal.createNewHardware(new Hardware("2 year", "2x2x2", "America", "hardware2", "key board", "NA", 110, 1.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular,company2.getUserId() );
            Hardware hardware3 = hardwareSessionBeanLocal.createNewHardware(new Hardware("3 year", "2x2x2", "America", "hardware3", "monitor", "NA", 120, 2.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular,company3.getUserId() );
            Hardware hardware4 = hardwareSessionBeanLocal.createNewHardware(new Hardware("1 year", "2x2x2", "America", "hardware4", "controller", "NA", 130, 3.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular,company4.getUserId() );
            Hardware hardware5 = hardwareSessionBeanLocal.createNewHardware(new Hardware("2 year", "2x2x2", "America", "hardware5", "hard disk", "NA", 140, 4.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular,company5.getUserId() );
            
            OtherSoftware softwaretool1 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software1", "IDE", "No requirements", 20, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company1.getUserId());
            OtherSoftware softwaretool2 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software2", "IDE", "No requirements", 30, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company2.getUserId());
            OtherSoftware softwaretool3 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software3", "IDE", "No requirements", 40, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company3.getUserId());
            OtherSoftware softwaretool4 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software4", "IDE", "No requirements", 50, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company4.getUserId());
            OtherSoftware softwaretool5 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software5", "IDE", "No requirements", 60, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company5.getUserId());
            
          
        } catch (SystemAdminUsernameExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | CreateNewTagException | CreateNewProductException | ProductSkuCodeExistException | CompanyNotFoundException | CompanyUsernameExistException | CustomerUsernameExistException ex) {
            ex.printStackTrace();
        }
    }

}
