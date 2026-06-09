package com.retail.inventory.service;

import com.retail.inventory.entity.Product;

import java.util.List;

public interface ProductService {
    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    public Product getProductById(Long id);
    /**
     * 添加商品
     * @param product
     * @return
     */
    public Long addProduct(Product product);
    /**
     * 修改商品
     * @param product
     * @return
     */
    public int updateProduct(Product product);
    /**
     * 删除商品
     * @param id
     * @return
     */
    public int deleteProduct(int id);
    /**
     * 查询所有商品
     * @return
     */
    public List<Product> ListProduct();
}
