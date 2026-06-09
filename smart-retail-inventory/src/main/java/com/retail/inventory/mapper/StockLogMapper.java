package com.retail.inventory.mapper;

import com.retail.inventory.entity.StockLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockLogMapper{
    StockLog getStockLogById(Long id);

    int addStockLog(StockLog stockLog);

    int updateStockLog(StockLog stockLog);

    int deleteStockLog(Long id);

    List<StockLog> ListStockLog();
}
