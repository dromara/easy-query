package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.enums.SqlRangeEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * @FileName: GenericTest.java
 * @Description: 文件说明
 * @Date: 2023/3/17 22:22
 * @author xuejiaming
 */
public class GenericTest extends BaseTest {

    @Test
    public void SqlRangeTest1(){
        boolean openFirst1 = SqlRangeEnum.openFirst(SqlRangeEnum.Open);
        Assert.assertTrue(openFirst1);
        boolean openFirst2 = SqlRangeEnum.openFirst(SqlRangeEnum.Closed);
        Assert.assertFalse(openFirst2);
        boolean openFirst3 = SqlRangeEnum.openFirst(SqlRangeEnum.closedOpen);
        Assert.assertFalse(openFirst3);
        boolean openFirst4 = SqlRangeEnum.openFirst(SqlRangeEnum.openClosed);
        Assert.assertTrue(openFirst1);
        boolean openEnd1 = SqlRangeEnum.openEnd(SqlRangeEnum.Open);
        Assert.assertTrue(openEnd1);
        boolean openEnd2 = SqlRangeEnum.openEnd(SqlRangeEnum.Closed);
        Assert.assertFalse(openEnd2);
        boolean openEnd3 = SqlRangeEnum.openEnd(SqlRangeEnum.closedOpen);
        Assert.assertTrue(openEnd3);
        boolean openEnd4 = SqlRangeEnum.openEnd(SqlRangeEnum.openClosed);
        Assert.assertFalse(openEnd4);
    }
}
