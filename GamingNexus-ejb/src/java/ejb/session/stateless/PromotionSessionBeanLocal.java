/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import entity.Promotion;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNotFoundException;

/**
 *
 * @author root
 */
@Local
public interface PromotionSessionBeanLocal {


    public void deletePromotion(long promotionID);

    public Promotion retrievePromotionById(long promotionID);

    public List<Promotion> retrivePromotionsByCompanyID(long companyID) throws CompanyNotFoundException;

    public void updatePromotion(Promotion promotion, List<Long> productsIdsListToBeUpdated);
    public List<Promotion> retrieveAllPromotions();

    public Promotion createPromotion(Promotion promotion, List<Long> listProductIDs);

    public Promotion createPromotion(Promotion promotion);
    
}
