package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.stock.StockChangeDTO;
import com.retail.inventory.entity.Inventory;
import com.retail.inventory.service.StockChangeService;
import com.retail.inventory.vo.stock.StockChangeVO;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockChangeController {
    @Autowired
    StockChangeService stockChangeService;

    @PostMapping("/change")
    public Result<StockChangeVO> stockChange(@Valid @RequestBody StockChangeDTO dto) {
        Inventory vo = stockChangeService.stockChange(dto);
        StockChangeVO stockChangeVO = new StockChangeVO();
        BeanUtils.copyProperties(vo, stockChangeVO);

        return Result.success(stockChangeVO);
    }
}
