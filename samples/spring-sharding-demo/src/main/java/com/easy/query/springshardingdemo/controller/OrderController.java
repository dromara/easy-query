package com.easy.query.springshardingdemo.controller;

import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.springshardingdemo.domain.OrderEntity;
import com.easy.query.springshardingdemo.domain.proxy.OrderEntityProxy;
import com.easy.query.springshardingdemo.dto.OrderGroupWithAvgOrderNoVO;
import com.easy.query.springshardingdemo.dto.OrderGroupWithSumOrderNoVO;
import com.easy.query.springshardingdemo.dto.proxy.OrderGroupWithAvgOrderNoVOProxy;
import com.easy.query.springshardingdemo.dto.proxy.OrderGroupWithSumOrderNoVOProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/6/26 21:47
 * 文件说明
 *
 * @author xuejiaming
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderController {
    private final EasyProxyQuery easyProxyQuery;

    @GetMapping("/init")
    public Object init() {

        long start = System.currentTimeMillis();
        LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        ArrayList<OrderEntity> orderEntities = new ArrayList<>();
        List<String> userIds = Arrays.asList("小明", "小红", "小蓝", "小黄", "小绿");
        int i = 0;
        do {
            OrderEntity orderEntity = new OrderEntity();
            String timeFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(beginTime);
            orderEntity.setId(timeFormat);
            orderEntity.setOrderNo(i);
            orderEntity.setUserId(userIds.get(i % 5));
            orderEntity.setCreateTime(beginTime);
            orderEntities.add(orderEntity);
            i++;
            beginTime = beginTime.plusMinutes(1);
        } while (beginTime.isBefore(now));

        long end = System.currentTimeMillis();

        long insertStart = System.currentTimeMillis();
        long rows = easyProxyQuery.insertable(orderEntities).executeRows();
        long insertEnd = System.currentTimeMillis();

        return "成功插入:" + rows + ",其中路由对象生成耗时:" + (end - start) + "(ms),插入耗时:" + (insertEnd - insertStart) + "(ms)";
    }


    @GetMapping("/groupByWithSumOrderNo")
    public Object groupByWithSumOrderNo() {
        long start = System.currentTimeMillis();
        List<String> userIds = Arrays.asList("小明", "小绿");
        List<OrderGroupWithSumOrderNoVO> list = easyProxyQuery.queryable(OrderEntityProxy.DEFAULT)
                .where(o -> o.in(o.t().userId(), userIds))
                .groupBy(g -> g.column(g.t().userId()))
                .select(OrderGroupWithSumOrderNoVOProxy.DEFAULT, s -> s.columnAs(s.t().userId(), r -> r.userId()).columnSumAs(s.t().orderNo(), r -> r.orderNoSum()))
                .toList();
        long end = System.currentTimeMillis();
        return Arrays.asList(list, (end - start) + "(ms)");
    }

    @GetMapping("/groupByWithSumOrderNoOrderByUserId")
    public Object groupByWithSumOrderNoOrderByUserId() {
        long start = System.currentTimeMillis();
        List<String> userIds = Arrays.asList("小明", "小绿");
        List<OrderGroupWithSumOrderNoVO> list = easyProxyQuery.queryable(OrderEntityProxy.DEFAULT)
                .where(o -> o.in(o.t().userId(), userIds))
                .groupBy(g -> g.column(g.t().userId()))
                .orderByAsc(o -> o.column(o.t().userId()))
                .select(OrderGroupWithSumOrderNoVOProxy.DEFAULT, s -> s.columnAs(s.t().userId(), r -> r.userId()).columnSumAs(s.t().orderNo(), r -> r.orderNoSum()))
                .toList();
        long end = System.currentTimeMillis();
        return Arrays.asList(list, (end - start) + "(ms)");
    }

    @GetMapping("/groupByWithAvgOrderNo")
    public Object groupByWithAvgOrderNo() {
        long start = System.currentTimeMillis();
        List<String> userIds = Arrays.asList("小明", "小绿");
        List<OrderGroupWithAvgOrderNoVO> list = easyProxyQuery.queryable(OrderEntityProxy.DEFAULT)
                .where(o -> o.in(o.t().userId(), userIds))
                .groupBy(g -> g.column(g.t().userId()))
                .orderByAsc(o -> o.column(o.t().userId()))
                .select(OrderGroupWithAvgOrderNoVOProxy.DEFAULT, s -> s.columnAs(s.t().userId(), r -> r.userId()).columnAvgAs(s.t().orderNo(), r -> r.orderNoAvg()))
                .toList();
        long end = System.currentTimeMillis();
        return Arrays.asList(list, (end - start) + "(ms)");
    }

    @GetMapping("/groupByWithAvgOrderNo1")
    public Object groupByWithAvgOrderNo1() {
        long start = System.currentTimeMillis();
        List<String> userIds = Arrays.asList("小明");
        List<OrderGroupWithAvgOrderNoVO> list = easyProxyQuery.queryable(OrderEntityProxy.DEFAULT)
                .where(o -> o.in(o.t().userId(), userIds))
                .groupBy(g -> g.column(g.t().userId()))
                .orderByAsc(o -> o.column(o.t().userId()))
                .select(OrderGroupWithAvgOrderNoVOProxy.DEFAULT, s -> s.columnAs(s.t().userId(), r -> r.userId()).columnSumAs(s.t().orderNo(), r -> r.orderNoAvg()))
                .toList();
        long end = System.currentTimeMillis();
        return Arrays.asList(list, (end - start) + "(ms)");
    }

    @GetMapping("/groupByWithAvgOrderNo2")
    public Object groupByWithAvgOrderNo2() {
        long start = System.currentTimeMillis();
        List<String> userIds = Arrays.asList("小明");
        List<OrderGroupWithAvgOrderNoVO> list = easyProxyQuery.queryable(OrderEntityProxy.DEFAULT)
                .where(o -> o.in(o.t().userId(), userIds))
                .groupBy(g -> g.column(g.t().userId()))
                .orderByAsc(o -> o.column(o.t().userId()))
                .select(OrderGroupWithAvgOrderNoVOProxy.DEFAULT, s -> s.columnAs(s.t().userId(), r -> r.userId()).columnCountAs(s.t().orderNo(), r -> r.orderNoAvg()))
                .toList();
        long end = System.currentTimeMillis();
        return Arrays.asList(list, (end - start) + "(ms)");
    }

    @GetMapping("/groupByWithAvgOrderNo3")
    public Object groupByWithAvgOrderNo3() {
        long start = System.currentTimeMillis();
        List<String> userIds = Arrays.asList("小明", "小绿");
        List<OrderGroupWithAvgOrderNoVO> list = easyProxyQuery.queryable(OrderEntityProxy.DEFAULT)
                .where(o -> o.in(o.t().userId(), userIds).in(o.t().id(), Arrays.asList("20200101000500","20200101000400","20200101001000","20200601000500","20200601000900")))
                .groupBy(g -> g.column(g.t().userId()))
                .orderByAsc(o -> o.column(o.t().userId()))
                .select(OrderGroupWithAvgOrderNoVOProxy.DEFAULT, s -> s.columnAs(s.t().userId(), r -> r.userId()).columnAvgAs(s.t().orderNo(), r -> r.orderNoAvg()))
                .toList();
        long end = System.currentTimeMillis();
        return Arrays.asList(list, (end - start) + "(ms)");
    }
    @GetMapping("/page")
    public Object page(@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize) {
        long start = System.currentTimeMillis();
        List<String> userIds = Arrays.asList("小明", "小绿");
        EasyPageResult<OrderEntity> pageResult = easyProxyQuery.queryable(OrderEntityProxy.DEFAULT)
                .where(o -> o.in(o.t().userId(), userIds))
                .orderByAsc(o -> o.column(o.t().userId()))
                .toPageResult(pageIndex, pageSize);
        long end = System.currentTimeMillis();
        return Arrays.asList(pageResult, (end - start) + "(ms)");
    }
}
