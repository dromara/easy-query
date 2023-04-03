package com.easyquery.springbootdemo.controller;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.api.client.EasyQuery;
import com.easyquery.springbootdemo.domain.TestUserMysql0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName: EasyQueryController.java
 * @Description: 文件说明
 * @Date: 2023/3/11 14:37
 * @author xuejiaming
 */
@RestController
@RequestMapping("/easy-query")
public class EasyQueryController {
    @Autowired
    private EasyQuery easyQuery;

    @GetMapping("/sayHello")
    @EasyQueryTrack
    public Object sayHello() {
        TestUserMysql0 testUserMysql = easyQuery.queryable(TestUserMysql0.class)
                .firstOrNull();
        return testUserMysql;
    }

    @GetMapping("/sayHello1")
    public Object sayHello1() {
        TestUserMysql0 testUserMysql1 = new TestUserMysql0();
        testUserMysql1.setId("123321123321");
        testUserMysql1.setAge(1);
        testUserMysql1.setName("xxx");
        easyQuery.insertable(testUserMysql1).executeRows();
        return testUserMysql1;
    }

    @GetMapping("/sayHello2")
    @Transactional(rollbackFor = Exception.class)
    public Object sayHello2() {
        TestUserMysql0 testUserMysql1 = new TestUserMysql0();
        testUserMysql1.setId("123321123321xxx");
        testUserMysql1.setAge(1);
        testUserMysql1.setName("xxx");
        easyQuery.insertable(testUserMysql1).executeRows();
        throw new RuntimeException("错误了");
    }

    @GetMapping("/sayHello3")
    public Object sayHello3() {
        TestUserMysql0 testUserMysql = easyQuery.queryable(TestUserMysql0.class).whereById("123321123321xxx").firstOrNull();
        return testUserMysql;
    }
    @GetMapping("/sayHello4")
    public Object sayHello4() {
        TestUserMysql0 testUserMysql = easyQuery.queryable(TestUserMysql0.class).whereById("123321123321").firstOrNull();
        return testUserMysql;
    }
}
