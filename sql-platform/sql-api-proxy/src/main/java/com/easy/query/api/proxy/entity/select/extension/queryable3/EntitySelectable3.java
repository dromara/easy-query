package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & Draft> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression3<T1Proxy,T2Proxy,T3Proxy, DraftFetcher<TR,TRProxy>> selectExpression){
        DraftFetcher<TR, TRProxy> draftFetcher = selectExpression.apply(get1Proxy(),get2Proxy(),get3Proxy());
        ClientQueryable<TR> select = getClientQueryable3().select(EasyObjectUtil.typeCastNullable(draftFetcher.getDraft().getClass()), columnAsSelector -> {
            draftFetcher.accept(columnAsSelector.getAsSelector());
        });
        TRProxy draftProxy = draftFetcher.getDraftProxy();
        select.getSQLEntityExpressionBuilder().getExpressionContext().setDraftPropTypes(draftFetcher.getDraftPropTypes());
        return new EasyEntityQueryable<>(draftProxy, select);
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & Draft> EntityQueryable<TRProxy, TR> selectDraftMerge(SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, DraftFetcher<TR,TRProxy>> selectExpression){
        return selectDraft((t1,t2,t3)->selectExpression.apply(new MergeTuple3<>(get1Proxy(),get2Proxy(),get3Proxy())));
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR>  select(SQLFuncExpression3<T1Proxy,T2Proxy,T3Proxy, TRProxy> selectExpression){

        TRProxy apply = selectExpression.apply(get1Proxy(),get2Proxy(),get3Proxy());
        SQLSelectAsExpression selectAsExpression = apply.getEntitySQLContext().getSelectAsExpression();
        if(selectAsExpression==null){//全属性映射
            ClientQueryable<TR> select = getClientQueryable3().select(apply.getEntityClass());
            return new EasyEntityQueryable<>(apply, select);
        }else{
            ClientQueryable<TR> select = getClientQueryable3().select(apply.getEntityClass(), columnAsSelector -> {
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            return new EasyEntityQueryable<>(apply, select);
        }
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR>  selectMerge(SQLFuncExpression1<MergeTuple3<T1Proxy,T2Proxy,T3Proxy>, TRProxy> selectExpression){
        return select((a,b,c)->selectExpression.apply(new MergeTuple3<>(a,b,c)));
    }
}
