package com.easyquery.springbootdemo.controller;

import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.exception.EasyQueryException;
import com.easyquery.springbootdemo.domain.OrderDsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/6/4 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
@RestController
@RequestMapping("/orderDs")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderDsController {

    private final EasyQuery easyQuery;

    @GetMapping("/init")
    public Object init() {
        ArrayList<OrderDsEntity> orderEntities = new ArrayList<>(100);
        List<String> users = Arrays.asList("xiaoming", "xiaohong", "xiaolan");

        for (int i = 0; i < 100; i++) {
            OrderDsEntity orderEntity = new OrderDsEntity();
            orderEntity.setId(String.valueOf(i));
            int i1 = i % 3;
            String uid = users.get(i1);
            orderEntity.setUid(uid);
            orderEntity.setOrderNo(i);
            orderEntities.add(orderEntity);
        }
        long l = easyQuery.insertable(orderEntities).executeRows();
        return "成功插入:" + l;
    }

    @GetMapping("/updateTrans")
    @Transactional(rollbackFor = Exception.class)
    public Object updateTrans() {
        long l1 = easyQuery.updatable(OrderDsEntity.class)
                .set(OrderDsEntity::getOrderNo, "xx")
                .where(o -> o.eq(OrderDsEntity::getId, "1")).executeRows();
        long l2 = easyQuery.updatable(OrderDsEntity.class)
                .set(OrderDsEntity::getOrderNo, "yy")
                .where(o -> o.eq(OrderDsEntity::getId, "2")).executeRows();
        if (true) {
            throw new EasyQueryException("123");
        }
        return 0;
    }

    @GetMapping("/getTrans")
    public Object getTrans() {
        List<OrderDsEntity> list = easyQuery.queryable(OrderDsEntity.class)
                .whereByIds("1", "2").toList();

        return list;
    }
}
