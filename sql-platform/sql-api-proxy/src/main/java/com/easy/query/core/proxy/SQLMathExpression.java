package com.easy.query.core.proxy;

import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;

import java.math.BigDecimal;

/**
 * create time 2024/2/21 10:58
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLMathExpression {

    static  <TProperty extends Number> NumberTypeExpression<TProperty> abs(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Abs);
        }, propTypeColumn.getPropertyType());
    }

    /**
     * 是一种数学类方法，该方法返回一个整数，该整数指定数字的符号
     *
     * @param propTypeColumn
     * @return 如果值等于零返回0,如果值大于零返回1,如果值小于零返回-1
     * @param <TProperty>
     */
    static <TProperty extends Number> NumberTypeExpression<Integer> sign(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Abs);
        }, Integer.class);
    }

    /**
     * 向下取整
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    static <TProperty extends Number> NumberTypeExpression<TProperty> floor(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Floor);
        }, propTypeColumn.getPropertyType());
    }

    /**
     * 向上取整
     * @return
     */
    static <TProperty extends Number> NumberTypeExpression<TProperty> ceiling(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Ceiling);
        }, propTypeColumn.getPropertyType());
    }

    /**
     * 四舍五入
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> round(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Round);
        }, BigDecimal.class);
    }

    /**
     * 四舍五入
     * @param propTypeColumn
     * @param decimals
     * @return
     * @param <TProperty>
     */
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> round(PropTypeColumn<TProperty> propTypeColumn, int decimals) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
                o.value(decimals);
            }, MathMethodEnum.Round);
        }, BigDecimal.class);
    }

    /**
     * 指定幂的数字 d
     * @param propTypeColumn d
     * @return 数字 e 的 d 次幂。
     * @param <TProperty>
     */
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> exp(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Exp);
        }, BigDecimal.class);
    }

    /**
     * 要查找其对数的数字。
     * @param propTypeColumn d
     * @return d 参数 正 d 的自然对数，即 ln d 或 log ed 零
     * @param <TProperty>
     */
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> log(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Log);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> log(PropTypeColumn<TProperty> propTypeColumn, BigDecimal newBase) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
                o.value(newBase);
            }, MathMethodEnum.Log);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> log10(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Log10);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> pow(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Pow);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> pow(PropTypeColumn<TProperty> propTypeColumn, BigDecimal exponent) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
                o.value(exponent);
            }, MathMethodEnum.Pow);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> sqrt(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Sqrt);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> cos(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Cos);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> sin(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Sin);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> tan(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Tan);
        }, BigDecimal.class);
    }
    default <TProperty extends Number> NumberTypeExpression<BigDecimal> acos(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Acos);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> asin(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Asin);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> atan(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Atan);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> atan2(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Atan2);
        }, BigDecimal.class);
    }
    static <TProperty extends Number> NumberTypeExpression<BigDecimal> truncate(PropTypeColumn<TProperty> propTypeColumn) {
        return new NumberTypeExpressionImpl<>(propTypeColumn.getEntitySQLContext(), propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o,propTypeColumn);
            }, MathMethodEnum.Truncate);
        }, BigDecimal.class);
    }
}
