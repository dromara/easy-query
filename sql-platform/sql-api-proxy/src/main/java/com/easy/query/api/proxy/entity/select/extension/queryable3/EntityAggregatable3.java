package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityAggregatable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {

        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, BigDecimal def) {

        List<TMember> list = getQueryable3().selectColumn((a, b, c) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c);

            return new AnyTypeExpressionImpl<TMember>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.sum(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, EasyObjectUtil.typeCastNullable(BigDecimal.class));
        }).toList();

        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> TMember sumOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {

        List<TMember> list = getQueryable3().selectColumn((a, b, c) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c);

            return new AnyTypeExpressionImpl<TMember>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.sum(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propTypeColumn.getPropertyType());
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }

    default <TMember> TMember maxOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    default <TMember> TMember maxOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {

        List<TMember> list = getQueryable3().selectColumn((a, b, c) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c);

            return new AnyTypeExpressionImpl<TMember>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.max(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propTypeColumn.getPropertyType());
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }

    default <TMember> TMember minOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    default <TMember> TMember minOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {

        List<TMember> list = getQueryable3().selectColumn((a, b, c) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c);

            return new AnyTypeExpressionImpl<TMember>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.min(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propTypeColumn.getPropertyType());
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgBigDecimalOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgFloatOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {


        List<TResult> list = getQueryable3().selectColumn((a, b, c) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c);

            return new AnyTypeExpressionImpl<TResult>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, resultClass);
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }

}
