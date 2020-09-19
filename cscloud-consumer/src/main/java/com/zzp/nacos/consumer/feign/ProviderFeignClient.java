package com.zzp.nacos.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "cscloud-provider")
public interface ProviderFeignClient {

    @GetMapping("providerService")
    String providerService();

}
