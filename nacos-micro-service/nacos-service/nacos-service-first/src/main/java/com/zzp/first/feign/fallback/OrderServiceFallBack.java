package com.zzp.first.feign.fallback;

import com.zzp.first.feign.RemoteOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class OrderServiceFallBack implements RemoteOrderService {
    @Override
    public Integer createOrder(BigDecimal orderMoney) {
        log.info("降级了,降级了");
        return null;
    }
}
