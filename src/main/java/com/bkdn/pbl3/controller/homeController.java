package com.bkdn.pbl3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
    @GetMapping("/login")
    public String index(){
        return "login";
    }
}
