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
            Tag tagEntityMOBA = tagSessionBeanLocal.createNewTagEntity(new Tag("MOBA"));
            Tag tagEntityPuzzle = tagSessionBeanLocal.createNewTagEntity(new Tag("Puzzle"));
            Tag tagEntityOpenworld = tagSessionBeanLocal.createNewTagEntity(new Tag("Openworld"));
            Tag tagEntityZombies = tagSessionBeanLocal.createNewTagEntity(new Tag("Zombies"));

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
            tags.add(tagEntitySingleplayer.getTagId());
            Game cs = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

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
            tags.add(tagEntityPuzzle.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game portal = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Dota 2";
            releaseDate = LocalDate.parse("2013-07-09", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 90687580;
            price = 0.00;
            description = "Dota is a competitive game of action and strategy played both professionally and casually by millions of passionate fans worldwide. Players pick from a pool of over a hundred heroes forming two teams of five players. Radiant heroes then battle their Dire counterparts to control a gorgeous fantasy landscape waging campaigns of cunning stealth and outright warfare.Irresistibly colorful on the surface Dota is a game of infinite depth and complexity. Every hero has an array of skills and abilities that combine with the skills of their allies in unexpected ways to ensure that no game is ever remotely alike. This is one of the reasons that the Dota phenomenon has continued to grow. Originating as a fan-made Warcraft 3 modification Dota was an instant underground hit. After coming to Valve the original community developers have bridged the gap to a more inclusive audience so that the rest of the world can experience the same core gameplay but with the level of polish that only Valve can provide.Get a taste of the game that has enthralled millions.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/570/header.jpg?t=1471028674";
            computerRequirements = "Minimum:OS: Windows 7 or newerProcessor: Dual core from Intel or AMD at 2.8 GHzMemory: 4 GB RAMGraphics: nVidia GeForce 8600/9600GT ATI/AMD Radeon HD2600/3600DirectX: Version 9.0cNetwork: Broadband Internet connectionStorage: 15 GB available spaceSound Card: DirectX Compatible";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityMOBA.getTagId());
            Game dota2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Team Fortress 2";
            releaseDate = LocalDate.parse("2007-10-10", formatter);
            parentAdvisory = false;
            averageRating = 92;
            sales = 37878812;
            price = 0.00;
            description = "The most fun you can have online - PC Gamer Is now FREE! Theres no catch! Play as much as you want as long as you like! The most highly-rated free game of all time! One of the most popular online action games of all time Team Fortress 2 delivers constant free updates--new game modes maps equipment and most importantly hats. Nine distinct classes provide a broad range of tactical abilities and personalities and lend themselves to a variety of player skills. New to TF? Dont sweat it! No matter what your style and experience weve got a character for you. Detailed training and offline practice modes will help you hone your skills before jumping into one of TF2s many game modes including Capture the Flag Control Point Payload Arena King of the Hill and more. Make a character your own! There are hundreds of weapons hats and more to collect craft buy and trade. Tweak your favorite class to suit your gameplay style and personal taste. You dont need to pay to win--virtually all of the items in the Mann Co. Store can also be found in-game.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/440/header.jpg?t=1473198172";
            computerRequirements = "Minimum:OS: Windows(r) 7 (32/64-bit)/Vista/XPProcessor: 1.7 GHz Processor or betterMemory: 512 MB RAMDirectX: Version 8.1Network: Broadband Internet connectionStorage: 15 GB available spaceAdditional Notes: Mouse Keyboard";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game teamFortress2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Counter-Strike: Global Offensive";
            releaseDate = LocalDate.parse("2012-08-21", formatter);
            parentAdvisory = false;
            averageRating = 83;
            sales = 25833156;
            price = 15.00;
            description = "Counter-Strike: Global Offensive (CS: GO) will expand upon the team-based action gameplay that it pioneered when it was launched 14 years ago.CS: GO features new maps characters and weapons and delivers updated versions of the classic CS content (de_dust etc.). In addition CS: GO will introduce new gameplay modes matchmaking leader boards and more.Counter-Strike took the gaming industry by surprise when the unlikely MOD became the most played online PC action game in the world almost immediately after its release in August 1999 said Doug Lombardi at Valve. For the past 12 years it has continued to be one of the most-played games in the world headline competitive gaming tournaments and selling over 25 million units worldwide across the franchise. CS: GO promises to expand on CS award-winning gameplay and deliver it to gamers on the PC as well as the next gen consoles and the Mac.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/730/header.jpg?t=1467065027";
            computerRequirements = "Minimum:OS: Windows(r) 7/Vista/XPProcessor: Intel(r) Core(tm) 2 Duo E6600 or AMD Phenom(tm) X3 8750 processor or betterMemory: 2 GB RAMGraphics: Video card must be 256 MB or more and should be a DirectX 9-compatible with support for Pixel Shader 3.0DirectX: Version 9.0cStorage: 8 GB available space";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game csgo = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Portal 2";
            releaseDate = LocalDate.parse("2011-04-18", formatter);
            parentAdvisory = false;
            averageRating = 95;
            sales = 8741499;
            price = 20.00;
            description = "Portal 2 draws from the award-winning formula of innovative gameplay story and music that earned the original Portal over 70 industry accolades and created a cult following.The single-player portion of Portal 2 introduces a cast of dynamic new characters a host of fresh puzzle elements and a much larger set of devious test chambers. Players will explore never-before-seen areas of the Aperture Science Labs and be reunited with GLaDOS the occasionally murderous computer companion who guided them through the original game.The games two-player cooperative mode features its own entirely separate campaign with a unique story test chambers and two new player characters. This new mode forces players to reconsider everything they thought they knew about portals. Success will require them to not just act cooperatively but to think cooperatively.Product FeaturesExtensive single player: Featuring next generation gameplay and a wildly-engrossing story.Complete two-person co-op: Multiplayer game featuring its own dedicated story characters and gameplay.Advanced physics: Allows for the creation of a whole new range of interesting challenges producing a much larger but not harder game.Original music.Massive sequel: The original Portal was named 2007s Game of the Year by over 30 publications worldwide. Editing Tools: Portal 2 editing tools will be included.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/620/header.jpg?t=1452903431";
            computerRequirements = "Minimum:OS: Windows 7 / Vista / XPProcessor: 3.0 GHz P4 Dual Core 2.0 (or higher) or AMD64X2 (or higher)Memory: 2 GB RAMGraphics: Video card must be 128 MB or more and with support for Pixel Shader 2.0b (ATI Radeon X800 or higher / NVIDIA GeForce 7600 or higher / Intel HD Graphics 2000 or higher).DirectX: Version 9.0cStorage: 8 GB available spaceSound Card: DirectX 9.0c compatible";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPuzzle.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game portal2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Day of Defeat";
            releaseDate = LocalDate.parse("2003-05-01", formatter);
            parentAdvisory = false;
            averageRating = 79;
            sales = 7621102;
            price = 5.25;
            description = "Enlist in an intense brand of Axis vs. Allied teamplay set in the WWII European Theatre of Operations. Players assume the role of light/assault/heavy infantry sniper or machine-gunner class each with a unique arsenal of historical weaponry at their disposal. Missions are based on key historical operations. And as war rages players must work together with their squad to accomplish a variety of mission-specific objectives.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/30/header.jpg?t=1447350812";
            computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            Game dayofdefeat = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Left 4 Dead";
            releaseDate = LocalDate.parse("2008-11-17", formatter);
            parentAdvisory = false;
            averageRating = 89;
            sales = 4473517;
            price = 10.00;
            description = "From Valve (the creators of Counter-Strike Half-Life and more) comes Left 4 Dead a co-op action horror game for the PC and Xbox 360 that casts up to four players in an epic struggle for survival against swarming zombie hordes and terrifying mutant monsters. Set in the immediate aftermath of the zombie apocalypse L4Ds survival co-op mode lets you blast a path through the infected in four unique movies guiding your survivors across the rooftops of an abandoned metropolis through rural ghost towns and pitch-black forests in your quest to escape a devastated Ground Zero crawling with infected enemies. Each movie is comprised of five large maps and can be played by one to four human players with an emphasis on team-based strategy and objectives.                     New technology dubbed the AI Director is used to generate a unique gameplay experience every time you play. The Director tailors the frequency and ferocity of the zombie attacks to your performance putting you in the middle of a fast-paced but not overwhelming Hollywood horror movie.                    Addictive single player co-op and multiplayer action gameplay from the makers of Counter-Strike and Half-Life                    Versus Mode lets you compete four-on-four with friends playing as a human trying to get rescued or as a zombie boss monster that will stop at nothing to destroy them.See how long you and your friends can hold out against the infected horde in the new Survival ModeAn advanced AI director dynamically creates intense and unique experiences every time the game is played                     20 maps 10 weapons and unlimited possibilities in four sprawling movies                     Matchmaking stats rankings and awards system drive collaborative play                     Designers Commentary allows gamers to go behind the scenes of the game                     Powered by Source and Steam                     THE SACRIFICEThe Sacrifice is  the new add-on for Left 4 Dead.The Sacrifice is the prequel to The Passing and takes place from the L4D Survivors perspective as they make their way South. In addition to advancing the story The Sacrifice introduces a new style finale featuring Sacrificial Gameplay where players get to decide who will give their life so the others may live.In The Sacrifice for Left 4 Dead owners receive The Sacrifice campaign playable in Campaign Versus  and Survival modes.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/500/header.jpg?t=1447903113";
            computerRequirements = "Supported OS: Windows(r) 7 32/64-bit / Vista 32/64 / XP  Processor: Pentium 4 3.0GHz  Memory: 1 GB  Graphics: 128 MB Shader model 2.0 ATI 9600 NVidia 6600 or better                     Hard Drive: At least 7.5 GB of free space  Sound Card: DirectX 9.0c compatible sound card";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityZombies.getTagId());
            Game left4dead = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Left 4 Dead 2";
            releaseDate = LocalDate.parse("2009-11-16", formatter);
            parentAdvisory = false;
            averageRating = 89;
            sales = 15574539;
            price = 10.00;
            description = "Set in the zombie apocalypse, Left 4 Dead 2 (L4D2) is the highly anticipated sequel to the award-winning Left 4 Dead, the #1 co-op game of 2008. This co-operative action horror FPS takes you and your friends through the cities, swamps and cemeteries of the Deep South, from Savannah to New Orleans across five expansive campaigns.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/550/header.jpg?t=1467131981";
            computerRequirements = "Minimum:OS: Windows(r) 7 32/64-bit / Vista 32/64 / XPProcessor: Pentium 4 3.0GHzMemory: 2 GB RAMGraphics: Video card with 128 MB Shader model 2.0. ATI X800 NVidia 6600 or betterDirectX: Version 9.0cStorage: 13 GB available spaceSound Card: DirectX 9.0c compatible sound card";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityZombies.getTagId());
            Game left4dead2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "DOOM II: Hell on Earth";
            releaseDate = LocalDate.parse("2007-08-03", formatter);
            parentAdvisory = false;
            averageRating = 83;
            sales = 443890;
            price = 5.00;
            description = "Let the Obsession begin. Again.This time the entire forces of the netherworld have overrun Earth. To save her you must descend into the stygian depths of Hell itself!Battle mightier nastier deadlier demons and monsters. Use more powerful weapons. Survive more mind-blowing explosions and more of the bloodiest fiercest most awesome blastfest ever!Play DOOM II solo with two people over a modem or with up to four players over a LAN (supporting IPX protocol). No matter which way you choose get ready for adrenaline-pumping action-packed excitement thats sure to give your heart a real workout.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/2300/header.jpg?t=1449848674";
            computerRequirements = "Minimum: A 100% Windows XP/Vista-compatible computer system";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityAction.getTagId());
            Game doom2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Prototype";
            releaseDate = LocalDate.parse("2009-06-10", formatter);
            parentAdvisory = true;
            averageRating = 79;
            sales = 482271;
            price = 20.00;
            description = "You are the Prototype Alex Mercer a man without memory armed with amazing shape-shifting abilities hunting your way to the heart of the conspiracy which created you; making those responsible pay.                    Fast & Deadly Shape-Shifting Combat: Reconfigure your body to the situation at hand. From Claws to Blades to Hammers to Whips choose the right weapon for the situation. Change to a shield or armor for defense or use advanced sensory powers (thermal vision infected vision) to track your enemies                    Over-the-Top Locomotion & Agility: Seamlessly and fluidly bound from building to building run up walls bounce off cars and everything in your path. Adaptive parkour lets you move freely through the open-world environments of New York City.                    Unique Disguising Abilities: Consume anyone at anytime take on their appearances and assume their memories and special abilities.                    Deep Conspiracy-Driven Storyline: Wake up with no memory of the past...just mysterious powers and a link to a town in Idaho. Delve into the mysteries of your origin the true nature of your power and your part in a conspiracy 40 years in the making.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/10150/header.jpg?t=1464729751";
            computerRequirements = "OS: Windows(r) XP (with Service Pack 3) or Windows Vista(r)                    Processor: Intel(r) Core(tm)2 Duo 2.6 GHz or AMD Athlon(tm) 64 X2 4000+ or better                    Memory: Vista 2 GB RAM / XP 1 GB RAM                    Graphics: All NVIDIA(r) GeForce(r) 7800 GT 256 MB and better chipsets. All ATI Radeon(tm) X1800 256 MB and better chipsets                    DirectX(r): Microsoft DirectX 9.0c                    Hard Drive: 8GB of free hard drive space                    Sound: DirectX(r) 9.0c compliant sound card";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game prototype = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Sid Meier's Civilization VI";
            releaseDate = LocalDate.parse("2016-10-21", formatter);
            parentAdvisory = false;
            averageRating = 88;
            sales = 1363057;
            price = 74.90;
            description = "Civilization VI offers new ways to engage with your world: cities now physically expand across the map, active research in technology and culture unlocks new potential, and competing leaders will pursue their own agendas based on their historical traits as you race for one of five ways to achieve victory in the game.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/289070/header.jpg?t=1477354938";
            computerRequirements = "Minimum:OS: Windows 7x64 / Windows 8.1x64 / Windows 10x64Processor: Intel Core i3 2.5 Ghz or AMD Phenom II 2.6 Ghz or greaterMemory: 4 GB RAMGraphics: 1 GB & AMD 5570 or nVidia 450DirectX: Version 11Storage: 12 GB available spaceSound Card: DirectX Compatible Sound Device";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game civVI = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Sid Meier's Civilization V";
            releaseDate = LocalDate.parse("2010-09-21", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 9711421;
            price = 29.00;
            description = "The Flagship Turn-Based Strategy Game ReturnsBecome Ruler of the World by establishing and leading a civilization from the dawn of man into the space age: Wage war conduct diplomacy discover new technologies go head-to-head with some of historys greatest leaders and build the most powerful empire the world has ever known.INVITING PRESENTATION: Jump right in and play at your own pace with an intuitive interface that eases new players into the game. Civ veterans will appreciate the depth detail and control that are highlights of the series.BELIEVABLE WORLD: Ultra realistic graphics showcase lush landscapes for you to explore battle over and claim as your own. Art deco influences abound in the menus and icons in the most well-designed Civ ever developed.COMMUNITY & MULTIPLAYER: Compete with Civ players all over the world or locally in LAN matches mod* the game in unprecedented ways and install mods directly from an in-game community hub without ever leaving the game. Civilization V brings community to the forefront.WIDE SYSTEM COMPATIBILITY: Civilization V operates on many different systems from high end DX11 desktops to many laptops. Enjoy unlimited installations on multiple PCs with your Steam account and take your Civ V experience with you everywhere you go.ALL NEW FEATURES: A new hex-based gameplay grid opens up exciting new combat and build strategies. City States become a new resource in your diplomatic battleground. An improved diplomacy system allows you to negotiate with fully interactive leaders. Custom music scores and orchestral recordings give Civ V the level of polish and quality you expect from the series.SOCIALLY RESPONSIBLE: 2K Games is donating a total of $250000 to four education based charities and users choices will determine how the money is dispersed: simply select your choice from the pre-selected charities during the install process.+*Modding SDK available post launch as a free download.+ Charity selection available until Dec. 31 2010. Not valid in all territories.    Note: The Mac and Linux + SteamOS versions of Sid Meiers Civilization V are available in English French Italian German and Spanish only.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/8930/header.jpg?t=1473370865";
            computerRequirements = "Minimum:OS: Windows(r) Vista SP2/ Windows(r) 7 Processor: Intel Core 2 Duo 1.8 GHz or AMD Athlon X2 64 2.0 GHzMemory: 2GB RAM Graphics:256 MB ATI HD2600 XT or better 256 MB nVidia 7900 GS or better or Core i3 or better integrated graphics DirectX(r): DirectX(r) version 9.0c Hard Drive: 8 GB Free Sound: DirectX 9.0c-compatible sound card Note: Optimized for the touch-screen Ultrabook(tm) device";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game civV = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Sid Meier's Civilization IV";
            releaseDate = LocalDate.parse("2005-10-25", formatter);
            parentAdvisory = false;
            averageRating = 94;
            sales = 1422601;
            price = 20.00;
            description = "With over 6 million units sold and unprecedented critical acclaim from fans and press around the world, Sid Meier's Civilization is recognized as one of the greatest PC game franchises of all-time. Now, Sid Meier and Firaxis Games will take this incredibly fun and addictive game to new heights by adding new ways to play and win, new tools to manage and expand your civilization, all-new easy to use mod capabilities and intense multiplayer modes and options*. Civilization IV will come to life like never before in a beautifully detailed, living 3D world that will elevate the gameplay experience to a whole new level. Civilization IV has already been heralded as one of the top ten games of 2005, and a must-have for gamers around the globe!";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/3900/header.jpg?t=1447366360";
            computerRequirements = "Minimum: Windows 2000/XP 1.2GHz Intel Pentium 4 or AMD Athlon processor or equivalent 256MB RAM 64 MB Video Card w/ Hardware T&L (GeForce 2/Radeon 7500 or better) DirectX7 compatible sound card 1.7GB of free hard drive space DirectX9.0c (included)Recommended: Windows 2000/XP 1.8GHz Intel Pentium 4 or AMD Athlon processor or equivalent/better 512 MB RAM 128 MB Video Card w/ DirectX 8 support (pixel and vertex shaders) DirectX7 compatible sound card 1.7GB of free hard drive space DirectX9.0c (included)";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game civIV = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Grand Theft Auto: Vice City";
            releaseDate = LocalDate.parse("2003-05-13", formatter);
            parentAdvisory = true;
            averageRating = 94;
            sales = 1830263;
            price = 10.50;
            description = "Welcome to Vice City. Welcome to the 1980s.From the decade of big hair excess and pastel suits comes a story of one mans rise to the top of the criminal pile. Vice City a huge urban sprawl ranging from the beach to the swamps and the glitz to the ghetto was one of the most varied complete and alive digital cities ever created. Combining open-world gameplay with a character driven narrative you arrive in a town brimming with delights and degradation and given the opportunity to take it over as you choose.Having just made it back onto the streets of Liberty City after a long stretch in maximum security Tommy Vercetti is sent to Vice City by his old boss Sonny Forelli. They were understandably nervous about his re-appearance in Liberty City so a trip down south seemed like a good idea. But all does not go smoothly upon his arrival in the glamorous hedonistic metropolis of Vice City. Hes set up and is left with no money and no merchandise. Sonny wants his money back but the biker gangs Cuban gangsters and corrupt politicians stand in his way. Most of Vice City seems to want Tommy dead. His only answer is to fight back and take over the city himself.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/12110/header.jpg?t=1457026681";
            computerRequirements = "Minimum:OS: Microsoft(r) Windows(r) 2000/XPProcessor: 800 MHz Intel Pentium III or 800 MHz AMD Athlon or 1.2GHz Intel Celeron or 1.2 GHz AMD Duron processorMemory: 128 MB of RAMGraphics: 32 MB video card with DirectX 9.0 compatible drivers (GeForce or better)DirectX Version: Microsoft DirectX(r) 9.0Hard Drive: 915 MB of free hard disk space (+ 635 MB if video card does NOT support DirectX Texture Compression)Sound Card: Sound Card with DirectX 9.0Recommended:Processor: Intel Pentium IV or AMD Athlon XP processor 256(+) MB of RAMMemory: 1.55 GB of free hard disk space(+ 635 MB if video card does NOT support DirectX Texture Compression)Graphics: 64(+) MB video card with DirectX 9.0 compatible drivers (GeForce 3 / Radeon 8500 or better with DirectX Texture Compression support)";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game gtavicecity = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Grand Theft Auto V";
            releaseDate = LocalDate.parse("2015-04-13", formatter);
            parentAdvisory = true;
            averageRating = 96;
            sales = 5886074;
            price = 40.00;
            description = "When a young street hustler a retired bank robber and a terrifying psychopath find themselves entangled with some of the most frightening and deranged elements of the criminal underworld the U.S. government and the entertainment industry they must pull off a series of dangerous heists to survive in a ruthless city in which they can trust nobody least of all each other. Grand Theft Auto V for PC offers players the option to explore the award-winning world of Los Santos and Blaine County in resolutions of up to 4k and beyond as well as the chance to experience the game running at 60 frames per second.  The game offers players a huge range of PC-specific customization options including over 25 separate configurable settings for texture quality shaders tessellation anti-aliasing and more as well as support and extensive customization for mouse and keyboard controls. Additional options include a population density slider to control car and pedestrian traffic as well as dual and triple monitor support 3D compatibility and plug-and-play controller support.   Grand Theft Auto V for PC also includes Grand Theft Auto Online with support for 30 players and two spectators. Grand Theft Auto Online for PC will include all existing gameplay upgrades and Rockstar-created content released since the launch of Grand Theft Auto Online including Heists and Adversary modes. The PC version of Grand Theft Auto V and Grand Theft Auto Online features First Person Mode giving players the chance to explore the incredibly detailed world of Los Santos and Blaine County in an entirely new way. Grand Theft Auto V for PC also brings the debut of the Rockstar Editor a powerful suite of creative tools to quickly and easily capture edit and share game footage from within Grand Theft Auto V and Grand Theft Auto Online. The Rockstar Editors Director Mode allows players the ability to stage their own scenes using prominent story characters pedestrians and even animals to bring their vision to life. Along with advanced camera manipulation and editing effects including fast and slow motion and an array of camera filters players can add their own music using songs from GTAV radio stations or dynamically control the intensity of the games score. Completed videos can be uploaded directly from the Rockstar Editor to YouTube and the Rockstar Games Social Club for easy sharing.   Soundtrack artists The Alchemist and Oh No return as hosts of the new radio station The Lab FM. The station features new and exclusive music from the production duo based on and inspired by the games original soundtrack. Collaborating guest artists include Earl Sweatshirt Freddie Gibbs Little Dragon Killer Mike Sam Herring from Future Islands and more. Players can also discover Los Santos and Blaine County while enjoying their own music through Self Radio a new radio station that will host player-created custom soundtracks. Existing players on PlayStation(r)3 PlayStation(r)4 Xbox 360 and Xbox One are able to transfer their Grand Theft Auto Online characters and progression to PC.  For more information please visit rockstargames.com/gtaonline/charactertransfer.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/271590/header.jpg?t=1470324156";
            computerRequirements = "Minimum:OS: Windows 8.1 64 Bit Windows 8 64 Bit Windows 7 64 Bit Service Pack 1 Windows Vista 64 Bit Service Pack 2* (*NVIDIA video card recommended if running Vista OS)Processor: Intel Core 2 Quad CPU Q6600 @ 2.40GHz (4 CPUs) / AMD Phenom 9850 Quad-Core Processor (4 CPUs) @ 2.5GHzMemory: 4 GB RAMGraphics: NVIDIA 9800 GT 1GB / AMD HD 4870 1GB (DX 10 10.1 11)Storage: 65 GB available spaceSound Card: 100% DirectX 10 compatibleAdditional Notes: Over time downloadable content and programming changes will change the system requirements for this game.  Please refer to your hardware manufacturer and www.rockstargames.com/support for current compatibility information. Some system components such as mobile chipsets integrated and AGP graphics cards may be incompatible. Unlisted specifications may not be supported by publisher.     Other requirements:  Installation and online play requires log-in to Rockstar Games Social Club (13+) network; internet connection required for activation online play and periodic entitlement verification; software installations required including Rockstar Games Social Club platform DirectX  Chromium and Microsoft Visual C++ 2008 sp1 Redistributable Package and authentication software that recognizes certain hardware attributes for entitlement digital rights management system and other support purposes.     SINGLE USE SERIAL CODE REGISTRATION VIA INTERNET REQUIRED; REGISTRATION IS LIMITED TO ONE ROCKSTAR GAMES SOCIAL CLUB ACCOUNT (13+) PER SERIAL CODE; ONLY ONE PC LOG-IN ALLOWED PER SOCIAL CLUB ACCOUNT AT ANY TIME; SERIAL CODE(S) ARE NON-TRANSFERABLE ONCE USED; SOCIAL CLUB ACCOUNTS ARE NON-TRANSFERABLE.  Partner Requirements:  Please check the terms of service of this site before purchasing this software.";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntityAction.getTagId());
            Game gtaV = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Call of Duty: Black Ops III";
            releaseDate = LocalDate.parse("2015-10-05", formatter);
            parentAdvisory = true;
            averageRating = 90;
            sales = 1378660;
            price = 59.99;
            description = "Treyarch developer of the two most-played games in Call of Duty(r) history returns with Call of Duty(r): Black Ops III. For the first time with three-years of development the revered award-winning studio has created its first title for next-gen hardware in the critically acclaimed Black Ops series. Welcome to Call of Duty: Black Ops 3 a dark twisted future where a new breed of Black Ops soldier emerges and the lines are blurred between our own humanity and the technology we created to stay ahead in a world where cutting-edge military robotics define warfare. Call of Duty: Black Ops 3 combines three unique game modes: Campaign Multiplayer and Zombies providing fans with the deepest and most ambitious Call of Duty ever. The Campaign has been designed as a co-op game that can be played with up to 4 players online or as a solo cinematic thrill-ride. Multiplayer will be the franchises deepest most rewarding and most engaging to date with new ways to rank up customize and gear up for battle. And Zombies delivers an all-new mind-blowing experience with its own dedicated narrative. Call of Duty: Black Ops 3 can be played entirely online and for the first time each of the offerings has its own unique player XP and progression systems. The title ushers in an unprecedented level of innovation including jaw-dropping environments never before experienced weaponry and abilities and the introduction of a new improved fluid movement system. All of this is brought to life by advanced technology custom crafted for this title including new AI and animation systems and graphics that redefine the standards Call of Duty fans have come to expect from the critically-acclaimed series with cutting edge lighting systems and visual effects.INTRODUCING A NEW ERA OF BLACK OPS:Call of Duty: Black Ops 3 deploys its players into a future where bio-technology has enabled a new breed of Black Ops soldier. Players are now always on and always connected to the intelligence grid and their fellow operatives during battle. In a world more divided than ever this elite squad consists of men and women who have enhanced their combat capabilities to fight faster stronger and smarter. Every soldier has to make difficult decisions and visit dark places in this engaging gritty narrative.A CALL OF DUTY CAMPAIGN UNLIKE ANYTHING BEFORE IT:Treyarch elevates the Call of Duty social gaming experience by delivering a campaign with the ability to play cooperatively with up to four players online using the same battle-tested network infrastructure and social systems that support its world-class Multiplayer and Zombies game modes. Designed for co-op and re-playability players encounter all the epic cinematic gameplay moments Call of Duty is known for delivering as well as new open-area arena-style gameplay elements designed to allow players to approach the game with a different strategy each time they play. And now every player is completely customizable: from weapons and loadouts to abilities and outfits all with full progression systems and a personalized armory to show off accomplishments providing a constantly-evolving campaign experience.PREPARE FOR A LEVELED-UP MULTIPLAYER:With Black Ops 3 Treyarch premieres a new momentum-based chained-movement system allowing players to fluidly move through the environment with finesse using controlled thrust jumps slides and mantling abilities in a multitude of combinations all while maintaining complete control over your weapon at all times. Maps are designed from the ground-up for the new movement system allowing players to be successful with traditional movement as well as advanced tactics and maneuvers. Black Ops 3 multiplayer also introduces the new Specialist character system which allows players to rank up and master each specific characters battle-hardened capabilities and weapons. With this addition to Traditional and Weapons XP progressions systems Black Ops 3 multiplayer gives players three different ways to rank up.FIGHT THE UNDEAD IN AN ALL-NEW HORROR STORY:No Treyarch title would be complete without its signature Zombies offering - a full-game experience with its own distinct storyline right out of the box. Black Ops 3 Zombies is the most immersive and ambitious Call of Duty Zombies to date with a full XP-based progression system for players that adds depth and re-playability to the engaging gameplay Zombies fans have come to expect.Call of Duty: Black Ops 3 delivers the ultimate 3-games-in-1 experience.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/311210/header.jpg?t=1473725004";
            computerRequirements = "Minimum:OS: Windows 7 64-Bit / Windows 8 64-Bit / Windows 8.1 64-BitProcessor: Intel(r) Core(tm) i3-530 @ 2.93 GHz / AMD Phenom(tm) II X4 810 @ 2.60 GHzMemory: 6 GB RAMGraphics: NVIDIA(r) GeForce(r) GTX 470 @ 1GB / ATI(r) Radeon(tm) HD 6970 @ 1GBDirectX: Version 11Network: Broadband Internet connectionStorage: 60 GB available spaceSound Card: DirectX Compatible";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityFPS.getTagId());
            Game callofdutyblackops3 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Half-Life";
            releaseDate = LocalDate.parse("1998-11-08", formatter);
            parentAdvisory = false;
            averageRating = 96;
            sales = 5927504;
            price = 10.00;
            description = "Named Game of the Year by over 50 publications Valves debut title blends action and adventure with award-winning technology to create a frighteningly realistic world where players must think to survive. Also includes an exciting multiplayer mode that allows you to play against friends and enemies around the world.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/70/header.jpg?t=1447890508";
            computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game halflife = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Half-Life 2";
            releaseDate = LocalDate.parse("2004-11-16", formatter);
            parentAdvisory = false;
            averageRating = 96;
            sales = 9901173;
            price = 10.00;
            description = "Half-Life 2 opens the door to a world where the player's presence affects everything around him, from the physical environment to the behaviors even the emotions of both friends and enemies. The player again picks up the crowbar of research scientist Gordon Freeman, who finds himself on an alien-infested Earth being picked to the bone, its resources depleted, its populace dwindling. Freeman is thrust into the unenviable role of rescuing the world from the wrong he unleashed back at Black Mesa. And a lot of people he cares about are counting on him.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/220/header.jpg?t=1456860366";
            computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game halflife2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Plants vs. Zombies: Game of the Year";
            releaseDate = LocalDate.parse("2009-05-06", formatter);
            parentAdvisory = false;
            averageRating = 87;
            sales = 1169180;
            price = 6.50;
            description = "Zombies are invading your home, and the only defense is your arsenal of plants! Armed with an alien nursery-worth of zombie-zapping plants like peashooters and cherry bombs, you'll need to think fast and plant faster to stop dozens of types of zombies dead in their tracks. Obstacles like a setting sun, creeping fog and a swimming pool add to the challenge, and with five game modes to dig into, the fun never dies!";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/3590/header.jpg?t=1447351772";
            computerRequirements = "OS: Windows XP/Vista/7                    Processor: 1.2GHz+ processor                    Memory: 1GB of RAM                    Graphics: 128MB of video memory 16-bit or 32-bit color quality                    DirectX: DirectX 8 or later                    Hard Drive: 65+MB of free hard drive space                    Sound: DirectX-compatible sound";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityZombies.getTagId());
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game plantsvszombies = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

            name = "Darksiders Warmastered Edition";
            releaseDate = LocalDate.parse("2016-11-29", formatter);
            parentAdvisory = false;
            averageRating = 87;
            sales = 2548378;
            price = 19.99;
            description = "Deceived by the forces of evil into prematurely bringing about the end of the world, War – the first Horseman of the Apocalypse – stands accused of breaking the sacred law by inciting a war between Heaven and Hell.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/462780/header.jpg?t=1481150698";
            computerRequirements = "Minimum:OS: Windows 7 Windows 8 Windows 10Processor: Intel or AMD Dual Core CPUMemory: 4 GB RAMGraphics: DirectX 10 Feature Level AMD or NVIDIA Card with 1 GB VRAMDirectX: Version 11Storage: 36 GB available space";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            tags.add(tagEntityRPG.getTagId());
            Game darksidersWarmasteredEdition = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());

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
