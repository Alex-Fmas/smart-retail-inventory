package com.retail.inventory.service;

import com.retail.inventory.entity.Inventory;

import java.util.List;

public interface InventoryService {
    /**
     * 根据id查询库存信息
     * @param id
     * @return
     */
    public Inventory getInventoryById(Long id);

    /**
     * 添加库存信息
     * @param inventory
     * @return
     */
    public Long addInventory(Inventory inventory);

    /**
     * 修改库存信息
     * @param inventory
     * @return
     */
    public int updateInventory(Inventory inventory);

    /**
     * 删除库存信息
     * @param id
     * @return
     */
    public int deleteInventory(Long id);
    /**
     * 查询所有库存信息
     * @return
     */
    public List<Inventory> ListInventory();
}
