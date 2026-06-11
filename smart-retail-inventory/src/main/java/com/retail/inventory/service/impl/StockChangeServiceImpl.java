package com.retail.inventory.service.impl;

import com.retail.inventory.dto.stock.StockChangeDTO;
import com.retail.inventory.entity.Inventory;
import com.retail.inventory.entity.Product;
import com.retail.inventory.entity.StockLog;
import com.retail.inventory.enums.StockChangeTypeEnum;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.InventoryMapper;
import com.retail.inventory.mapper.ProductMapper;
import com.retail.inventory.mapper.StockLogMapper;
import com.retail.inventory.service.StockChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockChangeServiceImpl implements StockChangeService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    InventoryMapper inventoryMapper;
    @Autowired
    StockLogMapper stockLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Inventory stockChange(StockChangeDTO dto) {
        Long productId = dto.getProductId();
        Integer fromArea = dto.getFromArea();
        Integer toArea = dto.getToArea();
        Integer quantity = dto.getQuantity();
        Integer type = dto.getType();

        StockChangeTypeEnum changeType = StockChangeTypeEnum.getByCode(type);
        if (changeType == null) {
            throw new BizException(BizExceptionEnum.STOCK_CHANGE_TYPE_ERROR);
        }

        // 校验商品是否存在
        Product productById = productMapper.getProductById(productId);
        if (productById == null) {
            throw new BizException(BizExceptionEnum.PRODUCT_NOT_EXIST);
        }

        Inventory sourceInventory = null;
        // 校验库存
        if (StockChangeTypeEnum.MOVE == changeType || StockChangeTypeEnum.SALE == changeType || StockChangeTypeEnum.LOSS == changeType){
            sourceInventory = inventoryMapper.getInventoryByProductId(productId, fromArea);
            if (sourceInventory == null) {
                throw new BizException(BizExceptionEnum.STOCK_NOT_EXIST);
            }
            if (sourceInventory.getQuantity() < quantity) {
                throw new BizException(BizExceptionEnum.STOCK_NOT_ENOUGH);
            }
        }

        switch (changeType) {
            case PURCHASE -> {
                Inventory inventory = inventoryMapper.getInventoryByProductId(productId, toArea);
                if (inventory == null) {
                    inventory = new Inventory();
                    inventory.setProductId(productId);
                    inventory.setAreaType(toArea);
                    inventory.setQuantity(quantity);
                    inventory.setMinThreshold(20);  // 默认最小库存阈值20
                    inventoryMapper.addInventory(inventory);
                } else {
                    inventory.setQuantity(inventory.getQuantity() + quantity);
                    inventoryMapper.updateInventory(inventory);
                }
            }
            case MOVE -> {
                sourceInventory.setQuantity(sourceInventory.getQuantity() - quantity);
                inventoryMapper.updateInventory(sourceInventory);

                Inventory targetInventory = inventoryMapper.getInventoryByProductId(productId, toArea);
                if (targetInventory == null) {
                    targetInventory = new Inventory();
                    targetInventory.setProductId(productId);
                    targetInventory.setAreaType(toArea);
                    targetInventory.setQuantity(quantity);
                    targetInventory.setMinThreshold(20);  // 默认最小库存阈值20
                    inventoryMapper.addInventory(targetInventory);
                } else {
                    targetInventory.setQuantity(targetInventory.getQuantity() + quantity);
                    inventoryMapper.updateInventory(targetInventory);
                }
            }
            case SALE -> {
                // 减库存
                Inventory inventory = inventoryMapper.getInventoryByProductId(productId, fromArea);
                if (inventory == null) {
                    throw new BizException(BizExceptionEnum.STOCK_NOT_EXIST);
                }
                if (inventory.getQuantity() < quantity) {
                    throw new BizException(BizExceptionEnum.STOCK_NOT_ENOUGH);
                }
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryMapper.updateInventory(inventory);
            }
            case LOSS -> {
                // 减库存
                Inventory inventory = inventoryMapper.getInventoryByProductId(productId, fromArea);
                if (inventory == null) {
                    throw new BizException(BizExceptionEnum.STOCK_NOT_EXIST);
                }
                if (inventory.getQuantity() < quantity) {
                    throw new BizException(BizExceptionEnum.STOCK_NOT_ENOUGH);
                }
               inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryMapper.updateInventory(inventory);
            }
        }

        StockLog stockLog = new StockLog();
        stockLog.setProductId(productId);
        stockLog.setFromArea(fromArea);
        stockLog.setToArea(toArea);
        stockLog.setQuantity(quantity);
        stockLog.setType(type);
        stockLog.setOperatorId(dto.getOperatorId());
        stockLog.setCreateTime(dto.getCreateTime());
        stockLogMapper.addStockLog(stockLog);

        return inventoryMapper.getInventoryByProductId(productId, toArea);
    }
}
