package com.easy.query.api4kt.func;

import com.easy.query.api4kt.func.concat.DefaultSQLKtColumnConcatSelector;
import com.easy.query.api4kt.func.concat.SQLKtColumnConcatSelector;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.concat.ConcatExpression;
import com.easy.query.core.func.concat.DefaultColumnConcatSelector;
import com.easy.query.core.util.EasyObjectUtil;
import kotlin.reflect.KProperty1;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/12 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtLambdaFunc {
    SQLFunc getSQLFunc();

    default <T> SQLFunction ifNull(KProperty1<? super T, ?> property, Object def) {
        return ifNull(null, property, def);
    }

    default <T> SQLFunction ifNull(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, Object def) {
        return getSQLFunc().ifNull(tableOwner, EasyKtLambdaUtil.getPropertyName(property), def);
    }

    /**
     * 获取绝对值
     *
     * @param property
     * @return
     */
    default <T> SQLFunction abs(KProperty1<? super T, ?> property) {
        return abs(null, property);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default <T> SQLFunction abs(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property) {
        return getSQLFunc().abs(tableOwner, EasyKtLambdaUtil.getPropertyName(property));
    }

    /**
     * 获取绝对值
     *
     * @param property
     * @return
     */
    default <T> SQLFunction round(KProperty1<? super T, ?> property, int scale) {
        return round(null, property, scale);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default <T> SQLFunction round(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, int scale) {
        return getSQLFunc().round(tableOwner, EasyKtLambdaUtil.getPropertyName(property), scale);
    }

    default <T> SQLFunction dateTimeJavaFormat(KProperty1<? super T, ?> property, String javaFormat) {
        return dateTimeJavaFormat(null, property, javaFormat);
    }

    default <T> SQLFunction dateTimeJavaFormat(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, String javaFormat) {
        return getSQLFunc().dateTimeJavaFormat(tableOwner, EasyKtLambdaUtil.getPropertyName(property), javaFormat);
    }

    default <T> SQLFunction dateTimeSQLFormat(KProperty1<? super T, ?> property, String format) {
        return dateTimeSQLFormat(null, property, format);
    }

    default <T> SQLFunction dateTimeSQLFormat(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, String format) {
        return getSQLFunc().dateTimeSQLFormat(tableOwner, EasyKtLambdaUtil.getPropertyName(property), format);
    }

    default <T> SQLFunction concat(KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2) {
        return concat(s -> {
            SQLKtColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    default <T> SQLFunction concat(KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2, KProperty1<? super T, ?> property3) {
        return concat(s -> {
            SQLKtColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default <T> SQLFunction concat(SQLExpression1<SQLKtColumnConcatSelector<T>> sqlExpression) {
        List<ConcatExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new DefaultSQLKtColumnConcatSelector<>(new DefaultColumnConcatSelector(concatExpressions)));
        return concat(concatExpressions);
    }

    default SQLFunction concat(List<ConcatExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }

    default <T> SQLFunction join(String separator, KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2) {
        return join(separator, s -> {
            SQLKtColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    default <T> SQLFunction join(String separator, KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2, KProperty1<? super T, ?> property3) {
        return join(separator, s -> {
            SQLKtColumnConcatSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default <T> SQLFunction join(String separator, SQLExpression1<SQLKtColumnConcatSelector<T>> sqlExpression) {
        List<ConcatExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new DefaultSQLKtColumnConcatSelector<>(new DefaultColumnConcatSelector(concatExpressions)));
        return join(separator, concatExpressions);
    }

    default SQLFunction join(String separator, List<ConcatExpression> concatExpressions){
        return getSQLFunc().join(separator,concatExpressions);
    }
}
