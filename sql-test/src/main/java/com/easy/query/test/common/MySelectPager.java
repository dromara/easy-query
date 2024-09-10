package com.easy.query.test.common;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2024/9/10 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySelectPager<TEntity, TProxy extends ProxyEntity<TProxy, TEntity>> {
    private final long pageIndex;
    private final long pageSize;
    private final long pageTotal;

    public MySelectPager(long pageIndex, long pageSize){
        this(pageIndex,pageSize,-1);
    }
    public MySelectPager(long pageIndex, long pageSize, long pageTotal){

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
    }

    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> MyToSelectPager<TEntity,TProxy,TR,TRProxy> toSelect(SQLFuncExpression1<TProxy, TRProxy> selectExpression){
        return new MyToSelectPager<>(pageIndex,pageSize,selectExpression,pageTotal);
    }
}
