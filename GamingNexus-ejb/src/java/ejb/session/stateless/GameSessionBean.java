package ejb.session.stateless;

import entity.Category;
import entity.Company;
import entity.Game;
import entity.Product;
import entity.Tag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

/**
 *
 * @author jinyichen
 */
@Stateless
public class GameSessionBean implements GameSessionBeanLocal {

    @EJB
    private SaleTransactionSessionBeanLocal saleTransactionSessionBeanLocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public GameSessionBean() {

    }

    @Override
    public Game createNewGame(Game newGame, Long categoryId, List<Long> tagIds, Long CompanyId) throws ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException, CreateNewProductException, CompanyNotFoundException {
        try {
            if (categoryId == null) {
                throw new CreateNewProductException("The new product must be associated a leaf category");
            }
            Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

            if (!category.getSubCategories().isEmpty()) {
                throw new CreateNewProductException("Selected category for the new product is not a leaf category");
            }

            if (CompanyId == null) {
                throw new CreateNewProductException("The new product must be associated a company");
            }
            Company company = companySessionBeanLocal.retrieveCompanyById(categoryId);

            em.persist(newGame);
            newGame.setCategory(category);
            newGame.setCompany(company);

            if (tagIds != null && (!tagIds.isEmpty())) {
                for (Long tagId : tagIds) {
                    Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    newGame.addTag(tag);
                }
            }
            em.flush();
            return newGame;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ProductSkuCodeExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } catch (CategoryNotFoundException | TagNotFoundException ex) {
            throw new CreateNewProductException("An error has occurred while creating the new product: " + ex.getMessage());
        }
    }

    @Override
    public List<Game> retrieveAllGames() {
        Query query = em.createQuery("SELECT g FROM Game g ORDER BY g.averageRating ASC");
        List<Game> games = query.getResultList();

        for (Game game : games) {
            game.getCategory();
            game.getTags().size();
        }

        return games;
    }

    @Override
    public List<Game> searchGamesByName(String searchString) {
        Query query = em.createQuery("SELECT g FROM Game g WHERE g.name LIKE :inSearchString");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Game> games = query.getResultList();

        for (Game game : games) {
            game.getCategory();
            game.getTags().size();
        }

        return games;
    }

    @Override
    public List<Product> filterProductsByCategory(Long categoryId) throws CategoryNotFoundException {
        List<Product> productEntities = new ArrayList<>();
        Category categoryEntity = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

        if (categoryEntity.getSubCategories().isEmpty()) {
            productEntities = categoryEntity.getProducts();
        } else {
            for (Category subCategoryEntity : categoryEntity.getSubCategories()) {
                productEntities.addAll(addSubCategoryProducts(subCategoryEntity));
            }
        }

        for (Product productEntity : productEntities) {
            productEntity.getCategory();
            productEntity.getTags().size();
        }

        Collections.sort(productEntities, new Comparator<Product>() {
            public int compare(Product pe1, Product pe2) {
                return pe1.getProductId().compareTo(pe2.getProductId());
            }
        });

        return productEntities;
    }

    public List<Product> filterProductsByTags(List<Long> tagIds, String condition) {
        List<Product> productEntities = new ArrayList<>();

        if (tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return productEntities;
        } else {
            if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT pe FROM Product pe, IN (pe.tags) te WHERE te.tagID IN :inTagIDs ORDER BY pe.productID ASC");
                query.setParameter("inTagIDs", tagIds);
                productEntities = query.getResultList();
            } else // AND
            {
                String selectClause = "SELECT pe FROM ProductEntity pe";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;

                for (Long tagId : tagIds) {
                    selectClause += ", IN (pe.tagEntities) te" + tagCount;

                    if (firstTag) {
                        whereClause = "WHERE te1.tagId = " + tagId;
                        firstTag = false;
                    } else {
                        whereClause += " AND te" + tagCount + ".tagId = " + tagId;
                    }

                    tagCount++;
                }

                String jpql = selectClause + " " + whereClause + " ORDER BY pe.skuCode ASC";
                Query query = em.createQuery(jpql);
                productEntities = query.getResultList();
            }

            for (Product productEntity : productEntities) {
                productEntity.getCategory();
                productEntity.getTags().size();
            }

            Collections.sort(productEntities, new Comparator<Product>() {
                public int compare(Product pe1, Product pe2) {
                    return pe1.getProductId().compareTo(pe2.getProductId());
                }
            });

            return productEntities;
        }
    }

    @Override
    public Game retrieveGamebyId(Long gameId) throws ProductNotFoundException {
        Game game = em.find(Game.class, gameId);

        if (game != null) {
            game.getCategory();
            game.getTags().size();

            return game;
        } else {
            throw new ProductNotFoundException("Product ID " + game + " does not exist!");
        }
    }

    @Override
    public void updateGame(Game game, Long categoryId, List<Long> tagIds) throws ProductNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateProductException, InputDataValidationException {
        if (game != null && game.getProductId() != null) {
            Game gameToBeUpdated = retrieveGamebyId(game.getProductId());

            if (categoryId != null && (!gameToBeUpdated.getCategory().getCategoryId().equals(categoryId))) {
                Category categoryEntityToUpdate = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                if (!categoryEntityToUpdate.getSubCategories().isEmpty()) {
                    throw new UpdateProductException("Selected category for the new product is not a leaf category");
                }

                gameToBeUpdated.setCategory(categoryEntityToUpdate);
            }
            if (tagIds != null) {
                for (Tag tagEntity : gameToBeUpdated.getTags()) {
                    tagEntity.getProducts().remove(gameToBeUpdated);
                }
                gameToBeUpdated.getTags().clear();
                for (Long tagId : tagIds) {
                    Tag tagEntity = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    gameToBeUpdated.addTag(tagEntity);
                }
            }
            gameToBeUpdated.setName(game.getName());
            gameToBeUpdated.setDescription(game.getDescription());
            gameToBeUpdated.setComputerRequirements(game.getComputerRequirements());
            gameToBeUpdated.setPrice(game.getPrice());
            gameToBeUpdated.setCompany(game.getCompany());
            gameToBeUpdated.setAverageRating((game.getAverageRating()));
            gameToBeUpdated.setCartItems(game.getCartItems());
            //  gameToBeUpdated.setCategory(game.getCategory());
            gameToBeUpdated.setForums(game.getForums());
            gameToBeUpdated.setGameAccounts(game.getGameAccounts());
            gameToBeUpdated.setGamePicturesURLs(game.getGamePicturesURLs());
            gameToBeUpdated.setOwnedItems(game.getOwnedItems());
            gameToBeUpdated.setParentAdvisory(game.getParentAdvisory());
            gameToBeUpdated.setPromotions(game.getPromotions());
            gameToBeUpdated.setRatings(game.getRatings());
            // gameToBeUpdated.setTags(game.getTags());

        } else {
            throw new ProductNotFoundException("Product ID not provided for product to be updated");
        }
    }

    /*
    public void deleteProduct(Long productId) throws ProductNotFoundException, DeleteProductException {
        ProductEntity productEntityToRemove = retrieveProductByProductId(productId);

        List<SaleTransactionLineItemEntity> saleTransactionLineItemEntities = saleTransactionEntitySessionBeanLocal.retrieveSaleTransactionLineItemsByProductId(productId);

        if (saleTransactionLineItemEntities.isEmpty()) {
            entityManager.remove(productEntityToRemove);
        } else {
            throw new DeleteProductException("Product ID " + productId + " is associated with existing sale transaction line item(s) and cannot be deleted!");
        }
    }
     */
    private List<Product> addSubCategoryProducts(Category categoryEntity) {
        List<Product> productEntities = new ArrayList<>();

        if (categoryEntity.getSubCategories().isEmpty()) {
            return categoryEntity.getProducts();
        } else {
            for (Category subCategoryEntity : categoryEntity.getSubCategories()) {
                productEntities.addAll(addSubCategoryProducts(subCategoryEntity));
            }

            return productEntities;
        }
    }
}
