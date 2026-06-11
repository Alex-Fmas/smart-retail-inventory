package com.retail.inventory.service;

import com.retail.inventory.dto.stock.StockChangeDTO;
import com.retail.inventory.entity.Inventory;

public interface StockChangeService {
    Inventory stockChange(StockChangeDTO dto);
}
