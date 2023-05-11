package com.easy.query.test;

import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInjectCurrentlyInCreationException;
import com.easy.query.core.exception.EasyQueryNotFoundException;
import com.easy.query.core.exception.EasyQueryRouteNotMatchException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.exception.EasyQuerySQLExecuteException;
import org.junit.Assert;
import org.junit.Test;

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
        EasyQueryNotFoundException easyQueryNotFoundException = new EasyQueryNotFoundException("");
        EasyQueryNotFoundException easyQueryNotFoundException1 = new EasyQueryNotFoundException("1", "1");
        EasyQueryNotFoundException easyQueryNotFoundException2 = new EasyQueryNotFoundException(a);
        EasyQueryNotFoundException easyQueryNotFoundException3 = new EasyQueryNotFoundException("1", "1", a);
        Assert.assertEquals("1",easyQueryNotFoundException3.getCode());


        EasyQueryRouteNotMatchException easyQueryRouteNotMatchException = new EasyQueryRouteNotMatchException("");
        EasyQueryRouteNotMatchException easyQueryRouteNotMatchException1 = new EasyQueryRouteNotMatchException(a);
        EasyQueryRouteNotMatchException easyQueryRouteNotMatchException2 = new EasyQueryRouteNotMatchException("1", a);

        EasyQuerySQLException asyQuerySQLException = new EasyQuerySQLException("");
        EasyQuerySQLException asyQuerySQLException1 = new EasyQuerySQLException(a);
        EasyQuerySQLException asyQuerySQLException2 = new EasyQuerySQLException("1", a);

        EasyQuerySQLExecuteException asyQuerySQLExecuteException = new EasyQuerySQLExecuteException("","");
        EasyQuerySQLExecuteException asyQuerySQLExecuteException1 = new EasyQuerySQLExecuteException("",a);
        EasyQuerySQLExecuteException asyQuerySQLExecuteException2 = new EasyQuerySQLExecuteException("1", a);
    }
}
