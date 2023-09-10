package com.easy.query.api.proxy.select.extension.queryable10;

import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
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
public interface ProxyAggregatable10<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9,
        T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> extends ClientProxyQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10>, ProxyQueryable10Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9,T10Proxy,T10> {


    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression) {

        return sumBigDecimalOrDefault10(columnSelectorExpression, null);
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(new Tuple10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy()));
        ColumnFunction sumFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    default <TMember extends Number> TMember sumOrNull10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression) {
        return sumOrDefault10(columnSelectorExpression, null);
    }

    default <TMember extends Number> TMember sumOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, TMember def) {

        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(new Tuple10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy()));
        ColumnFunction sumFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), sumFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember> TMember maxOrNull10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression) {
        return maxOrDefault10(columnSelectorExpression, null);
    }

    default <TMember> TMember maxOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(new Tuple10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy()));
        ColumnFunction maxFunction = getRuntimeContext().getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), maxFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember> TMember minOrNull10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression) {
        return minOrDefault10(columnSelectorExpression, null);
    }

    default <TMember> TMember minOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, TMember def) {

        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(new Tuple10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy()));
        ColumnFunction minFunction = getRuntimeContext().getColumnFunctionFactory().createMinFunction();
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), minFunction, memberSQLColumn.value(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember extends Number> Double avgOrNull10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression) {
        return avgOrDefault10(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression) {
        return avgBigDecimalOrDefault10(columnSelectorExpression, null);
    }

    default <TMember extends Number> Float avgFloatOrNull10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression) {
        return avgFloatOrDefault10(columnSelectorExpression, null);
    }

    default <TMember extends Number> Double avgOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault10(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault10(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, Float def) {
        return avgOrDefault10(columnSelectorExpression, def, Float.class);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault10(SQLFuncExpression1<Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {

        SQLColumn<TMember> memberSQLColumn = columnSelectorExpression.apply(new Tuple10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy()));
        ColumnFunction avgFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), avgFunction, memberSQLColumn.value(), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

}
