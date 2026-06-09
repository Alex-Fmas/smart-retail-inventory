package com.retail.inventory.mapper;

import com.retail.inventory.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper{
    public Product getProductById(Long id);

    public int addProduct(Product product);

    public int updateProduct(Product product);

    public int deleteProduct(int id);

    public List<Product> ListProduct();
}
