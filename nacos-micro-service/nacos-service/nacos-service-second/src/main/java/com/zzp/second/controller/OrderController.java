package com.zzp.second.controller;

import com.zzp.second.service.BusOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private BusOrderService busOrderService;

    @PostMapping(value = "createOrder")
    public Integer createOrder(BigDecimal orderMoney){
        return busOrderService.createOrder(orderMoney);
    }

}
