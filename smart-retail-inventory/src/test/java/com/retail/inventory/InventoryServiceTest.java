package com.retail.inventory;

import com.retail.inventory.entity.Inventory;
import com.retail.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class InventoryServiceTest {
    @Autowired
    InventoryService inventoryService;

//    /**
//     * 根据id查询库存信息
//     * @param id
//     * @return
//     */
//    public Inventory getInventoryById(int id);
//
//    /**
//     * 添加库存信息
//     * @param inventory
//     * @return
//     */
//    public int addInventory(Inventory inventory);
//
//    /**
//     * 修改库存信息
//     * @param inventory
//     * @return
//     */
//    public int updateInventory(Inventory inventory);
//
//    /**
//     * 删除库存信息
//     * @param id
//     * @return
//     */
//    public int deleteInventory(Long id);

    @Test
    void test02() {
        System.out.println("------------ListInventory-------------");
        List<Inventory> inventories = inventoryService.ListInventory();
        for (Inventory inventory : inventories) {
            System.out.println(inventory);
        }
        System.out.println("------------deleteInventory-------------");
        int i = inventoryService.deleteInventory(8L);
        System.out.println("删除成功" + i);
        System.out.println("------------ListInventory-------------");
        inventories = inventoryService.ListInventory();
        for (Inventory inventory : inventories) {
            System.out.println(inventory);
        }

    }

    @Test
    void test01() {
        System.out.println("------------getInventoryById-------------");
        Inventory inventoryById = inventoryService.getInventoryById(1L);
        System.out.println(inventoryById);

        System.out.println("------------addInventory-------------");
        Inventory inventory = new Inventory();
        inventory.setProductId(6L);
        inventory.setAreaType(1);
        inventory.setQuantity(10);
        inventory.setMinThreshold(5);
        Long i = inventoryService.addInventory(inventory);
        System.out.println("添加成功(id)" + i);
        Inventory inventoryById1 = inventoryService.getInventoryById(i);
        System.out.println(inventoryById1);

        System.out.println("------------updateInventory-------------");
        inventory.setId(i);
        inventory.setQuantity(20);
        int i1 = inventoryService.updateInventory(inventory);
        System.out.println("修改成功" + i1);
    }
}
