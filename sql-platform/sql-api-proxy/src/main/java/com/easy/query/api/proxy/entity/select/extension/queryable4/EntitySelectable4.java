package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & Draft> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression4<T1Proxy,T2Proxy,T3Proxy,T4Proxy, DraftFetcher<TR,TRProxy>> selectExpression){
        DraftFetcher<TR, TRProxy> draftFetcher = selectExpression.apply(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy());
        ClientQueryable<TR> select = getClientQueryable4().select(EasyObjectUtil.typeCastNullable(draftFetcher.getDraft().getClass()), columnAsSelector -> {
            draftFetcher.accept(columnAsSelector.getAsSelector());
        });
        TRProxy draftProxy = draftFetcher.getDraftProxy();
        select.getSQLEntityExpressionBuilder().getExpressionContext().setDraftPropTypes(draftFetcher.getDraftPropTypes());
        return new EasyEntityQueryable<>(draftProxy, select);
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & Draft> EntityQueryable<TRProxy, TR> selectDraftMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, DraftFetcher<TR,TRProxy>> selectExpression){
        return selectDraft((t1,t2,t3,t4)->selectExpression.apply(new MergeTuple4<>(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy())));
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR>  select(SQLFuncExpression4<T1Proxy,T2Proxy,T3Proxy, T4Proxy, TRProxy> selectExpression){

        TRProxy resultProxy = selectExpression.apply(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy());
        Objects.requireNonNull(resultProxy,"select null result class");
        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
        if(selectAsExpression==null){//全属性映射
            ClientQueryable<TR> select = getClientQueryable4().select(resultProxy.getEntityClass());
            return new EasyEntityQueryable<>(resultProxy, select);
        }else{
            ClientQueryable<TR> select = getClientQueryable4().select(resultProxy.getEntityClass(), columnAsSelector -> {
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            return new EasyEntityQueryable<>(resultProxy, select);
        }
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR>  selectMerge(SQLFuncExpression1<MergeTuple4<T1Proxy,T2Proxy,T3Proxy, T4Proxy>, TRProxy> selectExpression){
        return select((a,b,c,d)->selectExpression.apply(new MergeTuple4<>(a,b,c,d)));
    }
}
