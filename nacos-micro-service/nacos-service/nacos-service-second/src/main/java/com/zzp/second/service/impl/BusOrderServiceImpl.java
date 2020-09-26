package com.zzp.second.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzp.common.entity.BusOrder;
import com.zzp.common.utils.SnowflakeIdWorker;
import com.zzp.second.mapper.BusOrderMapper;
import com.zzp.second.service.BusOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class BusOrderServiceImpl extends ServiceImpl<BusOrderMapper, BusOrder> implements BusOrderService {

    @Resource
    private BusOrderMapper busOrderMapper;

    @Override
    public Integer createOrder(BigDecimal orderMoney) {
        BusOrder order = new BusOrder();
        order.setId(SnowflakeIdWorker.getNextId());
        order.setOrderMoney(orderMoney);
        order.setOrderSn("123456");
        order.setOrderTime(LocalDateTime.now());
        return busOrderMapper.insert(order);
    }
}
