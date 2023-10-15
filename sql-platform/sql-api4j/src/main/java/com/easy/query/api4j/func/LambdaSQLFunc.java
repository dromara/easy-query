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
public interface LambdaSQLFunc<T1> {
    SQLFunc getSQLFunc();

    default SQLFunction ifNull(Property<T1, ?> property, Object def) {
        return ifNull(s -> {
            s.column(property)
                    .value(def);
        });
    }

    default SQLFunction ifNull(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return ifNull(columnExpressions);
    }

    default SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return getSQLFunc().ifNull(columnExpressions);
    }

    /**
     * 获取绝对值
     *
     * @param property
     * @return
     */
    default SQLFunction abs(Property<T1, ?> property) {
        return abs(null, property);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default SQLFunction abs(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property) {
        return getSQLFunc().abs(tableOwner, EasyLambdaUtil.getPropertyName(property));
    }

    /**
     * 获取绝对值
     *
     * @param property
     * @return
     */
    default SQLFunction round(Property<T1, ?> property, int scale) {
        return round(null, property, scale);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default SQLFunction round(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property, int scale) {
        return getSQLFunc().round(tableOwner, EasyLambdaUtil.getPropertyName(property), scale);
    }

    default SQLFunction dateTimeJavaFormat(Property<T1, ?> property) {
        return dateTimeJavaFormat(property, null);
    }

    default SQLFunction dateTimeJavaFormat(Property<T1, ?> property, String javaFormat) {
        return dateTimeJavaFormat(null, property, javaFormat);
    }

    default SQLFunction dateTimeJavaFormat(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property, String javaFormat) {
        return getSQLFunc().dateTimeJavaFormat(tableOwner, EasyLambdaUtil.getPropertyName(property), javaFormat);
    }

    default SQLFunction dateTimeSQLFormat(Property<T1, ?> property, String format) {
        return dateTimeSQLFormat(null, property, format);
    }

    default SQLFunction dateTimeSQLFormat(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property, String format) {
        return getSQLFunc().dateTimeSQLFormat(tableOwner, EasyLambdaUtil.getPropertyName(property), format);
    }

    default SQLFunction concat(Property<T1, ?> property1, Property<T1, ?> property2) {
        return concat(s -> {
            SQLColumnFuncSelector<T1> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    default SQLFunction concat(Property<T1, ?> property1, Property<T1, ?> property2, Property<T1, ?> property3) {
        return this.<T1>concat(s -> {
            s.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default SQLFunction concat(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return concat(columnExpressions);
    }

    default SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }
//
//    default SQLFunction join(String separator, Property<T1, ?> property1, Property<T1, ?> property2) {
//        return this.<T1>join(separator, s -> {
//            s.column(property1)
//                    .column(property2);
//        });
//    }
//
//    default SQLFunction join(String separator, Property<T1, ?> property1, Property<T1, ?> property2, Property<T1, ?> property3) {
//        return this.<T1>join(separator, s -> {
//            s.column(property1)
//                    .column(property2)
//                    .column(property3);
//        });
//    }
//
//    default SQLFunction join(String separator, SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
//        List<ColumnExpression> columnExpressions = new ArrayList<>();
//        sqlExpression.apply(new SQLColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
//        return join(separator, columnExpressions);
//    }
//
//    default SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return getSQLFunc().join(separator, concatExpressions);
//    }


    default SQLFunction now() {
        return getSQLFunc().now();
    }

    default SQLFunction utcNow() {
        return getSQLFunc().utcNow();
    }
}
