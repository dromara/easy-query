package com.easy.query.api4j.func;

import com.easy.query.api4j.func.column.SQLColumnConcatSelectorImpl;
import com.easy.query.api4j.func.column.SQLColumnFuncSelector;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelectorImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/12 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLLambdaFunc {
    SQLFunc getSQLFunc();

    default <T> SQLFunction ifNull(Property<T, ?> property, Object def) {
        return this.<T>ifNull(s -> {
            s.column(property)
                    .value(def);
        });
    }

    default <T> SQLFunction ifNull(SQLExpression1<SQLColumnFuncSelector<T>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return ifNull(columnExpressions);
    }

    default <T> SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return getSQLFunc().ifNull(columnExpressions);
    }

    /**
     * 获取绝对值
     *
     * @param property
     * @return
     */
    default <T> SQLFunction abs(Property<T, ?> property) {
        return abs(null, property);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default <T> SQLFunction abs(EntitySQLTableOwner<T> tableOwner, Property<T, ?> property) {
        return getSQLFunc().abs(tableOwner, EasyLambdaUtil.getPropertyName(property));
    }

    /**
     * 获取绝对值
     *
     * @param property
     * @return
     */
    default <T> SQLFunction round(Property<T, ?> property, int scale) {
        return round(null, property, scale);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default <T> SQLFunction round(EntitySQLTableOwner<T> tableOwner, Property<T, ?> property, int scale) {
        return getSQLFunc().round(tableOwner, EasyLambdaUtil.getPropertyName(property), scale);
    }

    default <T> SQLFunction dateTimeJavaFormat(Property<T, ?> property, String javaFormat) {
        return dateTimeJavaFormat(null, property, javaFormat);
    }

    default <T> SQLFunction dateTimeJavaFormat(EntitySQLTableOwner<T> tableOwner, Property<T, ?> property, String javaFormat) {
        return getSQLFunc().dateTimeJavaFormat(tableOwner, EasyLambdaUtil.getPropertyName(property), javaFormat);
    }

    default <T> SQLFunction dateTimeSQLFormat(Property<T, ?> property, String format) {
        return dateTimeSQLFormat(null, property, format);
    }

    default <T> SQLFunction dateTimeSQLFormat(EntitySQLTableOwner<T> tableOwner, Property<T, ?> property, String format) {
        return getSQLFunc().dateTimeSQLFormat(tableOwner, EasyLambdaUtil.getPropertyName(property), format);
    }

    default <T> SQLFunction concat(Property<T, ?> property1, Property<T, ?> property2) {
        return concat(s -> {
            SQLColumnFuncSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    default <T> SQLFunction concat(Property<T, ?> property1, Property<T, ?> property2, Property<T, ?> property3) {
        return this.<T>concat(s -> {
            s.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default <T> SQLFunction concat(SQLExpression1<SQLColumnFuncSelector<T>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return concat(columnExpressions);
    }

    default SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }

    default <T> SQLFunction join(String separator, Property<T, ?> property1, Property<T, ?> property2) {
        return this.<T>join(separator, s -> {
            s.column(property1)
                    .column(property2);
        });
    }

    default <T> SQLFunction join(String separator, Property<T, ?> property1, Property<T, ?> property2, Property<T, ?> property3) {
        return this.<T>join(separator, s -> {
            s.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default <T> SQLFunction join(String separator, SQLExpression1<SQLColumnFuncSelector<T>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return join(separator, columnExpressions);
    }

    default SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
        return getSQLFunc().join(separator, concatExpressions);
    }
}
