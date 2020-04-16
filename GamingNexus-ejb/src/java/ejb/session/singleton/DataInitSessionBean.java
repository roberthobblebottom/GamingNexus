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
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.SystemAdminSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Category;
import entity.Company;
import entity.Customer;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import entity.Promotion;
import entity.SystemAdmin;
import entity.Tag;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyUsernameExistException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewTagException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

/**
 *
 * @author jinyichen
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

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
        System.out.println("**************Entered Post construct");
        if (em.find(SystemAdmin.class, 1l) == null) {

            this.initializeData();
        }
    }

    private void initializeData() {

        try {
            System.out.println("**************Entered initialize data   ");

            SystemAdmin systemAdmin = new SystemAdmin("123456", "addr 1", "email@hotmail.com",
                    "Singapore", "admin1", "password", LocalDateTime.now(ZoneId.of("UTC+08:00")));
            systemAdminSessionBeanLocal.createNewSystemAdmin(systemAdmin);
//            systemAdminSessionBeanLocal.createNewSystemAdmin(new SystemAdmin("Default", "System Admin2", "admin2", "password"));

            Category categoryEntitySoftwareGame = categorySessionBeanLocal.createNewCategoryEntity(new Category("SoftwareGame", "Game"), null);
            Category categoryEntitySoftwareTool = categorySessionBeanLocal.createNewCategoryEntity(new Category("SoftwareTool", "SoftwareTool"), null);
            Category categoryEntityHardWare = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hardware", "Hardware"), null);
            Category categoryEntityFPS = categorySessionBeanLocal.createNewCategoryEntity(new Category("FPS", "Like CS"),
                    categoryEntitySoftwareGame.getCategoryId());

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

            Company company1 = companySessionBeanLocal.createNewCompany(new Company("123123", "Singapore", "company1@gmail.com",
                    "Singapore", "company1", "password", "picture", LocalDateTime.now()));
            Company company2 = companySessionBeanLocal.createNewCompany(new Company("1231234", "Singapore", "company2@gmail.com", "Singapore", "company2", "password", "picture", LocalDateTime.now()));
            Company company3 = companySessionBeanLocal.createNewCompany(new Company("123223", "Singapore", "company3@gmail.com", "Singapore", "company3", "password", "picture", LocalDateTime.now()));
            Company company4 = companySessionBeanLocal.createNewCompany(new Company("1232234", "Singapore", "company4@gmail.com", "Singapore", "company4", "password", "picture", LocalDateTime.now()));
            Company company5 = companySessionBeanLocal.createNewCompany(new Company("123323", "Singapore", "company5@gmail.com", "Singapore", "company5", "password", "picture", LocalDateTime.now()));

            Customer customer1 = customerSessionBeanlocal.createCustomer(new Customer("7654321", "Singapore", "customer1@gmail.com", "Singapore", "customer1", "password", "portfolio", LocalDateTime.now()));
            Customer customer2 = customerSessionBeanlocal.createCustomer(new Customer("76543210", "Singapore", "customer2@gmail.com", "Singapore", "customer2", "password", "portfolio", LocalDateTime.now()));
            Customer customer3 = customerSessionBeanlocal.createCustomer(new Customer("765432101", "Singapore", "customer3@gmail.com", "Singapore", "customer3", "password", "portfolio", LocalDateTime.now()));
            Customer customer4 = customerSessionBeanlocal.createCustomer(new Customer("765432102", "Singapore", "customer4@gmail.com", "Singapore", "customer4", "password", "portfolio", LocalDateTime.now()));
            Customer customer5 = customerSessionBeanlocal.createCustomer(new Customer("765432103", "Singapore", "customer5@gmail.com", "Singapore", "customer5", "password", "portfolio", LocalDateTime.now()));

            Game csgo = gameSessionBeanLocal.createNewGame(new Game("CSGO", "World famous first person shooter.",
                    "Windows 7 4 gigs of ram and 500MB of free space", 10, 5),
                    categoryEntityFPS.getCategoryId(), tagIdsPopularDiscount, company1.getUserId());
            Game dota = gameSessionBeanLocal.createNewGame(new Game("DOTA 2", "Defence of the ancient. MOAB",
                    "Windows 7 4 gigs of ram and 300mb of free space", 20, 4),
                    categoryEntityFPS.getCategoryId(), tagIdsPopular, company1.getUserId());
            Game halflife = gameSessionBeanLocal.createNewGame(new Game("half life alyx", "The half life series is back with a brand new VR game",
                    "Windows 10 8 gigs of ram and yes", 30, 5), categoryEntityFPS.getCategoryId(), tagIdsPopularNew, company1.getUserId());

            Game game2 = gameSessionBeanLocal.createNewGame(new Game("Game2", "Worse Than CF", "No Mac Pls", 23.5, 2.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company2.getUserId());
            Game game3 = gameSessionBeanLocal.createNewGame(new Game("Game3", "Worse Than CF", "No Mac Pls", 24.5, 3.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company3.getUserId());
            Game game4 = gameSessionBeanLocal.createNewGame(new Game("Game4", "Worse Than CF", "No Mac Pls", 25.5, 4.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company4.getUserId());
            Game game5 = gameSessionBeanLocal.createNewGame(new Game("Game5", "Worse Than CF", "No Mac Pls", 26.5, 5.5), categoryEntityFPS.getCategoryId(), tagIdsDiscount, company5.getUserId());

            Hardware valveindex = hardwareSessionBeanLocal.createNewHardware(new Hardware("1 year", "High responseiiveness, high display resolution",
                    "America", "Valve Index", "Virtual Reality Headset", "windows 10 8 gigs of ram 1 gig of free space", 100, 4.3), categoryEntityHardWare.getCategoryId(), tagIdsPopular, company1.getUserId());

            Hardware hardware2 = hardwareSessionBeanLocal.createNewHardware(new Hardware("2 year", "2x2x2", "America", "hardware2", "key board", "NA", 110, 1.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular, company2.getUserId());
            Hardware hardware3 = hardwareSessionBeanLocal.createNewHardware(new Hardware("3 year", "2x2x2", "America", "hardware3", "monitor", "NA", 120, 2.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular, company3.getUserId());
            Hardware hardware4 = hardwareSessionBeanLocal.createNewHardware(new Hardware("1 year", "2x2x2", "America", "hardware4", "controller", "NA", 130, 3.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular, company4.getUserId());
            Hardware hardware5 = hardwareSessionBeanLocal.createNewHardware(new Hardware("2 year", "2x2x2", "America", "hardware5", "hard disk", "NA", 140, 4.5), categoryEntityHardWare.getCategoryId(), tagIdsPopular, company5.getUserId());

            OtherSoftware softwaretool1 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("Steam", "IDE", "Windows 7", 1, 4.5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company1.getUserId());
            OtherSoftware softwaretool2 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software2", "IDE", "No requirements", 30, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company2.getUserId());
            OtherSoftware softwaretool3 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software3", "IDE", "No requirements", 40, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company3.getUserId());
            OtherSoftware softwaretool4 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software4", "IDE", "No requirements", 50, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company4.getUserId());
            OtherSoftware softwaretool5 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware("software5", "IDE", "No requirements", 60, 5), categoryEntitySoftwareTool.getCategoryId(), tagIdsEmpty, company5.getUserId());

            Promotion promo1 = promotionSessionBean.createPromotion(new Promotion("VALVE SALE", "YAAAY ANOTHER SALLLLEE",
                    (double) 10, (double) 0, LocalDateTime.now(),
                    LocalDateTime.now().plusDays(10),
                    new ArrayList<>(Arrays.asList(csgo))));

            csgo.getPromotions().add(promo1);
            try {
                gameSessionBeanLocal.updateGame(csgo, csgo.getCategory().getCategoryId(), tagIdsPopularDiscount);
            } catch (CategoryNotFoundException | ProductNotFoundException | TagNotFoundException | UpdateProductException ex) {
                ex.printStackTrace();
            }
            
            List<Product> company1Products = company1.getProducts();
            company1Products.add(csgo);
            company1Products.add(dota);
            company1Products.add(valveindex);
            company1Products.add(softwaretool1);

            company1.setProducts(company1Products);
            companySessionBeanLocal.updateCompany(company1);

        } catch (SystemAdminUsernameExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | CreateNewTagException | CreateNewProductException | ProductSkuCodeExistException | CompanyNotFoundException | CompanyUsernameExistException | CustomerUsernameExistException ex) {
            ex.printStackTrace();
        }
    }

}
