package com.easy.query.core.proxy.extension.functions.cast;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.functions.executor.DateTimeTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.DateTimeTypeExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * create time 2023/12/25 09:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCastDateTimeAvailable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {

    default <T> DateTimeTypeExpression<T> toDateTime(Class<T> clazz){
        return new DateTimeTypeExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.cast(sqlFunction, clazz);
            } else {
                return fx.cast(this.getValue(), clazz);
            }
        }, clazz);
    }
    /**
     * 编译层面欺骗编译器将其视作LocalDateTime
     * @return
     */
    default DateTimeTypeExpression<LocalDateTime> asLocalDateTime() {
        return asDateTime(LocalDateTime.class);
    }

    /**
     * 编译层面欺骗编译器将其视作LocalDate
     * @return
     */
    default DateTimeTypeExpression<LocalDate> asLocalDate() {
        return asDateTime(LocalDate.class);
    }

    /**
     * 编译层面欺骗编译器将其视作Date
     * @return
     */
    default DateTimeTypeExpression<Date> asUtilDate() {
        return asDateTime(Date.class);
    }
    default <TR> DateTimeTypeExpression<TR> asDateTime(Class<TR> clazz) {
        return new DateTimeTypeExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                return ((DSLSQLFunctionAvailable) this).func().apply(fx);
            } else {
                return fx.anySQLFunction("{0}", c -> c.column(this.getTable(), this.getValue()));
            }
        }, clazz);
    }
}
