package com.retail.inventory.mapper;


import com.retail.inventory.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InventoryMapper {
    public Inventory getInventoryById(Long id);

    public int addInventory(Inventory inventory);

    public int updateInventory(Inventory inventory);

    public int deleteInventory(Long id);

    public List<Inventory> ListInventory();
}
