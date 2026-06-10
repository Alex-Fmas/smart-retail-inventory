package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.stock.StockLogAddDTO;
import com.retail.inventory.entity.StockLog;
import com.retail.inventory.service.StockLogService;
import com.retail.inventory.vo.stock.StockLogVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/stock-log")
public class StockLogController {
    @Autowired
    StockLogService stockLogService;

    /**
     * 根据ID查询库存流水
     */
    @GetMapping("/{id}")
    public Result<StockLogVO> getById(@NotNull @PathVariable Long id) {

        StockLog stockLog = stockLogService.getStockLogById(id);
        StockLogVO vo = new StockLogVO().setStockLog(stockLog);

        return Result.success(vo);
    }

    /**
     * 查询全部库存流水
     */
    @GetMapping("/list")
    public Result<List<StockLogVO>> list() {

        List<StockLog> stockLogs = stockLogService.listStockLog();
        List<StockLogVO> voList = stockLogs.stream()
                .map(stockLog -> {
                    StockLogVO vo = new StockLogVO();
                    vo.setStockLog(stockLog);
                    return vo;
                })
                .collect(Collectors.toList());

        return Result.success(voList);
    }

    /**
     * 新增库存流水
     */
    @PostMapping
    public Result<Long> add(@Valid @RequestBody StockLogAddDTO dto) {

        StockLog stockLog = new StockLog();

        BeanUtils.copyProperties(dto, stockLog);

        Long id = stockLogService.addStockLog(stockLog);

        return Result.success(id);
    }

    /**
     * 删除库存流水
     */
    @DeleteMapping("/{id}")
    public Result<Integer> delete(@NotNull @PathVariable Long id) {

        int result = stockLogService.deleteStockLog(id);

        return Result.success(result);
    }
    /*
      修改库存流水(禁用)
     */
}
