package com.easy.query.core.proxy.sql;

import com.easy.query.api.proxy.base.ListProxy;
import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasySelectManyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft10;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.draft.Draft5;
import com.easy.query.core.proxy.core.draft.Draft6;
import com.easy.query.core.proxy.core.draft.Draft7;
import com.easy.query.core.proxy.core.draft.Draft8;
import com.easy.query.core.proxy.core.draft.Draft9;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.proxy.core.draft.DraftFetcherImpl;
import com.easy.query.core.proxy.core.draft.proxy.Draft10Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft1Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft3Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft4Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft5Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft6Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft7Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft8Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft9Proxy;
import com.easy.query.core.proxy.core.draft.proxy.DraftProxy;
import com.easy.query.core.proxy.impl.SQLSelectGroupKeyAsImpl;
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

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param <T1>
     * @return
     */
    @Deprecated
    public static <T1> DraftFetcher<Draft1<T1>, Draft1Proxy<T1>> draft(PropTypeColumn<T1> column1) {
        Draft1Proxy<T1> draft1Proxy = new Draft1Proxy<>();
        DraftFetcherImpl<Draft1<T1>, Draft1Proxy<T1>> draftFetcher = new DraftFetcherImpl<>(new Draft1<>(), draft1Proxy);
        draftFetcher.fetch(0, column1, draft1Proxy.value1());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param <T1>
     * @param <T2>
     * @return
     */
    @Deprecated
    public static <T1, T2> DraftFetcher<Draft2<T1, T2>, Draft2Proxy<T1, T2>> draft(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2) {
        Draft2Proxy<T1, T2> draft2Proxy = new Draft2Proxy<>();
        DraftFetcherImpl<Draft2<T1, T2>, Draft2Proxy<T1, T2>> draftFetcher = new DraftFetcherImpl<>(new Draft2<>(), draft2Proxy);
        draftFetcher.fetch(0, column1, draft2Proxy.value1());
        draftFetcher.fetch(1, column2, draft2Proxy.value2());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3> DraftFetcher<Draft3<T1, T2, T3>, Draft3Proxy<T1, T2, T3>> draft(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3) {
        Draft3Proxy<T1, T2, T3> draft3Proxy = new Draft3Proxy<>();
        DraftFetcherImpl<Draft3<T1, T2, T3>, Draft3Proxy<T1, T2, T3>> draftFetcher = new DraftFetcherImpl<>(new Draft3<>(), draft3Proxy);
        draftFetcher.fetch(0, column1, draft3Proxy.value1());
        draftFetcher.fetch(1, column2, draft3Proxy.value2());
        draftFetcher.fetch(2, column3, draft3Proxy.value3());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param column4
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3, T4> DraftFetcher<Draft4<T1, T2, T3, T4>, Draft4Proxy<T1, T2, T3, T4>> draft(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4) {
        Draft4Proxy<T1, T2, T3, T4> draft4Proxy = new Draft4Proxy<>();
        DraftFetcherImpl<Draft4<T1, T2, T3, T4>, Draft4Proxy<T1, T2, T3, T4>> draftFetcher = new DraftFetcherImpl<>(new Draft4<>(), draft4Proxy);
        draftFetcher.fetch(0, column1, draft4Proxy.value1());
        draftFetcher.fetch(1, column2, draft4Proxy.value2());
        draftFetcher.fetch(2, column3, draft4Proxy.value3());
        draftFetcher.fetch(3, column4, draft4Proxy.value4());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param column4
     * @param column5
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3, T4, T5> DraftFetcher<Draft5<T1, T2, T3, T4, T5>, Draft5Proxy<T1, T2, T3, T4, T5>> draft(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4, PropTypeColumn<T5> column5) {
        Draft5Proxy<T1, T2, T3, T4, T5> draft5Proxy = new Draft5Proxy<>();
        DraftFetcherImpl<Draft5<T1, T2, T3, T4, T5>, Draft5Proxy<T1, T2, T3, T4, T5>> draftFetcher = new DraftFetcherImpl<>(new Draft5<>(), draft5Proxy);
        draftFetcher.fetch(0, column1, draft5Proxy.value1());
        draftFetcher.fetch(1, column2, draft5Proxy.value2());
        draftFetcher.fetch(2, column3, draft5Proxy.value3());
        draftFetcher.fetch(3, column4, draft5Proxy.value4());
        draftFetcher.fetch(4, column5, draft5Proxy.value5());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param column4
     * @param column5
     * @param column6
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3, T4, T5, T6> DraftFetcher<Draft6<T1, T2, T3, T4, T5, T6>, Draft6Proxy<T1, T2, T3, T4, T5, T6>> draft(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6) {
        Draft6Proxy<T1, T2, T3, T4, T5, T6> draft6Proxy = new Draft6Proxy<>();
        DraftFetcherImpl<Draft6<T1, T2, T3, T4, T5, T6>, Draft6Proxy<T1, T2, T3, T4, T5, T6>> draftFetcher =
                new DraftFetcherImpl<>(new Draft6<>(), draft6Proxy);
        draftFetcher.fetch(0, column1, draft6Proxy.value1());
        draftFetcher.fetch(1, column2, draft6Proxy.value2());
        draftFetcher.fetch(2, column3, draft6Proxy.value3());
        draftFetcher.fetch(3, column4, draft6Proxy.value4());
        draftFetcher.fetch(4, column5, draft6Proxy.value5());
        draftFetcher.fetch(5, column6, draft6Proxy.value6());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param column4
     * @param column5
     * @param column6
     * @param column7
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3, T4, T5, T6, T7> DraftFetcher<Draft7<T1, T2, T3, T4, T5, T6, T7>, Draft7Proxy<T1, T2, T3, T4, T5, T6, T7>> draft(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7) {
        Draft7Proxy<T1, T2, T3, T4, T5, T6, T7> draft7Proxy = new Draft7Proxy<>();
        DraftFetcherImpl<Draft7<T1, T2, T3, T4, T5, T6, T7>, Draft7Proxy<T1, T2, T3, T4, T5, T6, T7>> draftFetcher =
                new DraftFetcherImpl<>(new Draft7<>(), draft7Proxy);
        draftFetcher.fetch(0, column1, draft7Proxy.value1());
        draftFetcher.fetch(1, column2, draft7Proxy.value2());
        draftFetcher.fetch(2, column3, draft7Proxy.value3());
        draftFetcher.fetch(3, column4, draft7Proxy.value4());
        draftFetcher.fetch(4, column5, draft7Proxy.value5());
        draftFetcher.fetch(5, column6, draft7Proxy.value6());
        draftFetcher.fetch(6, column7, draft7Proxy.value7());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param column4
     * @param column5
     * @param column6
     * @param column7
     * @param column8
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3, T4, T5, T6, T7, T8> DraftFetcher<Draft8<T1, T2, T3, T4, T5, T6, T7, T8>, Draft8Proxy<T1, T2, T3, T4, T5, T6, T7, T8>> draft(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8) {
        Draft8Proxy<T1, T2, T3, T4, T5, T6, T7, T8> draft8Proxy = new Draft8Proxy<>();
        DraftFetcherImpl<Draft8<T1, T2, T3, T4, T5, T6, T7, T8>, Draft8Proxy<T1, T2, T3, T4, T5, T6, T7, T8>> draftFetcher =
                new DraftFetcherImpl<>(new Draft8<>(), draft8Proxy);
        draftFetcher.fetch(0, column1, draft8Proxy.value1());
        draftFetcher.fetch(1, column2, draft8Proxy.value2());
        draftFetcher.fetch(2, column3, draft8Proxy.value3());
        draftFetcher.fetch(3, column4, draft8Proxy.value4());
        draftFetcher.fetch(4, column5, draft8Proxy.value5());
        draftFetcher.fetch(5, column6, draft8Proxy.value6());
        draftFetcher.fetch(6, column7, draft8Proxy.value7());
        draftFetcher.fetch(7, column8, draft8Proxy.value8());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param column4
     * @param column5
     * @param column6
     * @param column7
     * @param column8
     * @param column9
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @param <T9>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> DraftFetcher<Draft9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Draft9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9>> draft(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9) {
        Draft9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9> draft9Proxy = new Draft9Proxy<>();
        DraftFetcherImpl<Draft9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Draft9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9>> draftFetcher =
                new DraftFetcherImpl<>(new Draft9<>(), draft9Proxy);
        draftFetcher.fetch(0, column1, draft9Proxy.value1());
        draftFetcher.fetch(1, column2, draft9Proxy.value2());
        draftFetcher.fetch(2, column3, draft9Proxy.value3());
        draftFetcher.fetch(3, column4, draft9Proxy.value4());
        draftFetcher.fetch(4, column5, draft9Proxy.value5());
        draftFetcher.fetch(5, column6, draft9Proxy.value6());
        draftFetcher.fetch(6, column7, draft9Proxy.value7());
        draftFetcher.fetch(7, column8, draft9Proxy.value8());
        draftFetcher.fetch(8, column9, draft9Proxy.value9());
        return draftFetcher;
    }

    /**
     * 请使用 Select.DRAFT.of
     *
     * @param column1
     * @param column2
     * @param column3
     * @param column4
     * @param column5
     * @param column6
     * @param column7
     * @param column8
     * @param column9
     * @param column10
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param <T5>
     * @param <T6>
     * @param <T7>
     * @param <T8>
     * @param <T9>
     * @param <T10>
     * @return
     */
    @Deprecated
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> DraftFetcher<Draft10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Draft10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> draft(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9, PropTypeColumn<T10> column10) {
        Draft10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> draft10Proxy = new Draft10Proxy<>();
        DraftFetcherImpl<Draft10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Draft10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> draftFetcher =
                new DraftFetcherImpl<>(new Draft10<>(), draft10Proxy);

        draftFetcher.fetch(0, column1, draft10Proxy.value1());
        draftFetcher.fetch(1, column2, draft10Proxy.value2());
        draftFetcher.fetch(2, column3, draft10Proxy.value3());
        draftFetcher.fetch(3, column4, draft10Proxy.value4());
        draftFetcher.fetch(4, column5, draft10Proxy.value5());
        draftFetcher.fetch(5, column6, draft10Proxy.value6());
        draftFetcher.fetch(6, column7, draft10Proxy.value7());
        draftFetcher.fetch(7, column8, draft10Proxy.value8());
        draftFetcher.fetch(8, column9, draft10Proxy.value9());
        draftFetcher.fetch(9, column10, draft10Proxy.value10());
        return draftFetcher;
    }
//
//    public static <T1, T2, T3, T4> DraftFetcher<Draft4<T1, T2, T3, T4>> draft(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4) {
//        DraftFetcher<Draft4<T1, T2, T3, T4>> draftFetcher = new DraftFetcherImpl<>(new Draft4<>());
//        draftFetcher.fetch(column1, column2, column3,column4);
//        return draftFetcher;
//    }

//    public static <TSubQuery> SQLSelectAsExpression subQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, TablePropColumn propColumn) {
//        return new SQLSelectAsImpl(s -> {
//            throw new UnsupportedOperationException();
//        }, s -> {
//            s.columnSubQueryAs(subQueryableFunc, propColumn.getValue());
//        }, s -> {
//            throw new UnsupportedOperationException();
//        });
//    }


    public static SQLSelectExpression groupKeys(int index) {
        return new SQLSelectGroupKeyAsImpl(index);
    }

    //PropTypeColumn<Object>
//    public static PropTypeColumn<Object> draftSQL(String sqlSegment) {
//        return draftSQL(sqlSegment, f -> {
//        });
//    }
//
//    public static PropTypeColumn<Object> draftSQL(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
//        return new SQLNativeDraftImpl((alias, f) -> {
//            f.sqlNativeSegment(sqlSegment, c -> {
//                if (alias != null) {
//                    c.setPropertyAlias(alias);
//                }
//                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
//            });
//        });
//    }


    //    public static <TSubQuery> PropTypeColumn<TSubQuery> draftSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc) {
//        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply();
//        return new SQLDraftAsSelectImpl<>((alias,f)->{
//            f.columnSubQueryAs(()->subQueryQuery, alias);
//        },subQueryQuery.queryClass());
//    }
//
//    public static SQLSelectExpression sql(String sqlSegment) {
//        return sql(sqlSegment, f -> {
//        });
//    }
//
//    public static SQLSelectExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
//        return new SQLSelectImpl(f -> {
//            f.sqlNativeSegment(sqlSegment, c -> {
//                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
//            });
//        });
//    }
//
//    public static SQLSelectAsExpression sql(String sqlSegment, PropColumn sqlColumnAlias) {
//        return sql(sqlSegment, f -> {
//        }, sqlColumnAlias);
//    }
//
//    public static SQLSelectAsExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume, PropColumn sqlColumnAlias) {
//        return new SQLSelectNativeAsImpl(f -> {
//            f.sqlNativeSegment(sqlSegment, c -> {
//                c.setPropertyAlias(sqlColumnAlias.getValue());
//                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
//            });
//        });
//    }
//
//    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> SQLSelectAsExpression sqlAlias(TRProxy proxy, String sqlSegment) {
//        return sqlAlias(proxy, sqlSegment, f -> {
//        });
//    }
//
//    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> SQLSelectAsExpression sqlAlias(TRProxy proxy, String sqlSegment, SQLExpression1<SQLAliasNativeProxyExpressionContext<TRProxy, TR>> contextConsume) {
//        return new SQLSelectNativeAsImpl(s -> {
//            s.sqlNativeSegment(sqlSegment, c -> {
//                contextConsume.apply(new SQLAliasNativeProxyExpressionContextImpl<>(c));
//            });
//        });
//    }
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
