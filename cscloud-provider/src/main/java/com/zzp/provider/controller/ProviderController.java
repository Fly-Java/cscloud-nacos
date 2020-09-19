package com.zzp.provider.controller;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProviderController {

    @GetMapping("/providerService")
    public String providerService(){
        log.info("provider invoke");
        return "调用成功";
    }
}
