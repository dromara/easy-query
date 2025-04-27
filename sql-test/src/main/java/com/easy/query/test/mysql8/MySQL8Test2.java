package com.easy.query.test.mysql8;

import com.easy.query.test.mysql8.entity.M8TestIndex;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/4/27 15:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test2 extends BaseTest{
    @Test
    public void test(){
        List<M8TestIndex> list = easyEntityQuery.queryable(M8TestIndex.class)
                .toList();
    }

}
