package com.easy.query.test;

import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.entity.Topic;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/6/11 22:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryNativeSQLTest extends BaseTest{
    @Test
    public void testNativeSQL1(){
        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.expression().sqlSegment("{0}={1}", c -> {
                        c.column(t.title()).value("123");
                    }, Object.class).executeSQL();
                }).toList();

        List<Draft1<String>> list1 = easyEntityQuery.queryable(Topic.class).select(t -> Select.DRAFT.of(
                t.expression().sqlSegment("{0}", c -> {
                    c.column(t.title());
                }, String.class)
        )).toList();
        List<Topic> list2 = easyEntityQuery.queryable(Topic.class).select(Topic.class, t -> t.expression().sqlSegment("{0}", c -> {
            c.column(t.stars());
        }, String.class).as("title")).toList();
    }
}
