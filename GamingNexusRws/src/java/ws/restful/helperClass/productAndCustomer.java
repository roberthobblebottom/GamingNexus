/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.helperClass;

/**
 *
 * @author ufoya
 */
public class productAndCustomer {
    
    private Long customerId;
    private Long productId;

    public productAndCustomer() {
    }

    public productAndCustomer(Integer customerId, Integer productId) {
        this.customerId = customerId.longValue();
        this.productId = productId.longValue();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    
    
}
