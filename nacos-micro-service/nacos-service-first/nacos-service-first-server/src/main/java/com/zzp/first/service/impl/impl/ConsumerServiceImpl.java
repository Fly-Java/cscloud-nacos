package com.zzp.first.service.impl.impl;

import com.zzp.first.service.impl.ConsumerService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Override
    public String consumerService() {
        return "consumerService";
    }
}
