package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.context.CurrentUserHolder;
import com.retail.inventory.dto.product.ProductAddDTO;
import com.retail.inventory.dto.product.ProductUpdateDTO;
import com.retail.inventory.entity.Product;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.service.ProductService;
import com.retail.inventory.vo.product.ProductVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    /**
     * 根据ID查询商品
     */
    @GetMapping("/{id}")
    public Result<ProductVO> getById(@NotNull @PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_NOT_EXIST);
        }
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(product, vo);
        return Result.success(vo);
    }

    /**
     * 查询所有商品
     */
    @GetMapping("/list")
    public Result<List<ProductVO>> list() {
        List<Product> products = productService.ListProduct();
        List<ProductVO> voList = products.stream()
                .map(product -> {
                    ProductVO vo = new ProductVO();
                    BeanUtils.copyProperties(product, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(voList);
    }

    /**
     * 新增商品
     */
    @PostMapping
    public Result<Long> add(@Valid @RequestBody ProductAddDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        Long id = productService.addProduct(product);
        log.info(
                "操作人={} 新增商品 id={} name={}",
                CurrentUserHolder.getUsername(),
                id,
                dto.getName()
        );

        return Result.success(id);
    }

    /**
     * 修改商品
     */
    @PutMapping
    public Result<Integer> update(@Valid @RequestBody ProductUpdateDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        int result = productService.updateProduct(product);
        log.info(
                "操作人={} 修改商品 id={} name={}",
                CurrentUserHolder.getUsername(),
                dto.getId(),
                dto.getName()
        );
        return Result.success(result);
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public Result<Integer> delete(@NotNull @PathVariable Long id) {
        int result = productService.deleteProduct(id);
        log.info(
                "操作人={} 删除商品 id={}",
                CurrentUserHolder.getUsername(),
                id
        );
        return Result.success(result);
    }

}
