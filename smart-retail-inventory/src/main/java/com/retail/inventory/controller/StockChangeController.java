package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.context.CurrentUserHolder;
import com.retail.inventory.dto.stock.StockChangeDTO;
import com.retail.inventory.entity.Inventory;
import com.retail.inventory.service.StockChangeService;
import com.retail.inventory.vo.stock.StockChangeVO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stock")
public class StockChangeController {
    @Autowired
    StockChangeService stockChangeService;

    @PostMapping("/change")
    public Result<StockChangeVO> stockChange(@Valid @RequestBody StockChangeDTO dto) {
        Inventory inventory = stockChangeService.stockChange(dto);
        StockChangeVO stockChangeVO = new StockChangeVO();

        BeanUtils.copyProperties(dto, stockChangeVO);
        if (inventory != null) {
            BeanUtils.copyProperties(inventory, stockChangeVO);
        }

        log.info(
                "操作人={} 商品id={} 来源区域id={} 目的区域={} 数量={} 类型={}",
                CurrentUserHolder.getUsername(),
                dto.getProductId(),
                dto.getFromArea(),
                dto.getToArea(),
                dto.getQuantity(),
                dto.getType()
        );

        return Result.success(stockChangeVO);
    }
}
