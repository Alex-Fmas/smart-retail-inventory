package com.retail.inventory;

import com.retail.inventory.entity.StockLog;
import com.retail.inventory.service.StockLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class StockLogServiceTest {
    @Autowired
    StockLogService stockLogService;
//    /**
//     * 根据id查询
//     * @param id
//     * @return
//     */
//    StockLog getStockLogById(Long id);
//
//    /**
//     * 添加流水
//     * @param stockLog
//     * @return
//     */
//    Long addStockLog(StockLog stockLog);
//
//    /**
//     * 更新流水
//     * @param stockLog
//     * @return
//     */
//    int updateStockLog(StockLog stockLog);
//
//    /**
//     * 删除流水
//     * @param id
//     * @return
//     */
//    int deleteStockLog(Long id);
//
//    /**
//     * 获取所有流水
//     * @return
//     */
//    List<StockLog> listStockLog();
    @Test
    void test02() {
        System.out.println("------------listStockLog--------------");
        List<StockLog> stockLogs = stockLogService.listStockLog();
        for (StockLog stockLog : stockLogs) {
            System.out.println(stockLog);
        }
        System.out.println("------------deleteStockLog--------------");
        int i = stockLogService.deleteStockLog(2L);
        System.out.println("返回值(i)：" + i);
        System.out.println("------------listStockLog--------------");
        stockLogs = stockLogService.listStockLog();
        for (StockLog stockLog : stockLogs) {
            System.out.println(stockLog);
        }
    }

    @Test
    void test01() {
        System.out.println("------------getStockLogById--------------");
        StockLog stockLog = stockLogService.getStockLogById(6L);
        System.out.println(stockLog);
        System.out.println("------------addStockLog--------------");
        StockLog stockLog1 = new StockLog();
        stockLog1.setProductId(6L);
        stockLog1.setFromArea(0);
        stockLog1.setToArea(2);
        stockLog1.setQuantity(100);//200
        stockLog1.setType(1);
        stockLog1.setOperatorId(2L);
        stockLog1.setCreateTime(LocalDateTime.now());
        Long id = stockLogService.addStockLog(stockLog1);
        System.out.println("返回值(id)：" + id);
        System.out.println("------------updateStockLog--------------");
        stockLog1.setId(id);
        stockLog1.setQuantity(200);
        int i = stockLogService.updateStockLog(stockLog1);
        System.out.println("返回值(i)：" + i);

    }
}
