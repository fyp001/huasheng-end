package com.myproject.api.springboot_mybatis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


@Controller
public class PageController {
    @RequestMapping("/login")
//    public String jumpLogin(){
//        return "redirect:http://127.0.0.1/dist";
//    }
    public String jumpLogin(){
        return "redirect:http://8.129.86.121/dist";
    }
    @RequestMapping("/main")
    public String jumpMain(){
        return "main";
    }

    @RequestMapping(value = "/jump")
    public String jumpWebTemplate(){
        return "WebTemplate";
    }

    @RequestMapping("/contactInfo")
    public String jumpContactInfo(){
        return "contactInfo";
    }

    @RequestMapping("/certificate")
    public String jumpCertificate(){
        return "certificate";
    }

    @RequestMapping("/businessScope")
    public String jumpBusinessScope(){
        return "businessScope";
    }
}