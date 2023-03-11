package org.easyquery.springbootdemo.controller;

import org.easy.query.core.api.client.EasyQuery;
import org.easyquery.springbootdemo.domain.TestUserMysql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName: EasyQueryController.java
 * @Description: 文件说明
 * @Date: 2023/3/11 14:37
 * @Created by xuejiaming
 */
@RestController
@RequestMapping("/easy-query")
public class EasyQueryController {
    @Autowired
    private EasyQuery easyQuery;
    @GetMapping("/sayHello")
    public Object sayHello(){
        TestUserMysql testUserMysql = easyQuery.query(TestUserMysql.class)
                .firstOrNull();
        return testUserMysql;
    }
}
