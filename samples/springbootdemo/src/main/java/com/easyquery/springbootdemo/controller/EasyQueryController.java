package com.easyquery.springbootdemo.controller;

import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easyquery.springbootdemo.domain.BlogEntity;
import com.easyquery.springbootdemo.domain.HelpCityEntity;
import com.easyquery.springbootdemo.domain.HelpProvinceEntity;
import com.easyquery.springbootdemo.domain.TestUserMysql0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

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
    private EasyQueryClient easyQuery;
    @Autowired
    private EasyCacheClient easyCacheClient;
    @GetMapping("/say")
    @EasyQueryTrack
    public Object say(@RequestParam(value = "aa",required = false) String aa) {
        BlogEntity blogEntity = easyCacheClient.kvStorage(BlogEntity.class)
                .firstOrNull(aa);
        return blogEntity;
    }
    @GetMapping("/sayp")
    @EasyQueryTrack
    public Object sayp(@RequestParam(value = "aa",required = false) String aa) {
        List<HelpProvinceEntity> all = easyCacheClient.allStorage(HelpProvinceEntity.class).getAll();
        return all;
    }
    @GetMapping("/sayp1")
    @EasyQueryTrack
    public Object sayp1(@RequestParam(value = "aa",required = false) String aa) {
        List<HelpProvinceEntity> all = easyCacheClient.allStorage(HelpProvinceEntity.class).getIn(Arrays.asList(aa));
        return all;
    }
    @GetMapping("/sayp2")
    @EasyQueryTrack
    public Object sayp2(@RequestParam(value = "aa",required = false) String aa) {
        HelpProvinceEntity all = easyCacheClient.allStorage(HelpProvinceEntity.class).firstOrNull(aa);
        return all;
    }
    @GetMapping("/sayc1")
    @EasyQueryTrack
    public Object sayc1(@RequestParam(value = "aa",required = false) String aa) {
        List<HelpCityEntity> all = easyCacheClient.multiStorage(HelpCityEntity.class).getAll(aa);
        return all;
    }
    @GetMapping("/sayc2")
    @EasyQueryTrack
    public Object sayc2(@RequestParam(value = "aa",required = false) String aa,@RequestParam(value = "bb",required = false) String bb) {
        HelpCityEntity allIndex = easyCacheClient.multiStorage(HelpCityEntity.class).firstOrNull(aa,bb);
        return allIndex;
    }

    @GetMapping("/sayHello")
    @EasyQueryTrack
    public Object sayHello(@RequestParam(value = "aa",required = false) String aa) {
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
        TestUserMysql0 testUserMysql1 = easyQuery.queryable(TestUserMysql0.class).whereById("123321123321xxx").firstOrNull();
        TestUserMysql0 testUserMysql2 = easyQuery.queryable(TestUserMysql0.class).whereById("123321123321xxx1").firstOrNull();
        return Arrays.asList(testUserMysql1,testUserMysql2);
    }
    @GetMapping("/sayHello4")
    public Object sayHello4() {
        TestUserMysql0 testUserMysql = easyQuery.queryable(TestUserMysql0.class).whereById("123321123321").firstOrNull();
        return testUserMysql;
    }
    @GetMapping("/sayHello5")
    public Object sayHello5() {
        BlogEntity blogEntity = easyQuery.queryable(BlogEntity.class)
                .whereById("123").firstOrNull();

        return blogEntity;
    }
    @GetMapping("/sayHello6")
    public Object sayHello6() {
        try(Transaction transaction = easyQuery.beginTransaction()){

            TestUserMysql0 testUserMysql1 = new TestUserMysql0();
            testUserMysql1.setId("123321123321xxx");
            testUserMysql1.setAge(1);
            testUserMysql1.setName("xxx");
            easyQuery.insertable(testUserMysql1).executeRows();
            test1();
            if(true){
                throw new RuntimeException("错误了");
            }
            transaction.commit();
        }
        return 1;
    }

    public void test1(){

        TestUserMysql0 testUserMysql1 = new TestUserMysql0();
        testUserMysql1.setId("123321123321xxx1");
        testUserMysql1.setAge(1);
        testUserMysql1.setName("xxx");
        easyQuery.insertable(testUserMysql1).executeRows();
    }
    @GetMapping("/sayHello7")
    public Object sayHello7() {
        long l = easyQuery.deletable(TestUserMysql0.class)
                .whereByIds(Arrays.asList("123321123321xxx3", "123321123321xxx2")).allowDeleteStatement(true).disableLogicDelete()
                .noInterceptor().executeRows();

        try(Transaction transaction = easyQuery.beginTransaction()){

            TestUserMysql0 testUserMysql1 = new TestUserMysql0();
            testUserMysql1.setId("123321123321xxx3");
            testUserMysql1.setAge(1);
            testUserMysql1.setName("xxx");
            easyQuery.insertable(testUserMysql1).executeRows();
            test2();
            transaction.commit();
        }
        return 1;
    }
    @GetMapping("/sayHello8")
    public Object sayHello8() {
        TestUserMysql0 testUserMysql1 = easyQuery.queryable(TestUserMysql0.class).whereById("123321123321xxx3").firstOrNull();
        TestUserMysql0 testUserMysql2 = easyQuery.queryable(TestUserMysql0.class).whereById("123321123321xxx2").firstOrNull();
        return Arrays.asList(testUserMysql1,testUserMysql2);
    }

    public void test2(){

        TestUserMysql0 testUserMysql1 = new TestUserMysql0();
        testUserMysql1.setId("123321123321xxx2");
        testUserMysql1.setAge(1);
        testUserMysql1.setName("xxx");
        easyQuery.insertable(testUserMysql1).executeRows();
    }

}
