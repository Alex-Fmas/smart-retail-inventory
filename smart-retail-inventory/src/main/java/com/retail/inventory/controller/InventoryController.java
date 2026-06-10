package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.inventory.InventoryAddDTO;
import com.retail.inventory.dto.inventory.InventoryUpdateDTO;
import com.retail.inventory.entity.Inventory;
import com.retail.inventory.entity.Product;
import com.retail.inventory.service.InventoryService;
import com.retail.inventory.service.ProductService;
import com.retail.inventory.vo.inventory.InventoryVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    ProductService productService;

    /**
     * 根据ID查询库存信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<InventoryVO> getInventoryById(@NotNull @PathVariable Long id) {
        Inventory inventoryById = inventoryService.getInventoryById(id);
        Product productById = productService.getProductById(inventoryById.getProductId());
        InventoryVO inventoryVO = new InventoryVO().setInventory(inventoryById, productById.getName());
        return Result.success(inventoryVO);
    }

    /**
     * 查询全部库存
     */
    @GetMapping("/list")
    public Result<List<InventoryVO>> list() {

        List<Inventory> inventories = inventoryService.ListInventory();
        List<InventoryVO> voList = inventories.stream()
                .map(inventory -> {
                    InventoryVO vo = new InventoryVO();
                    BeanUtils.copyProperties(inventory, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(voList);
    }

    /**
     * 新增库存
     */
    @PostMapping
    public Result<Long> add(@Valid @RequestBody InventoryAddDTO dto) {
        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(dto, inventory);
        Long id = inventoryService.addInventory(inventory);
        return Result.success(id);
    }

    /**
     * 修改库存
     */
    @PutMapping
    public Result<Integer> update(@Valid @RequestBody InventoryUpdateDTO dto) {
        Inventory inventory = new Inventory();
        BeanUtils.copyProperties(dto, inventory);
        int result = inventoryService.updateInventory(inventory);
        return Result.success(result);
    }

    /**
     * 删除库存
     */
    @DeleteMapping("/{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        int result = inventoryService.deleteInventory(id);
        return Result.success(result);
    }


}
