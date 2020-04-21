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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        //if (em.find(Game.class, 1l) == null) {

            this.initializeData();
        //}
    }

    private void initializeData() {

        try {
            System.out.println("**************Entered initialize data   ");

            SystemAdmin systemAdmin = new SystemAdmin("123456", "addr 1", "email@hotmail.com", "Singapore", "admin1", "password");
            systemAdminSessionBeanLocal.createNewSystemAdmin(systemAdmin);
//            systemAdminSessionBeanLocal.createNewSystemAdmin(new SystemAdmin("Default", "System Admin2", "admin2", "password"));

            
            Category categoryEntitySoftwareGame = categorySessionBeanLocal.createNewCategoryEntity(new Category("SoftwareGame", "Game"), null);
            Category categoryEntitySoftwareTool = categorySessionBeanLocal.createNewCategoryEntity(new Category("SoftwareTool", "SoftwareTool"), null);
            Category categoryEntityHardware = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hardware", "Hardware"), null);

            Tag tagEntityPopular = tagSessionBeanLocal.createNewTagEntity(new Tag("Popular"));
            Tag tagEntityDiscount = tagSessionBeanLocal.createNewTagEntity(new Tag("Discount"));
            Tag tagEntityNew = tagSessionBeanLocal.createNewTagEntity(new Tag("New"));
            Tag tagEntityAction = tagSessionBeanLocal.createNewTagEntity(new Tag("Action"));
            Tag tagEntityAdventure = tagSessionBeanLocal.createNewTagEntity(new Tag("Adventure"));
            Tag tagEntityCasual = tagSessionBeanLocal.createNewTagEntity(new Tag("Casual"));
            Tag tagEntitySimulation = tagSessionBeanLocal.createNewTagEntity(new Tag("Simulation"));
            Tag tagEntityMultiplayer = tagSessionBeanLocal.createNewTagEntity(new Tag("Multiplayer"));
            Tag tagEntitySingleplayer = tagSessionBeanLocal.createNewTagEntity(new Tag("Singleplayer"));
            Tag tagEntitySports = tagSessionBeanLocal.createNewTagEntity(new Tag("Sports"));
            Tag tagEntityRacing = tagSessionBeanLocal.createNewTagEntity(new Tag("Racing"));
            Tag tagEntityStrategy = tagSessionBeanLocal.createNewTagEntity(new Tag("Strategy"));
            Tag tagEntityRPG = tagSessionBeanLocal.createNewTagEntity(new Tag("RPG"));
            Tag tagEntityFPS = tagSessionBeanLocal.createNewTagEntity(new Tag("FPS"));
            Tag tagEntityPuzzle = tagSessionBeanLocal.createNewTagEntity(new Tag("Puzzle"));

            Company company1 = companySessionBeanLocal.createNewCompany(new Company("123123", "Singapore", "company1@gmail.com", "Singapore", "company1", "password"));
            Company company2 = companySessionBeanLocal.createNewCompany(new Company("1231234", "Singapore", "company2@gmail.com", "Singapore", "company2", "password"));
            Company company3 = companySessionBeanLocal.createNewCompany(new Company("123223", "Singapore", "company3@gmail.com", "Singapore", "company3", "password"));
            Company company4 = companySessionBeanLocal.createNewCompany(new Company("1232234", "Singapore", "company4@gmail.com", "Singapore", "company4", "password"));
            Company company5 = companySessionBeanLocal.createNewCompany(new Company("123323", "Singapore", "company5@gmail.com", "Singapore", "company5", "password"));

            List<Long> tags = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            String name = "Counter-Strike";
            LocalDate releaseDate = LocalDate.parse("2000-11-01", formatter);
            boolean parentAdvisory = false;
            double averageRating = 88;
            int sales = 13033334;
            double price = 10.00;
            String description = "Play the worlds number 1 online action game. Engage in an incredibly realistic brand of terrorist warfare in this wildly popular team-based game. Ally with teammates to complete strategic missions. Take out enemy sites. Rescue hostages. Your role affects your teams success. Your teams success affects your role.";
            String headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/10/header.jpg?t=1447887426";
            String computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            Long categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityFPS.getTagId());
            Game csgo = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid,tags,company1.getUserId());

            name = "Portal";
            releaseDate = LocalDate.parse("2007-10-10", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 9795716;
            price = 10.00;
            description = "Portal is a new single player game from Valve. Set in the mysterious Aperture Science Laboratories Portal has been called one of the most innovative new games on the horizon and will offer gamers hours of unique gameplay.The game is designed to change the way players approach manipulate and surmise the possibilities in a given environment; similar to how Half-Life?? 2s Gravity Gun innovated new ways to leverage an object in any given situation.Players must solve physical puzzles and challenges by opening portals to maneuvering objects and themselves through space.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/400/header.jpg?t=1447890222";
            computerRequirements = "Minimum: 1.7 GHz Processor 512MB RAM DirectX 8.1 level Graphics Card (Requires support for SSE) Windows 7 (32/64-bit)/Vista/XP Mouse Keyboard Internet ConnectionRecommended: Pentium 4 processor (3.0GHz or better) 1GB RAM DirectX 9 level Graphics Card Windows 7 (32/64-bit)/Vista/XP Mouse Keyboard Internet Connection";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityPuzzle.getTagId());
            Game portal = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid,tags,company1.getUserId());

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

            Customer customer1 = customerSessionBeanlocal.createCustomer(new Customer("7654321", "Singapore", "customer1@gmail.com", "Singapore", "customer1", "password"));
            Customer customer2 = customerSessionBeanlocal.createCustomer(new Customer("76543210", "Singapore", "customer2@gmail.com", "Singapore", "customer2", "password"));
            Customer customer3 = customerSessionBeanlocal.createCustomer(new Customer("765432101", "Singapore", "customer3@gmail.com", "Singapore", "customer3", "password"));
            Customer customer4 = customerSessionBeanlocal.createCustomer(new Customer("765432102", "Singapore", "customer4@gmail.com", "Singapore", "customer4", "password"));
            Customer customer5 = customerSessionBeanlocal.createCustomer(new Customer("765432103", "Singapore", "customer5@gmail.com", "Singapore", "customer5", "password"));

            Hardware valveindex = hardwareSessionBeanLocal.createNewHardware(new Hardware("1 year", "High responseiiveness, high display resolution",
                    "America", "Valve Index", "Virtual Reality Headset", "windows 10 8 gigs of ram 1 gig of free space", 100, 4.3), categoryEntityHardware.getCategoryId(), tagIdsPopular, company1.getUserId());

            Hardware hardware2 = hardwareSessionBeanLocal.createNewHardware(new Hardware("2 year", "2x2x2", "America", "hardware2", "key board", "NA", 110, 1.5), categoryEntityHardware.getCategoryId(), tagIdsPopular, company2.getUserId());
            Hardware hardware3 = hardwareSessionBeanLocal.createNewHardware(new Hardware("3 year", "2x2x2", "America", "hardware3", "monitor", "NA", 120, 2.5), categoryEntityHardware.getCategoryId(), tagIdsPopular, company3.getUserId());
            Hardware hardware4 = hardwareSessionBeanLocal.createNewHardware(new Hardware("1 year", "2x2x2", "America", "hardware4", "controller", "NA", 130, 3.5), categoryEntityHardware.getCategoryId(), tagIdsPopular, company4.getUserId());
            Hardware hardware5 = hardwareSessionBeanLocal.createNewHardware(new Hardware("2 year", "2x2x2", "America", "hardware5", "hard disk", "NA", 140, 4.5), categoryEntityHardware.getCategoryId(), tagIdsPopular, company5.getUserId());

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
            company1Products.add(portal);
            company1Products.add(valveindex);
            company1Products.add(softwaretool1);

            company1.setProducts(company1Products);
            companySessionBeanLocal.updateCompany(company1);

        } catch (SystemAdminUsernameExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | CreateNewTagException | CreateNewProductException | ProductSkuCodeExistException | CompanyNotFoundException | CompanyUsernameExistException | CustomerUsernameExistException ex) {
            ex.printStackTrace();
        }
    }

}
