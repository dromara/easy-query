package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityPager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pager;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.executor.PageAble;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
  * create time 2024/9/10 23:08
  * 文件说明
  * @author xuejiaming
  */
public interface EntityPageAble1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends PageAble<T1>,EntityQueryableAvailable<T1Proxy, T1> {
    default <TPageResult, TR> TPageResult toPageSelectResult(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, Query<TR>> selectExpression, EntityPager<T1Proxy,T1,TPageResult> pager){
        return pager.toResult(selectExpression,this);
    }
    default <TR> EasyPageResult<TR> toPageSelectResult(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, Query<TR>> selectExpression,long pageIndex, long pageSize){
        return toPageSelectResult(selectExpression,pageIndex,pageSize,-1L);
    }
    default <TR> EasyPageResult<TR> toPageSelectResult(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, Query<TR>> selectExpression, long pageIndex, long pageSize, long pageTotal){

        EntityQueryable<T1Proxy, T1> entityQueryable = getQueryable();
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
        EntityQueryable<T1Proxy, T1> select = entityQueryable.cloneQueryable().limit(offset, realTake).select(t -> t);
        select.getSQLEntityExpressionBuilder().getExpressionContext().getRelationExtraMetadata().getRelationExtraColumnMap().clear();
        Query<TR> apply = selectExpression.apply(select);
        List<TR> list = apply.toList();
        return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, list);
    }
}
