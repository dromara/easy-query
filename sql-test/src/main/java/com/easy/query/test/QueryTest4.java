package com.easy.query.test;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.test.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * create time 2023/9/26 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest4 extends BaseTest{

    @Test
    public void query1233(){
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.eq(Topic::getCreateTime,maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime,"1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?",sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.ge(Topic::getCreateTime,maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime,"1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` >= (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?",sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.gt(Topic::getCreateTime,maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime,"1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` > (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?",sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.ne(Topic::getCreateTime,maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime,"1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <> (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?",sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.le(Topic::getCreateTime,maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime,"1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <= (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?",sql);
        }
        {
            Queryable<LocalDateTime> maxCreateTimeQuery = easyQuery.queryable(Topic.class)
                    .select(LocalDateTime.class, x -> x.columnMax(Topic::getCreateTime));
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> {
                        o.lt(Topic::getCreateTime,maxCreateTimeQuery);
                        o.eq(Topic::getCreateTime,"1");
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` < (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1) AND t.`create_time` = ?",sql);
        }
    }
}
