package com.easy.query.test;

import com.easy.query.test.doc.MySignUp;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/10/14 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTestRelationTest extends BaseTest{
    @Test
    public void relationTest(){
        List<MySignUp> list = easyEntityQuery.queryable(MySignUp.class)
                .where(m -> {
                    m.comUser().userId().eq("1234");
                    m.comId().eq("123");
                }).toList();
    }
}
