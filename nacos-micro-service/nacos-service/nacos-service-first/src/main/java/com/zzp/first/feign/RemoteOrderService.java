package com.zzp.first.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "nacos-service-second")
public interface RemoteOrderService {

    @PostMapping(value = "order/createOrder")
    Integer createOrder(@RequestParam("orderMoney") BigDecimal orderMoney);

}
