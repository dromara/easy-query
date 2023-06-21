package com.easy.query.test;

import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInjectCurrentlyInCreationException;
import com.easy.query.core.exception.EasyQueryFirstOrNotNullException;
import com.easy.query.core.exception.EasyQueryRouteNotMatchException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * create time 2023/5/11 15:53
 * 文件说明
 *
 * @author xuejiaming
 */

public class SkipTest {
    @Test
    public void testEasyQueryConcurrentException(){
        EasyQueryConcurrentException easyQueryConcurrentException = new EasyQueryConcurrentException("1");
        EasyQueryConcurrentException easyQueryConcurrentException1 = new EasyQueryConcurrentException("1", "1");
        Assert.assertEquals("1",easyQueryConcurrentException1.getCode());
        Throwable a=null;
        EasyQueryConcurrentException easyQueryConcurrentException2 = new EasyQueryConcurrentException(a);
        EasyQueryException easyQueryException = new EasyQueryException(a);
        EasyQueryInjectCurrentlyInCreationException easyQueryInjectCurrentlyInCreationException = new EasyQueryInjectCurrentlyInCreationException(a);
        EasyQueryInjectCurrentlyInCreationException easyQueryInjectCurrentlyInCreationException1 = new EasyQueryInjectCurrentlyInCreationException("1", a);
        EasyQueryFirstOrNotNullException easyQueryNotFoundException = new EasyQueryFirstOrNotNullException("");
        EasyQueryFirstOrNotNullException easyQueryNotFoundException1 = new EasyQueryFirstOrNotNullException("1", "1");
        EasyQueryFirstOrNotNullException easyQueryNotFoundException2 = new EasyQueryFirstOrNotNullException(a);
        EasyQueryFirstOrNotNullException easyQueryNotFoundException3 = new EasyQueryFirstOrNotNullException("1", "1", a);
        Assert.assertEquals("1",easyQueryNotFoundException3.getCode());


        EasyQueryRouteNotMatchException easyQueryRouteNotMatchException = new EasyQueryRouteNotMatchException("");
        EasyQueryRouteNotMatchException easyQueryRouteNotMatchException1 = new EasyQueryRouteNotMatchException(a);
        EasyQueryRouteNotMatchException easyQueryRouteNotMatchException2 = new EasyQueryRouteNotMatchException("1", a);

        EasyQuerySQLCommandException asyQuerySQLException = new EasyQuerySQLCommandException("");
        EasyQuerySQLCommandException asyQuerySQLException1 = new EasyQuerySQLCommandException(a);
        EasyQuerySQLCommandException asyQuerySQLException2 = new EasyQuerySQLCommandException("1", a);

        EasyQuerySQLStatementException asyQuerySQLExecuteException = new EasyQuerySQLStatementException("", new SQLException("123"));
        EasyQuerySQLStatementException asyQuerySQLExecuteException1 = new EasyQuerySQLStatementException("", a);
        EasyQuerySQLStatementException asyQuerySQLExecuteException2 = new EasyQuerySQLStatementException("1", a);
    }
    @Test

    public void test11(){
        String[] strings = new String[20];
        strings[0]="you";
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        String you = stringStringHashMap.put("you", "11");
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000000; i++) {
                String string = strings[0];
            }
            long end = System.currentTimeMillis();
            System.out.println((end-start)+"(ms)");
        }
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000000; i++) {
                String string1 = strings[0];
                String string2 = strings[0];
            }
            long end = System.currentTimeMillis();
            System.out.println((end-start)+"(ms)");
        }
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000000; i++) {
                String string1 = strings[0];
                String string2 = strings[0];
                String string3 = strings[0];
            }
            long end = System.currentTimeMillis();
            System.out.println((end-start)+"(ms)");
        }
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000000; i++) {
                String you1 = stringStringHashMap.get("you");
            }
            long end = System.currentTimeMillis();
            System.out.println((end-start)+"(ms)");
        }
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000000; i++) {
                String you1 = stringStringHashMap.get("you".toLowerCase());
            }
            long end = System.currentTimeMillis();
            System.out.println((end-start)+"(ms)");
        }
    }
}
