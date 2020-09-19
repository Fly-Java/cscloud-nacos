package com.zzp.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosApplicationController {


    @GetMapping("/nacosapplication")
    public String applicationService(){
        return "NacosApplication";
    }

}
