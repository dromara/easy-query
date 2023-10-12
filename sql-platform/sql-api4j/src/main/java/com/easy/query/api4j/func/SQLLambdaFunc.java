package com.easy.query.api4j.func;

import com.easy.query.api4j.func.concat.DefaultSQLColumnConcatSelector;
import com.easy.query.api4j.func.concat.SQLColumnConcatSelector;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.concat.ConcatExpression;
import com.easy.query.core.func.concat.DefaultColumnConcatSelector;
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
        return ifNull(null, property, def);
    }

    default <T> SQLFunction ifNull(EntitySQLTableOwner<T> tableOwner, Property<T, ?> property, Object def) {
        return getSQLFunc().ifNull(tableOwner, EasyLambdaUtil.getPropertyName(property), def);
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
            SQLColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    default <T> SQLFunction concat(Property<T, ?> property1, Property<T, ?> property2, Property<T, ?> property3) {
        return concat(s -> {
            SQLColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default <T> SQLFunction concat(SQLExpression1<SQLColumnConcatSelector<T>> sqlExpression) {
        List<ConcatExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new DefaultSQLColumnConcatSelector<>(new DefaultColumnConcatSelector(concatExpressions)));
        return concat(concatExpressions);
    }

    default SQLFunction concat(List<ConcatExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }

    default <T> SQLFunction join(String separator, Property<T, ?> property1, Property<T, ?> property2) {
        return join(separator, s -> {
            SQLColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    default <T> SQLFunction join(String separator, Property<T, ?> property1, Property<T, ?> property2, Property<T, ?> property3) {
        return join(separator, s -> {
            SQLColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default <T> SQLFunction join(String separator, SQLExpression1<SQLColumnConcatSelector<T>> sqlExpression) {
        List<ConcatExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new DefaultSQLColumnConcatSelector<>(new DefaultColumnConcatSelector(concatExpressions)));
        return join(separator, concatExpressions);
    }

    default SQLFunction join(String separator, List<ConcatExpression> concatExpressions){
        return getSQLFunc().join(separator,concatExpressions);
    }
}
