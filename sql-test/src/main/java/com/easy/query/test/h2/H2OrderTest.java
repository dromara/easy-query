package com.easy.query.test.h2;

import com.easy.query.test.h2.domain.H2Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/6/6 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2OrderTest extends H2BaseTest {
    @Test
    public void test1() {
        List<H2Order> list = easyQueryOrder.queryable(H2Order.class)
                .where(o -> o.in(H2Order::getId, Arrays.asList(1, 2, 6, 7)))
                .toList();
        Assert.assertEquals(4, list.size());
    }

    @Test
    public void test2() {
        List<H2Order> list = easyQueryOrder.queryable(H2Order.class)
                .where(o -> o.in(H2Order::getId, Arrays.asList(1, 2, 6, 7)))
                .orderByDesc(o -> o.column(H2Order::getId))
                .toList();
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(7, (int) list.get(0).getId());
        Assert.assertEquals(6, (int) list.get(1).getId());
        Assert.assertEquals(2, (int) list.get(2).getId());
        Assert.assertEquals(1, (int) list.get(3).getId());

    }
}
