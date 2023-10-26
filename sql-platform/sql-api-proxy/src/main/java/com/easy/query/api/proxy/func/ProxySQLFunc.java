package com.easy.query.api.proxy.func;

import com.easy.query.api.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.api.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelectorImpl;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/12 17:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySQLFunc  extends ProxyAggregateSQLFunc{

    /**
     * 如果{@code sqlColumn}对应的值为null则返回def值
     *
     * @param sqlColumn 属性列
     * @param def 默认值
     * @return ifNull函数
     * @param <TProxy> 数据库对象代理对象
     * @param <T> 数据库对象
     */
    default <TProxy, T> SQLFunction nullDefault(SQLColumn<TProxy, T> sqlColumn, Object def) {
        return nullDefault(s -> {
            s.column(sqlColumn)
                    .value(def);
        });
    }

    /**
     * 如果列对应的值为null则返回def值
     * @param sqlExpression 列选择表达式
     * @return ifNull函数
     */
    default SQLFunction nullDefault(SQLExpression1<ProxyColumnFuncSelector> sqlExpression) {
        return getSQLFunc().nullDefault(o->{
            sqlExpression.apply(new ProxyColumnFuncSelectorImpl(o));
        });
    }

    /**
     * 获取绝对值
     *
     * @param sqlColumn 代理对象属性
     * @return 绝对值函数
     */
    default <TProxy, T> SQLFunction abs(SQLColumn<TProxy, T> sqlColumn) {
        return getSQLFunc().abs(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value());
    }

    /**
     * 获取四舍五入
     *
     * @param sqlColumn 属性列
     * @param scale 保留几位小数
     * @return 四舍五入函数
     * @param <TProxy> 代理对象
     * @param <T> 数据库对象
     */
    default <TProxy, T> SQLFunction round(SQLColumn<TProxy, T> sqlColumn, int scale) {
        return getSQLFunc().round(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), scale);
    }

    /**
     * 对时间格式的列进行格式化 默认 yyyy-MM-dd HH:mm:ss.fff
     *
     * @param sqlColumn 属性列
     * @return 时间格式函数
     * @param <TProxy> 代理对象
     * @param <T> 数据库对象
     */
    default <TProxy, T> SQLFunction dateTimeFormat(SQLColumn<TProxy, T> sqlColumn) {
        return getSQLFunc().dateTimeFormat(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), null);
    }

    /**
     * 对时间格式的列进行格式化 默认 yyyy-MM-dd HH:mm:ss.fff
     * {@code javaFormat} yyyy-MM-dd HH:mm:ss | yyyy/MM/dd HH:mm:ss | yyyy-MM-dd ...
     *
     * @param sqlColumn 属性列
     * @param javaFormat java格式化
     * @return 时间格式函数
     * @param <TProxy> 代理对象
     * @param <T> 数据库对象
     */
    default <TProxy, T> SQLFunction dateTimeFormat(SQLColumn<TProxy, T> sqlColumn, String javaFormat) {
        return getSQLFunc().dateTimeFormat(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), javaFormat);
    }

    /**
     * 对时间格式的列进行格式化
     *
     * @param sqlColumn 属性列
     * @param format 数据库格式化
     * @return 时间格式函数
     * @param <TProxy> 代理对象
     * @param <T> 数据库对象
     */
    default <TProxy, T> SQLFunction dateTimeSQLFormat(SQLColumn<TProxy, T> sqlColumn, String format) {
        return getSQLFunc().dateTimeSQLFormat(new SimpleSQLTableOwner(sqlColumn.getTable()), sqlColumn.value(), format);
    }

    /**
     * 连接函数将多个列合并在一起
     *
     * @param sqlColumn1 属性列1
     * @param sqlColumn2 属性列2
     * @param sqlColumns 属性列集合
     * @return 链接函数
     */
    default SQLFunction concat(SQLColumn<?, ?> sqlColumn1, SQLColumn<?, ?> sqlColumn2, SQLColumn<?, ?>... sqlColumns) {
        return concat(s -> {
            s.column(sqlColumn1)
                    .column(sqlColumn2);
            if (EasyArrayUtil.isNotEmpty(sqlColumns)) {
                for (SQLColumn<?, ?> sqlColumn : sqlColumns) {
                    s.column(sqlColumn);
                }
            }
        });
    }

    /**
     * 链接函数表达式 将多个列合并在一起
     *
     * @param sqlExpression 列常量选择表达式
     * @return 链接函数
     */
    default SQLFunction concat(SQLExpression1<ProxyColumnFuncSelector> sqlExpression) {
        List<ColumnExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new ProxyColumnFuncSelectorImpl(new ColumnFuncSelectorImpl(concatExpressions)));
        return concat(concatExpressions);
    }

    /**
     * 链接函数列常量表达式 将多个列合并在一起
     *
     * @param concatExpressions 常量表达式
     * @return 链接函数
     */
    default SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }
//
//    default SQLFunction join(String separator, SQLColumn<?, ?> sqlColumn1, SQLColumn<?, ?> sqlColumn2, SQLColumn<?, ?>... sqlColumns) {
//        return join(separator, s -> {
//            s.column(sqlColumn1)
//                    .column(sqlColumn2);
//            if (EasyArrayUtil.isNotEmpty(sqlColumns)) {
//                for (SQLColumn<?, ?> sqlColumn : sqlColumns) {
//                    s.column(sqlColumn);
//                }
//            }
//        });
//    }
//
//    default <T> SQLFunction join(String separator, SQLExpression1<ProxyColumnFuncSelector> sqlExpression) {
//        List<ColumnExpression> concatExpressions = new ArrayList<>();
//        sqlExpression.apply(new ProxyColumnFuncSelectorImpl(new ColumnFuncSelectorImpl(concatExpressions)));
//        return join(separator, concatExpressions);
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
