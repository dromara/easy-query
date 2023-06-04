package com.easyquery.springbootdemo.controller;

import com.easy.query.api4j.client.EasyQuery;
import com.easyquery.springbootdemo.domain.OrderByMonthEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/5/24 23:58
 * 文件说明
 *
 * @author xuejiaming
 */
@RestController
@RequestMapping("/orderMonth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderMonthController {

    private final EasyQuery easyQuery;

    @GetMapping("/init")
    public Object init() {
        ArrayList<OrderByMonthEntity> orderEntities = new ArrayList<>(100);
        List<String> users = Arrays.asList("xiaoming", "xiaohong", "xiaolan");
        LocalDateTime beginTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 5, 31, 0, 0);
        int i = 0;
        while (!beginTime.isAfter(endTime)) {

            OrderByMonthEntity orderEntity = new OrderByMonthEntity();
            orderEntity.setId(String.valueOf(i));
            int i1 = i % 3;
            String uid = users.get(i1);
            orderEntity.setUid(uid);
            orderEntity.setOrderNo(i);
            orderEntity.setCreateTime(beginTime);
            orderEntities.add(orderEntity);
            beginTime = beginTime.plusDays(1);
            i++;
        }
        long l = easyQuery.insertable(orderEntities).executeRows();
        return "成功插入:" + l;
    }

    @GetMapping("/first")
    public Object first(@RequestParam("id") String id) {
        OrderByMonthEntity orderEntity = easyQuery.queryable(OrderByMonthEntity.class)
                .whereById(id).firstOrNull();
        return orderEntity;
    }

    @GetMapping("/range")
    public Object first() {
        List<OrderByMonthEntity> list = easyQuery.queryable(OrderByMonthEntity.class)
                .where(o -> o.rangeClosed(OrderByMonthEntity::getCreateTime, LocalDateTime.of(2022, 3, 1, 0, 0), LocalDateTime.of(2022, 9, 1, 0, 0)))
                .toList();
        return list;
    }
//    @GetMapping("/firstByUid")
//    public Object firstByUid(@RequestParam("uid") String uid) {
//        OrderEntity orderEntity = easyQuery.queryable(OrderEntity.class)
//                .where(o->o.eq(OrderEntity::getUid,uid)).firstOrNull();
//        return orderEntity;
//    }
//    @GetMapping("/page")
//    public Object page(@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize) {
//        EasyPageResult<OrderEntity> pageResult = easyQuery.queryable(OrderEntity.class)
//                .orderByAsc(o -> o.column(OrderEntity::getOrderNo))
//                .toPageResult(pageIndex, pageSize);
//        return pageResult;
//    }
}
