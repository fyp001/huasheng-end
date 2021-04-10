package com.myproject.api.springboot_mybatis;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringbootMybatisApplicationTests {

    @Test
    void contextLoads() throws JSONException {
        List<String> list = new ArrayList();

        list.add("111");
        list.add("111");
        list.add("111");
        list.add("111");
        System.out.println(list.toString());

    }

}
