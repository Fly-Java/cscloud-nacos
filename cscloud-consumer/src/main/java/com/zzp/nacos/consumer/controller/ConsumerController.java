package com.zzp.nacos.consumer.controller;

import com.zzp.nacos.consumer.feign.ProviderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {

    @Autowired
    private ProviderFeignClient providerFeignClient;

    @GetMapping("/service")
    public String getProviderService(){
        return providerFeignClient.providerService();
    }

}
