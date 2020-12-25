package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.serviceImpl.ImageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ImageController {
    @RequestMapping(value = "/Apis/getCasesToShow")
    public String getCasesToShow(){
        String case1 = ImageService.getImgStr("ClassicCase1");
        String case2 = ImageService.getImgStr("ClassicCase2");
        String case3 = ImageService.getImgStr("ClassicCase3");
        String case4 = ImageService.getImgStr("ClassicCase4");

        return "{\"cases\":[{\"name\":\"案例一\",\"pic\":\"" + case1 +
                "\"},{\"name\":\"案例二\",\"pic\":\"" +   case2 +
                "\"},{\"name\":\"案例三\",\"pic\":\"" +   case3 +
                "\"},{\"name\":\"案例三\",\"pic\":\"" +   case4 +
                "\"}]}";
    }
}
