package com.retail.inventory.service;

import com.retail.inventory.entity.StockLog;

import java.util.List;

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
    Long addStockLog(StockLog stockLog);

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

    /**
     * 获取所有流水
     * @return
     */
    List<StockLog> listStockLog();

}
