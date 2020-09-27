package com.zzp.sentinel.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow")
public class FlowLimitController {


    @PostMapping(value = "/limitServiceA")
    public String limitServiceA(){
        return "limitServiceA";
    }

    @PostMapping(value = "/limitServiceB")
    public String limitServiceB(){
        return "limitServiceB";
    }
}
