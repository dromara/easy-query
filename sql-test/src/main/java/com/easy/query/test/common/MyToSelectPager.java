package com.easy.query.test.common;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pager;
import com.easy.query.core.basic.api.select.executor.PageAble;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/9/19 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyToSelectPager<TEntity, TProxy extends ProxyEntity<TProxy, TEntity>,TR,TRProxy extends ProxyEntity<TRProxy, TR>> implements Pager<TEntity,EasyPageResult<TR>> {
    private final long pageIndex;
    private final long pageSize;
    private final long pageTotal;
    private final SQLFuncExpression1<TProxy, TRProxy> selectExpression;

    public MyToSelectPager(long pageIndex, long pageSize, SQLFuncExpression1<TProxy, TRProxy> selectExpression){
        this(pageIndex,pageSize,selectExpression,-1);
    }
    public MyToSelectPager(long pageIndex, long pageSize, SQLFuncExpression1<TProxy, TRProxy> selectExpression, long pageTotal){

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.selectExpression = selectExpression;
    }
    @Override
    public EasyPageResult<TR> toResult(PageAble<TEntity> query) {
        EntityQueryable<TProxy, TEntity> entityQueryable = (EntityQueryable<TProxy, TEntity>) query;
        //设置每次获取多少条
        long take = pageSize <= 0 ? 1 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        long total = pageTotal < 0 ? entityQueryable.cloneQueryable().count() : pageTotal;
        QueryRuntimeContext runtimeContext = entityQueryable.getRuntimeContext();

        EasyPageResultProvider easyPageResultProvider = runtimeContext.getEasyPageResultProvider();
        if (total <= offset) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList());
        }//获取剩余条数
        long remainingCount = total - offset;
        //当剩余条数小于take数就取remainingCount
        long realTake = Math.min(remainingCount, take);
        if (realTake <= 0) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList());
        }
        List<TR> list = entityQueryable.cloneQueryable().limit(offset, realTake).select(t -> t)
                .select(selectExpression)
                .toList();
        return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, list);
    }
}
