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

    /**
     * 如果property对应的值为null则返回def值
     *
     * @param property 属性列
     * @param def 默认值
     * @return ifNull函数
     */
    default SQLFunction ifNull(KProperty1<? super T, ?> property, Object def) {
        return this.<T>ifNull(s -> {
            s.column(property)
                    .value(def);
        });
    }

    /**
     * 如果选择的对应的值为null则返回默认值
     *
     * @param sqlExpression 属性选择函数
     * @return ifNull函数
     */
    default SQLFunction ifNull(SQLExpression1<SQLKtColumnFuncSelector<T>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLKtColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return ifNull(columnExpressions);
    }

    /**
     * 如果属性常量表达式集合对应的值为null则返回默认值
     *
     * @param columnExpressions 常量表达式集合
     * @return ifNull函数
     */
    default SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return getSQLFunc().ifNull(columnExpressions);
    }

    /**
     * 获取绝对值
     *
     * @param property 属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(KProperty1<? super T, ?> property) {
        return abs(null, property);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner 属性所属表
     * @param property 属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property) {
        return getSQLFunc().abs(tableOwner, EasyKtLambdaUtil.getPropertyName(property));
    }

    /**
     * 按照指定的小数位数进行四舍五入运算
     *
     * @param property 属性列
     * @param scale 保留小数位数
     * @return 四舍五入函数
     */
    default SQLFunction round(KProperty1<? super T, ?> property, int scale) {
        return round(null, property, scale);
    }

    /**
     * 按照指定的小数位数进行四舍五入运算
     *
     * @param tableOwner 列所属表
     * @param property 属性列
     * @param scale 保留小数位数
     * @return round函数
     */
    default SQLFunction round(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, int scale) {
        return getSQLFunc().round(tableOwner, EasyKtLambdaUtil.getPropertyName(property), scale);
    }

    /**
     * 对时间格式的列进行格式化 默认 yyyy-MM-dd HH:mm:ss.fff
     *
     * @param property 时间格式列
     * @return 时间格式函数
     */
    default SQLFunction dateTimeFormat(KProperty1<? super T, ?> property) {
        return dateTimeFormat(property, null);
    }

    /**
     * 对时间格式的列进行格式化 默认 yyyy-MM-dd HH:mm:ss.fff
     * {@code javaFormat} yyyy-MM-dd HH:mm:ss | yyyy/MM/dd HH:mm:ss | yyyy-MM-dd ...
     *
     * @param property 时间格式列
     * @param javaFormat java格式化
     * @return 时间格式函数
     */
    default SQLFunction dateTimeFormat(KProperty1<? super T, ?> property, String javaFormat) {
        return dateTimeFormat(null, property, javaFormat);
    }

    /**
     * 对时间格式的列进行格式化 默认 yyyy-MM-dd HH:mm:ss.fff
     * {@code javaFormat} yyyy-MM-dd HH:mm:ss | yyyy/MM/dd HH:mm:ss | yyyy-MM-dd ...
     *
     * @param tableOwner 属性对应的表
     * @param property 时间格式列
     * @param javaFormat java格式化
     * @return 时间格式函数
     */
    default SQLFunction dateTimeFormat(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, String javaFormat) {
        return getSQLFunc().dateTimeFormat(tableOwner, EasyKtLambdaUtil.getPropertyName(property), javaFormat);
    }

    /**
     * 对时间格式的列进行格式化
     *
     * @param property 时间格式列
     * @param format 数据库格式化
     * @return 时间格式函数
     */
    default SQLFunction dateTimeSQLFormat(KProperty1<? super T, ?> property, String format) {
        return dateTimeSQLFormat(null, property, format);
    }

    /**
     * 对时间格式的列进行格式化
     *
     * @param tableOwner 属性对应的表
     * @param property 时间格式列
     * @param format 数据库格式化
     * @return 时间格式函数
     */
    default SQLFunction dateTimeSQLFormat(EntitySQLTableOwner<T> tableOwner, KProperty1<? super T, ?> property, String format) {
        return getSQLFunc().dateTimeSQLFormat(tableOwner, EasyKtLambdaUtil.getPropertyName(property), format);
    }

    /**
     * 连接函数将多个列合并在一起
     * @param property1 属性列1
     * @param property2 属性列2
     * @return 链接函数
     */
    default SQLFunction concat(KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2) {
        return concat(s -> {
            SQLKtColumnFuncSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    /**
     * 连接函数将多个列合并在一起
     *
     * @param property1 属性列1
     * @param property2 属性列2
     * @param property3 属性列3
     * @return 链接函数
     */
    default SQLFunction concat(KProperty1<? super T, ?> property1, KProperty1<? super T, ?> property2, KProperty1<? super T, ?> property3) {
        return concat(s -> {
            SQLKtColumnFuncSelector<T> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    /**
     * 链接函数表达式 将多个列合并在一起
     *
     * @param sqlExpression 指定多个属性列
     * @return 链接函数
     */
    default SQLFunction concat(SQLExpression1<SQLKtColumnFuncSelector<T>> sqlExpression) {
        List<ColumnExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLKtColumnConcatSelectorImpl<>(new ColumnFuncSelectorImpl(concatExpressions)));
        return concat(concatExpressions);
    }

    /**
     * 链接函数 将多个列合并在一起
     *
     * @param concatExpressions 链接列或者常量等表达式
     * @return 链接函数
     */
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

    /**
     * 当前时间函数
     *
     * @return 时间函数
     */
    default SQLFunction now() {
        return getSQLFunc().now();
    }

    /**
     * 当前UTC时间函数
     *
     * @return UTC时间函数
     */
    default SQLFunction utcNow() {
        return getSQLFunc().utcNow();
    }
}
