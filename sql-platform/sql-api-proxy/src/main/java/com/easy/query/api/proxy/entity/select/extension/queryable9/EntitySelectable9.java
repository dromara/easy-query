package com.easy.query.api.proxy.entity.select.extension.queryable9;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DraftResult;
import com.easy.query.core.common.tuple.MergeTuple9;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression9;
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
public interface EntitySelectable9<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> extends ClientEntityQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, EntityQueryable9Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> {

    /**
     * 请使用 select + Select.DRAFT.of
     * @param selectExpression
     * @return
     * @param <TRProxy>
     * @param <TR>
     */
    @Deprecated
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & DraftResult> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression9<T1Proxy,T2Proxy,T3Proxy,T4Proxy,T5Proxy,T6Proxy,T7Proxy,T8Proxy,T9Proxy, DraftFetcher<TR,TRProxy>> selectExpression){
        DraftFetcher<TR, TRProxy> draftFetcher = selectExpression.apply(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy(),get5Proxy(),get6Proxy(),get7Proxy(),get8Proxy(),get9Proxy());
        ClientQueryable<TR> select = getClientQueryable9().select(EasyObjectUtil.typeCastNullable(draftFetcher.getDraft().getClass()), columnAsSelector -> {
            draftFetcher.accept(columnAsSelector.getAsSelector());
        });
        TRProxy draftProxy = draftFetcher.getDraftProxy();
        select.getSQLEntityExpressionBuilder().getExpressionContext().setDraftPropTypes(draftFetcher.getDraftPropTypes());
        return new EasyEntityQueryable<>(draftProxy, select);
    }

    /**
     * 请使用 select + Select.DRAFT.of
     * @param selectExpression
     * @return
     * @param <TRProxy>
     * @param <TR>
     */
    @Deprecated
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & DraftResult> EntityQueryable<TRProxy, TR> selectDraftMerge(SQLFuncExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>, DraftFetcher<TR,TRProxy>> selectExpression){
        return selectDraft((t1,t2,t3,t4,t5,t6,t7,t8,t9)->selectExpression.apply(new MergeTuple9<>(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy(),get5Proxy(),get6Proxy(),get7Proxy(),get8Proxy(),get9Proxy())));
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR>  select(SQLFuncExpression9<T1Proxy,T2Proxy,T3Proxy, T4Proxy,T5Proxy,T6Proxy,T7Proxy,T8Proxy,T9Proxy, TRProxy> selectExpression){

        TRProxy resultProxy = selectExpression.apply(get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy(),get5Proxy(),get6Proxy(),get7Proxy(),get8Proxy(),get9Proxy());
        Objects.requireNonNull(resultProxy,"select null result class");
        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
        if(selectAsExpression==null){//全属性映射
            ClientQueryable<TR> select = getClientQueryable9().select(resultProxy.getEntityClass());
            return new EasyEntityQueryable<>(resultProxy, select);
        }else{
            ClientQueryable<TR> select = getClientQueryable9().select(resultProxy.getEntityClass(), columnAsSelector -> {
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            return new EasyEntityQueryable<>(resultProxy, select);
        }
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR>  selectMerge(SQLFuncExpression1<MergeTuple9<T1Proxy,T2Proxy,T3Proxy, T4Proxy,T5Proxy,T6Proxy,T7Proxy,T8Proxy,T9Proxy>, TRProxy> selectExpression){
        return select((a,b,c,d,e,f,g,h,i)->selectExpression.apply(new MergeTuple9<>(a,b,c,d,e,f,g,h,i)));
    }
}
