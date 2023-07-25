package com.easy.query.solon.controller;

import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.solon.annotation.Db;
import com.easy.query.solon.entity.Topic;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.MethodType;

/**
 * create time 2023/7/25 13:48
 * 文件说明
 *
 * @author xuejiaming
 */
@Controller
@Mapping("/test")
public class TestController {

    /**
     * 注意必须是配置多数据源的其中一个
     */
    @Db("db1")
    private EasyQuery easyQuery;
    @Mapping(value = "/hello",method = MethodType.GET)
    public String hello(){
        return "Hello World";
    }
    @Mapping(value = "/queryTopic",method = MethodType.GET)
    public Object queryTopic(){
        return easyQuery.queryable(Topic.class)
                .where(o->o.ge(Topic::getStars,2))
                .toList();
    }
}
