package com.zzp.first.feign;

import com.zzp.first.feign.fallback.OrderServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "nacos-service-second", fallback = OrderServiceFallBack.class)
public interface RemoteOrderService {

    @PostMapping(value = "order/createOrder")
    Integer createOrder(@RequestParam("orderMoney") BigDecimal orderMoney);

}
