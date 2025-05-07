package com.easy.query.test.mysql8;

import com.easy.query.test.mysql8.entity.M8Child;
import com.easy.query.test.mysql8.entity.M8Parent;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/5/7 15:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8IncludeOrderTest extends BaseTest {
    @Test
    public void testOrder() {
        List<M8Parent> list = easyEntityQuery.queryable(M8Parent.class)
                .includes(m -> m.children())
                .toList();
        for (M8Parent m8Parent : list) {
            Integer firstOrder = null;
            for (M8Child m8Child : m8Parent.getChildren()) {
                if (firstOrder == null) {
                    firstOrder = m8Child.getOrder();
                }
                Assert.assertTrue(firstOrder <= m8Child.getOrder());
                firstOrder = m8Child.getOrder();
            }
        }
    }

    @Test
    public void testOrde2r() {
        List<M8Parent> list = easyEntityQuery.queryable(M8Parent.class)
                .includes(m -> m.children2())
                .toList();
        for (M8Parent m8Parent : list) {
            Integer firstOrder = null;
            for (M8Child m8Child : m8Parent.getChildren2()) {
                if (firstOrder == null) {
                    firstOrder = m8Child.getOrder();
                }
                Assert.assertTrue(firstOrder <= m8Child.getOrder());
                firstOrder = m8Child.getOrder();
            }
        }
    }
}
