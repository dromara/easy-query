package com.easy.query.test;

import com.easy.query.core.metadata.EntityMetadata;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/2/12 15:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest21 extends BaseTest{
    @Test
    public void test(){

        EntityMetadata entityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(TestColumnUpper.class);
        System.out.println(entityMetadata);
    }
}
