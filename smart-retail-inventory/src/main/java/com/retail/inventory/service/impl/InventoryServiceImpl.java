package com.retail.inventory.service.impl;

import com.retail.inventory.entity.Inventory;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.InventoryMapper;
import com.retail.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public Inventory getInventoryById(Long id) {
        Inventory inventoryById = inventoryMapper.getInventoryById(id);
        return inventoryById;
    }

    @Override
    public Long addInventory(Inventory inventory) {
        int result = inventoryMapper.addInventory(inventory);
        Long id = inventory.getId();
        return id;
    }

    @Override
    public int updateInventory(Inventory inventory) {
        int result = inventoryMapper.updateInventory(inventory);
        return result;
    }

    @Override
    public int deleteInventory(Long id) {
        int result = inventoryMapper.deleteInventory(id);
        return result;
    }

    @Override
    public List<Inventory> ListInventory() {
        List<Inventory> ListInventory = inventoryMapper.ListInventory();
        return ListInventory;
    }
}
