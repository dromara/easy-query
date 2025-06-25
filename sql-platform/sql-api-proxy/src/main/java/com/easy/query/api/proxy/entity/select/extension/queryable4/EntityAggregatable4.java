package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
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
public interface EntityAggregatable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {

        return sumBigDecimalOrDefault(columnSelectorExpression, null);
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, BigDecimal def) {

        List<TMember> list = getQueryable4().selectColumn((a, b, c, d) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c, d);

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

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return sumOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> TMember sumOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {

        List<TMember> list = getQueryable4().selectColumn((a, b, c, d) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c, d);

            return new AnyTypeExpressionImpl<TMember>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.sum(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propTypeColumn.getPropertyType());
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }

    default <TMember> TMember maxOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return maxOrDefault(columnSelectorExpression, null);
    }

    default <TMember> TMember maxOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {

        List<TMember> list = getQueryable4().selectColumn((a, b, c, d) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c, d);

            return new AnyTypeExpressionImpl<TMember>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.max(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propTypeColumn.getPropertyType());
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }

    default <TMember> TMember minOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return minOrDefault(columnSelectorExpression, null);
    }

    default <TMember> TMember minOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {

        List<TMember> list = getQueryable4().selectColumn((a, b, c, d) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c, d);

            return new AnyTypeExpressionImpl<TMember>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.min(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propTypeColumn.getPropertyType());
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgOrDefault(columnSelectorExpression, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgBigDecimalOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgFloatOrDefault(columnSelectorExpression, null);
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault(columnSelectorExpression, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return avgOrDefault(columnSelectorExpression, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, Float def) {
        return avgOrDefault(columnSelectorExpression, def, Float.class);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, PropTypeColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {

        List<TResult> list = getQueryable4().selectColumn((a, b, c, d) -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(a, b, c, d);

            return new AnyTypeExpressionImpl<TResult>(a.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, resultClass);
        }).toList();
        return EasyCollectionUtil.firstOrDefault(list, def);
    }


    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression) {
        return sumBigDecimalOrNull((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return sumBigDecimalOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression) {
        return sumOrNull((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {
        return sumOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression) {
        return maxOrNull((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {
        return maxOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression) {
        return minOrNull((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, TMember def) {
        return minOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgOrNull((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgBigDecimalOrNull((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression) {
        return avgFloatOrNull((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, Double def) {
        return avgOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        return avgBigDecimalOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, Float def) {
        return avgFloatOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLFuncExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>, PropTypeColumn<TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return avgOrDefault((t, t1, t2, t4) -> {
            return columnSelectorExpression.apply(new MergeTuple4<>(t, t1, t2, t4));
        }, def, resultClass);
    }
}
