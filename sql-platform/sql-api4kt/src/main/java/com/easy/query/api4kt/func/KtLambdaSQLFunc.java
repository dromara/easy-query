package com.easy.query.api4kt.func;

import com.easy.query.api4kt.func.column.SQLKtColumnConcatSelectorImpl;
import com.easy.query.api4kt.func.column.SQLKtColumnFuncSelector;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelectorImpl;
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
public interface KtLambdaSQLFunc<T> {
    SQLFunc getSQLFunc();

//    default SQLFunction ifNull(KProperty1<? super T, ?> property, Object def) {
//        return ifNull(null, property, def);
//    }
//
//    default SQLFunction ifNull(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, Object def) {
//        return getSQLFunc().ifNull(tableOwner, EasyKtLambdaUtil.getPropertyName(property), def);
//    }
default SQLFunction ifNull(KProperty1<? super T, ?> property, Object def) {
    return this.<T>ifNull(s -> {
        s.column(property)
                .value(def);
    });
}

    default SQLFunction ifNull(SQLExpression1<SQLKtColumnFuncSelector<T>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLKtColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
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
    default SQLFunction abs(KProperty1<? super T, ?> property) {
        return abs(null, property);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default SQLFunction abs(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property) {
        return getSQLFunc().abs(tableOwner, EasyKtLambdaUtil.getPropertyName(property));
    }

    /**
     * 获取绝对值
     *
     * @param property
     * @return
     */
    default SQLFunction round(KProperty1<? super T, ?> property, int scale) {
        return round(null, property, scale);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner
     * @param property
     * @return
     */
    default SQLFunction round(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, int scale) {
        return getSQLFunc().round(tableOwner, EasyKtLambdaUtil.getPropertyName(property), scale);
    }

    default SQLFunction dateTimeJavaFormat(KProperty1<? super T, ?> property) {
        return dateTimeJavaFormat( property, null);
    }
    default SQLFunction dateTimeJavaFormat(KProperty1<? super T, ?> property, String javaFormat) {
        return dateTimeJavaFormat(null, property, javaFormat);
    }

    default SQLFunction dateTimeJavaFormat(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, String javaFormat) {
        return getSQLFunc().dateTimeJavaFormat(tableOwner, EasyKtLambdaUtil.getPropertyName(property), javaFormat);
    }

    default SQLFunction dateTimeSQLFormat(KProperty1<? super T, ?> property, String format) {
        return dateTimeSQLFormat(null, property, format);
    }

    default SQLFunction dateTimeSQLFormat(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, String format) {
        return getSQLFunc().dateTimeSQLFormat(tableOwner, EasyKtLambdaUtil.getPropertyName(property), format);
    }

    default SQLFunction concat(KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2) {
        return concat(s -> {
            SQLKtColumnFuncSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    default SQLFunction concat(KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2, KProperty1<? super T, ?> property3) {
        return concat(s -> {
            SQLKtColumnFuncSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    default SQLFunction concat(SQLExpression1<SQLKtColumnFuncSelector<T>> sqlExpression) {
        List<ColumnExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLKtColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(concatExpressions)));
        return concat(concatExpressions);
    }

    default SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }

//    default SQLFunction join(String separator, KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2) {
//        return join(separator, s -> {
//            SQLKtColumnFuncSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
//            s1.column(property1)
//                    .column(property2);
//        });
//    }
//
//    default SQLFunction join(String separator, KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2, KProperty1<? super T, ?> property3) {
//        return join(separator, s -> {
//            SQLKtColumnFuncSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
//            s1.column(property1)
//                    .column(property2)
//                    .column(property3);
//        });
//    }
//
//    default SQLFunction join(String separator, SQLExpression1<SQLKtColumnFuncSelector<T>> sqlExpression) {
//        List<ColumnExpression> concatExpressions = new ArrayList<>();
//        sqlExpression.apply(new SQLKtColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(concatExpressions)));
//        return join(separator, concatExpressions);
//    }
//
//    default SQLFunction join(String separator, List<ColumnExpression> concatExpressions){
//        return getSQLFunc().join(separator,concatExpressions);
//    }

    default SQLFunction now() {
        return getSQLFunc().now();
    }

    default SQLFunction utcNow() {
        return getSQLFunc().utcNow();
    }
}
