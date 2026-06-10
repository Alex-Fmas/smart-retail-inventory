package com.retail.inventory.service.impl;

import com.retail.inventory.entity.StockLog;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.StockLogMapper;
import com.retail.inventory.service.StockLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockLogServiceImpl implements StockLogService {
    @Autowired
    StockLogMapper stockLogMapper;
    @Override
    public StockLog getStockLogById(Long id) {
        StockLog stockLogById = stockLogMapper.getStockLogById(id);
        return stockLogById;
    }

    @Override
    public Long addStockLog(StockLog stockLog) {
        int result = stockLogMapper.addStockLog(stockLog);
        Long id = stockLog.getId();
        return id;
    }

    @Override
    public int updateStockLog(StockLog stockLog) {
        int result = stockLogMapper.updateStockLog(stockLog);
        return result;
    }

    @Override
    public int deleteStockLog(Long id) {
        int result = stockLogMapper.deleteStockLog(id);
        return result;
    }

    @Override
    public List<StockLog> listStockLog() {
        List<StockLog> stockLogs = stockLogMapper.ListStockLog();
        return stockLogs;
    }
}
