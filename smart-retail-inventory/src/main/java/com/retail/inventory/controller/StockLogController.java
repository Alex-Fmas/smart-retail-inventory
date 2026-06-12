package com.retail.inventory.controller;

import com.retail.inventory.annotation.RequireRole;
import com.retail.inventory.common.Result;
import com.retail.inventory.context.CurrentUserHolder;
import com.retail.inventory.dto.stock.StockLogAddDTO;
import com.retail.inventory.entity.StockLog;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.service.StockLogService;
import com.retail.inventory.vo.stock.StockLogVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        if(stockLog == null){
            throw new BizException(BizExceptionEnum.DATA_NOT_EXIST);
        }
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
    @RequireRole("ADMIN")
    @PostMapping
    public Result<Long> add(@Valid @RequestBody StockLogAddDTO dto) {

        StockLog stockLog = new StockLog();

        BeanUtils.copyProperties(dto, stockLog);

        Long id = stockLogService.addStockLog(stockLog);

        log.info(
                "操作人={} 商品id={} 来源区域id={} 目的区域={} 数量={} 类型={}",
                CurrentUserHolder.getUsername(),
                stockLog.getProductId(),
                stockLog.getFromArea(),
                stockLog.getToArea(),
                stockLog.getQuantity(),
                stockLog.getType()
        );

        return Result.success(id);
    }

    /**
     * 删除库存流水
     */
    @RequireRole("ADMIN")
    @DeleteMapping("/{id}")
    public Result<Integer> delete(@NotNull @PathVariable Long id) {

        int result = stockLogService.deleteStockLog(id);

        log.info(
                "操作人={} 删除库存流水={}",
                CurrentUserHolder.getUsername(),
                id
        );

        return Result.success(result);
    }
    /*
      修改库存流水(禁用)
     */
}
