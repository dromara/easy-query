package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAggregatable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    /**
     * 防止溢出
     *
     * @param sqlColumn
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLColumn<?,TMember> sqlColumn) {

        return sumBigDecimalOrDefault(sqlColumn, null);
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLColumn<?,TMember> sqlColumn, BigDecimal def) {
        ColumnFunction sumFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = getClientQueryable4().selectAggregateList(sqlColumn.getTable(), sumFunction, sqlColumn.value(), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    default <TMember extends Number> TMember sumOrNull(SQLColumn<?,TMember> sqlColumn) {
        return sumOrDefault(sqlColumn, null);
    }

    default <TMember extends Number> TMember sumOrDefault(SQLColumn<?,TMember> sqlColumn, TMember def) {

        ColumnFunction sumFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = getClientQueryable4().selectAggregateList(sqlColumn.getTable(), sumFunction, sqlColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember> TMember maxOrNull(SQLColumn<?,TMember> sqlColumn) {
        return maxOrDefault(sqlColumn, null);
    }

    default <TMember> TMember maxOrDefault(SQLColumn<?,TMember> sqlColumn, TMember def) {
        ColumnFunction maxFunction = getRuntimeContext().getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = getClientQueryable4().selectAggregateList(sqlColumn.getTable(), maxFunction, sqlColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember> TMember minOrNull(SQLColumn<?,TMember> sqlColumn) {
        return minOrDefault(sqlColumn, null);
    }

    default <TMember> TMember minOrDefault(SQLColumn<?,TMember> sqlColumn, TMember def) {

        ColumnFunction minFunction = getRuntimeContext().getColumnFunctionFactory().createMinFunction();
        List<TMember> result = getClientQueryable4().selectAggregateList(sqlColumn.getTable(), minFunction, sqlColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLColumn<?,TMember> sqlColumn) {
        return avgOrDefault(sqlColumn, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLColumn<?,TMember> sqlColumn) {
        return avgBigDecimalOrDefault(sqlColumn, null);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLColumn<?,TMember> sqlColumn) {
        return avgFloatOrDefault(sqlColumn, null);
    }

    default <TMember extends Number> Double avgOrDefault(SQLColumn<?,TMember> sqlColumn, Double def) {
        return avgOrDefault(sqlColumn, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLColumn<?,TMember> sqlColumn, BigDecimal def) {
        return avgOrDefault(sqlColumn, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLColumn<?,TMember> sqlColumn, Float def) {
        return avgOrDefault(sqlColumn, def, Float.class);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLColumn<?,TMember> sqlColumn, TResult def, Class<TResult> resultClass) {

        ColumnFunction avgFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = getClientQueryable4().selectAggregateList(sqlColumn.getTable(), avgFunction, sqlColumn.value(), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

}
