package com.retail.inventory.mapper;

import com.retail.inventory.entity.StockLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockLogMapper{
    StockLog getStockLogById(Long id);

    int addStockLog(StockLog stockLog);

    int updateStockLog(StockLog stockLog);

    int deleteStockLog(Long id);
}
