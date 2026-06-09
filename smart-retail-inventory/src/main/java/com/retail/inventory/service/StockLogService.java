package com.retail.inventory.service;

import com.retail.inventory.entity.StockLog;

public interface StockLogService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    StockLog getStockLogById(Long id);

    /**
     * 添加流水
     * @param stockLog
     * @return
     */
    int addStockLog(StockLog stockLog);

    /**
     * 更新流水
     * @param stockLog
     * @return
     */
    int updateStockLog(StockLog stockLog);

    /**
     * 删除流水
     * @param id
     * @return
     */
    int deleteStockLog(Long id);
}
