package com.easy.query.core.proxy.sql;

import com.easy.query.api.proxy.base.ListProxy;
import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasySelectManyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.draft.proxy.DraftProxy;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.List;
import java.util.Objects;

/**
 * create time 2023/12/2 17:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class Select {

    public static SQLSelectExpression of(boolean condition, SQLSelectExpression... selects) {
        if (condition) {
            return of(selects);
        }
        return SQLSelectExpression.empty;
    }

    public static SQLSelectExpression of(SQLSelectExpression... selects) {
        if (EasyArrayUtil.isNotEmpty(selects)) {
            SQLSelectExpression firstSQLSelectAs = selects[0];
            for (int i = 1; i < selects.length; i++) {
                firstSQLSelectAs = firstSQLSelectAs._concat(selects[i]);
            }
            return firstSQLSelectAs;
        }
        return SQLSelectExpression.empty;
    }

    public static SQLSelectAsExpression of(SQLSelectAsExpression... selectAss) {

        if (EasyArrayUtil.isNotEmpty(selectAss)) {
            SQLSelectAsExpression firstSQLSelectAs = selectAss[0];
            for (int i = 1; i < selectAss.length; i++) {
                firstSQLSelectAs = firstSQLSelectAs._concat(selectAss[i]);
            }
            return firstSQLSelectAs;
        }
        return SQLSelectAsExpression.empty;
    }
    public static Draft DRAFT = new Draft();


    public static <TR, TRProxy> void setDraftPropTypes(ClientQueryable<TR> select, TRProxy trProxy) {
        ExpressionContext expressionContext = select.getSQLEntityExpressionBuilder().getExpressionContext();
        if (trProxy instanceof DraftProxy) {
            DraftProxy draftProxy = (DraftProxy) trProxy;
            expressionContext.setResultPropTypes(draftProxy.getDraftPropTypes());
        } else if(trProxy instanceof MapTypeProxy){
            MapTypeProxy mapTypeProxy = (MapTypeProxy) trProxy;
            expressionContext.setResultPropTypes(mapTypeProxy._getResultPropTypes().toArray(new Class[0]));
        } else{
            if(expressionContext.getResultPropTypes()!=null){
                expressionContext.setResultPropTypes(null);
            }
        }
    }

    public static <TQueryable, TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectList(ListProxy<TRProxy, TR> listProxy, TQueryable queryable) {

        SQLQueryable<TRProxy, TR> sqlQueryable = listProxy.getSqlQueryable();
        Objects.requireNonNull(sqlQueryable, "select columns null result class");
        EntityQueryable<ListProxy<TRProxy, TR>, List<TR>> listProxyListEntityQueryable = new EasySelectManyQueryable<>(EasyObjectUtil.typeCastNullable(queryable), listProxy, sqlQueryable.getNavValue());
        return EasyObjectUtil.typeCastNullable(listProxyListEntityQueryable);
    }

    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectProxy(TRProxy resultProxy, ClientQueryable<?> queryable) {

        Objects.requireNonNull(resultProxy, "select null result class");
        if (resultProxy instanceof ListProxy) {
            ListProxy<TRProxy, TR> listProxy = (ListProxy<TRProxy, TR>) resultProxy;
            SQLQueryable<TRProxy, TR> sqlQueryable = listProxy.getSqlQueryable();
            Objects.requireNonNull(sqlQueryable, "select columns null result class");

            EntityQueryable<ListProxy<TRProxy, TR>, List<TR>> listProxyListEntityQueryable = new EasySelectManyQueryable<>(queryable, listProxy, sqlQueryable.getNavValue());
            return EasyObjectUtil.typeCastNullable(listProxyListEntityQueryable);
        }
//        if (resultProxy instanceof FlatListProxy) {
//            FlatListProxy<TRProxy, TR> listProxy = (FlatListProxy<TRProxy, TR>) resultProxy;
//            SQLQueryable<TRProxy, TR> sqlQueryable = listProxy.getSqlQueryable();
//            Objects.requireNonNull(sqlQueryable, "select columns null result class");
//
//            EntityQueryable<FlatListProxy<TRProxy, TR>, TR> listProxyListEntityQueryable = new EasySelectMany2Queryable<>(queryable, listProxy, sqlQueryable.getNavValue());
//            return EasyObjectUtil.typeCastNullable(listProxyListEntityQueryable);
//        }

        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
        if (selectAsExpression == null) {//全属性映射
            TableAvailable tableOrNull = resultProxy.getTableOrNull();
            if (tableOrNull == null) {
                ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass());
                Select.setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            } else {
                if(resultProxy instanceof SQLSelectExpression){
                    SQLSelectExpression resultProxySelectExpression = (SQLSelectExpression) resultProxy;
                    ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), columnAsSelector -> {
                        resultProxySelectExpression.accept(columnAsSelector.getAsSelector());
                    });
                    Select.setDraftPropTypes(select, resultProxy);
                    return new EasyEntityQueryable<>(resultProxy, select);
                }
                ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), columnAsSelector -> {
                    columnAsSelector.getAsSelector().columnAll(tableOrNull);
                });
                Select.setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            }
        } else {
            ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), columnAsSelector -> {
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            Select.setDraftPropTypes(select, resultProxy);
            return new EasyEntityQueryable<>(resultProxy, select);
        }
    }
}
