package com.retail.inventory;

import com.retail.inventory.entity.Product;
import com.retail.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    ProductService productService;
//
//    /**
//     * 根据id查询商品
//     * @param id
//     * @return
//     */
//    public Product getProductById(Long id);
//    /**
//     * 添加商品
//     * @param product
//     * @return
//     */
//    public int addProduct(Product product);
//    /**
//     * 修改商品
//     * @param product
//     * @return
//     */
//    public int updateProduct(Product product);
//    /**
//     * 删除商品
//     * @param id
//     * @return
//     */
//    public int deleteProduct(int id);
    @Test
    void test02() {
        System.out.println("--------ListProduct-------");
        List<Product> list = productService.ListProduct();
        for (Product product : list) {
            System.out.println(product);
        }
        System.out.println("--------deleteProduct-------");
        int deleteProduct = productService.deleteProduct(8L);
        System.out.println("返回值：" + deleteProduct);

        System.out.println("--------ListProduct-------");
        list = productService.ListProduct();
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    void test01() {
//        System.out.println("--------getProductById-------");
//        Product productById = productService.getProductById(1L);
//        System.out.println(productById);

        System.out.println("--------addProduct-------");
        Product product = new Product();
        product.setBarcode("12345678900");
        product.setName("测试商品2");
        product.setPrice(new BigDecimal(10));
        product.setUnit("个");
        Long id = productService.addProduct(product);
        System.out.println("返回值(id)：" + id);
        Product productById1 = productService.getProductById(id);
        System.out.println("添加结果为" + productById1);

//        System.out.println("--------updateProduct-------");
//        productById1.setName("测试商品1");
//        int updateProduct = productService.updateProduct(productById1);
//        System.out.println("返回值：" + updateProduct);
//        Product productById2 = productService.getProductById((long)addProduct);
//        System.out.println("修改结果为" + productById2);
    }
}
