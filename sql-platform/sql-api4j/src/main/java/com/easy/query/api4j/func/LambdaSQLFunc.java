package com.easy.query.api4j.func;

import com.easy.query.api4j.func.column.SQLColumnFuncSelectorImpl;
import com.easy.query.api4j.func.column.SQLColumnFuncSelector;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
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
public interface LambdaSQLFunc<T1> extends LambdaAggregateSQLFunc<T1> {



    /**
     * 如果property对应的值为null则返回def值
     *
     * @param property 属性列
     * @param def 默认值
     * @return ifNull函数
     */
    default SQLFunction nullOrDefault(Property<T1, ?> property, Object def) {
        return nullOrDefault(s -> {
            s.column(property)
                    .value(def);
        });
    }

    /**
     * 请使用 nullOrDefault
     * @param property
     * @param def
     * @return
     */
    @Deprecated
    default SQLFunction valueOrDefault(Property<T1, ?> property, Object def) {
        return nullOrDefault(s -> {
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
    default SQLFunction nullOrDefault(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        return getSQLFunc().nullOrDefault(o->{
            SQLColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }

    /**
     * 请使用 nullOrDefault
     * @param sqlExpression
     * @return
     */
    @Deprecated
    default SQLFunction valueOrDefault(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        return nullOrDefault(sqlExpression);
    }

    /**
     * 获取绝对值
     *
     * @param property 属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(Property<T1, ?> property) {
        return abs(null, property);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner 属性所属表
     * @param property 属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property) {
        return getSQLFunc().abs(tableOwner, EasyLambdaUtil.getPropertyName(property));
    }

    /**
     * 按照指定的小数位数进行四舍五入运算
     *
     * @param property 属性列
     * @param scale 保留小数位数
     * @return 四舍五入函数
     */
    default SQLFunction round(Property<T1, ?> property, int scale) {
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
    default SQLFunction round(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property, int scale) {
        return getSQLFunc().round(tableOwner, EasyLambdaUtil.getPropertyName(property), scale);
    }

    /**
     * 对时间格式的列进行格式化 默认 yyyy-MM-dd HH:mm:ss.fff
     *
     * @param property 时间格式列
     * @return 时间格式函数
     */
    default SQLFunction dateTimeFormat(Property<T1, ?> property) {
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
    default SQLFunction dateTimeFormat(Property<T1, ?> property, String javaFormat) {
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
    default SQLFunction dateTimeFormat(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property, String javaFormat) {
        return getSQLFunc().dateTimeFormat(tableOwner, EasyLambdaUtil.getPropertyName(property), javaFormat);
    }

    /**
     * 对时间格式的列进行格式化
     *
     * @param property 时间格式列
     * @param format 数据库格式化
     * @return 时间格式函数
     */
    default SQLFunction dateTimeSQLFormat(Property<T1, ?> property, String format) {
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
    default SQLFunction dateTimeSQLFormat(EntitySQLTableOwner<T1> tableOwner, Property<T1, ?> property, String format) {
        return getSQLFunc().dateTimeSQLFormat(tableOwner, EasyLambdaUtil.getPropertyName(property), format);
    }

    /**
     * 连接函数将多个列合并在一起
     * @param property1 属性列1
     * @param property2 属性列2
     * @return 链接函数
     */
    default SQLFunction concat(Property<T1, ?> property1, Property<T1, ?> property2) {
        return concat(s -> {
            SQLColumnFuncSelector<T1> s1 = EasyObjectUtil.typeCastNullable(s);
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
    default SQLFunction concat(Property<T1, ?> property1, Property<T1, ?> property2, Property<T1, ?> property3) {
        return this.<T1>concat(s -> {
            s.column(property1)
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
    default SQLFunction concat(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return concat(columnExpressions);
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
