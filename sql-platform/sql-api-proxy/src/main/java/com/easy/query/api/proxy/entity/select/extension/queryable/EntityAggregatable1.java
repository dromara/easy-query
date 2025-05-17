package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.extension.functions.executor.impl.AnyTypeExpressionImpl;
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
public interface EntityAggregatable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntityQueryableAvailable<T1Proxy, T1>, ClientEntityQueryableAvailable<T1> {

    /**
     * 防止溢出
     *
     * @param columnSelector
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return sumBigDecimalOrDefault(columnSelector, null);
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector, BigDecimal def) {

        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.sum(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, EasyObjectUtil.typeCastNullable(BigDecimal.class));
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return (BigDecimal) resultMember;
    }

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return sumOrDefault(columnSelector, null);
    }

    default <TMember extends Number> TMember sumOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector, TMember def) {

        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.sum(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propTypeColumn.getPropertyType());
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return resultMember;
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return maxOrDefault(columnSelector, null);
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector, TMember def) {
        ValueHolder<Class<?>> propertyTypeHolder = new ValueHolder<>();
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            propertyTypeHolder.setValue(propTypeColumn.getPropertyType());
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.max(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propertyTypeHolder.getValue());
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return resultMember;
    }

    default <TMember> TMember minOrNull(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return minOrDefault(columnSelector, null);
    }

    default <TMember> TMember minOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector, TMember def) {

        ValueHolder<Class<?>> propertyTypeHolder = new ValueHolder<>();
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            propertyTypeHolder.setValue(propTypeColumn.getPropertyType());
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.min(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, def != null ? def.getClass() : propertyTypeHolder.getValue());
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return resultMember;
    }

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, Double.class);
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return null;
        }
        return (Double) resultMember;
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, BigDecimal.class);
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return null;
        }
        return (BigDecimal) resultMember;
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, Float.class);
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return null;
        }
        return (Float) resultMember;
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector, Double def) {
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, Double.class);
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return (Double) resultMember;
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelectorExpression, BigDecimal def) {
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, BigDecimal.class);
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return (BigDecimal) resultMember;
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelectorExpression, Float def) {
        List<TMember> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TMember> propTypeColumn = columnSelectorExpression.apply(s);
            return new AnyTypeExpressionImpl<TMember>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, Float.class);
        }).toList();
        TMember resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return (Float) resultMember;
    }

    default BigDecimal avgOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<BigDecimal>> columnSelector, BigDecimal def) {
        List<BigDecimal> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<BigDecimal> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<BigDecimal>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, BigDecimal.class);
        }).toList();
        BigDecimal resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return resultMember;
    }

    default Float avgOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<Float>> columnSelector, Float def) {
        List<Float> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<Float> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<Float>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, Float.class);
        }).toList();
        Float resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return resultMember;
    }

    default <TResult extends Number> TResult avgOrDefault(SQLFuncExpression1<T1Proxy, PropTypeColumn<TResult>> columnSelector, TResult def, Class<TResult> resultClass) {
        List<TResult> list = getQueryable().selectColumn(s -> {
            PropTypeColumn<TResult> propTypeColumn = columnSelector.apply(s);
            return new AnyTypeExpressionImpl<TResult>(s.getEntitySQLContext(), s.getTable(), propTypeColumn.getValue(), fx -> {
                return fx.avg(x -> {
                    PropTypeColumn.columnFuncSelector(x, propTypeColumn);
                });
            }, resultClass);
        }).toList();
        TResult resultMember = EasyCollectionUtil.firstOrNull(list);
        if (resultMember == null) {
            return def;
        }
        return resultMember;
    }

}
