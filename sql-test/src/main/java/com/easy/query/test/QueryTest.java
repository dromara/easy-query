package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.entity.BlogEntity;
import org.junit.Assert;
import org.junit.Test;

/**
 * @FileName: QueryTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 17:31
 * @Created by xuejiaming
 */
public class QueryTest extends BaseTest {
    @Test
    public void query1(){
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"));
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ?",sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }

    @Override
    public void customInit() {

    }
}
