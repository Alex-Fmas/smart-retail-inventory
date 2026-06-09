package com.retail.inventory.service.impl;

import com.retail.inventory.entity.Product;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.ProductMapper;
import com.retail.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public Product getProductById(Long id) {
        Product productById = productMapper.getProductById(id);
        return productById;
    }

    @Override
    public Long addProduct(Product product) {
        int result = productMapper.addProduct(product);
        return product.getId();
    }

    @Override
    public int updateProduct(Product product) {
        if(product.getId() == null) {
            throw new BizException(BizExceptionEnum.ID_NOT_NULL);
        }
        int result = productMapper.updateProduct(product);
        return result;
    }

    @Override
    public int deleteProduct(int id) {
        int result = productMapper.deleteProduct(id);
        return result;
    }

    @Override
    public List<Product> ListProduct() {
        List<Product> products = productMapper.ListProduct();
        return products;
    }
}
