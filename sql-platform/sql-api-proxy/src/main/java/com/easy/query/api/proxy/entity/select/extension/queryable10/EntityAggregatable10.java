package com.easy.query.api.proxy.entity.select.extension.queryable10;

import com.easy.query.core.common.tuple.MergeTuple10;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression10;
import com.easy.query.core.func.SQLFunction;
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
public interface EntityAggregatable10<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9,
        T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> extends ClientEntityQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10>, EntityQueryable10Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9,T10Proxy,T10> {


    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression) {

        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, BigDecimal def) {
        SQLColumn<?,TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy());
        SQLFunction sqlFunction = memberSQLColumn.asAny().sum().func().apply(getRuntimeContext().fx());
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), sqlFunction, memberSQLColumn.getValue(), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> TMember sumOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, TMember def) {

        SQLColumn<?,TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy());
        SQLFunction sqlFunction = memberSQLColumn.asAny().sum().func().apply(getRuntimeContext().fx());
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), sqlFunction, memberSQLColumn.getValue(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember> TMember maxOrNull(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    default <TMember> TMember maxOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, TMember def) {
        SQLColumn<?,TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy());
        SQLFunction sqlFunction = memberSQLColumn.asAny().max().func().apply(getRuntimeContext().fx());
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), sqlFunction, memberSQLColumn.getValue(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember> TMember minOrNull(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    default <TMember> TMember minOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, TMember def) {

        SQLColumn<?,TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy());
        SQLFunction sqlFunction = memberSQLColumn.asAny().min().func().apply(getRuntimeContext().fx());
        List<TMember> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), sqlFunction, memberSQLColumn.getValue(), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression) {
        return avgBigDecimalOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression) {
        return avgFloatOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy, SQLColumn<?,TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {

        SQLColumn<?,TMember> memberSQLColumn = columnSelectorExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), get10Proxy());
        SQLFunction sqlFunction = memberSQLColumn.asAny().avg().func().apply(getRuntimeContext().fx());
        List<TResult> result = getClientQueryable10().selectAggregateList(memberSQLColumn.getTable(), sqlFunction, memberSQLColumn.getValue(), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }


    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression) {
        return sumBigDecimalOrNull((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, BigDecimal def) {
        return sumBigDecimalOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression) {
        return sumOrNull((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, TMember def) {
        return sumOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression) {
        return maxOrNull((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, TMember def) {
        return maxOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression) {
        return minOrNull((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, TMember def) {
        return minOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression) {
        return avgOrNull((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression) {
        return avgBigDecimalOrNull((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression) {
        return avgFloatOrNull((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, BigDecimal def) {
        return avgBigDecimalOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, Float def) {
        return avgFloatOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLFuncExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>, SQLColumn<?,TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return avgOrDefault((t, t1, t2, t4, t5, t6, t7, t8, t9, t10) -> {
            return columnSelectorExpression.apply(new MergeTuple10<>(t, t1, t2, t4, t5, t6, t7, t8, t9, t10));
        }, def, resultClass);
    }
}
