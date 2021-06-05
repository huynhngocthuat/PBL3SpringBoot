package com.bkdn.pbl3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {
    @RequestMapping("testapi")
    public String index(){
        return "testapi";
    }
}
