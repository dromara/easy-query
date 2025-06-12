package com.easy.query.test;

import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.entity.Topic;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2025/6/12 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest26 extends BaseTest{
    @Test
    public void test1(){
//        List<Draft1<BigDecimal>> list = easyEntityQuery.queryable(Topic.class)
//                .select(t_topic -> Select.DRAFT.of(
//                        t_topic.title().valueConvert(title->new BigDecimal(title))
//                )).toList();
    }
}
