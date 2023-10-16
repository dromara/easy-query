package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelectorImpl;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunc {
    /**
     * 如果property对应的值为null则返回def值
     *
     * @param property 属性列
     * @param def 默认值
     * @return ifNull函数
     */
    default SQLFunction ifNull(String property, Object def) {
        return ifNull(s->{
            s.column(property)
                    .value(def);
        });
    }

    /**
     * 如果property对应的值为null则返回默认值
     *
     * @param sqlExpression 属性选择函数
     * @return ifNull函数
     */
   default SQLFunction ifNull(SQLExpression1<ColumnFuncSelector> sqlExpression){
       List<ColumnExpression> columnExpressions = new ArrayList<>();
       sqlExpression.apply(new ColumnFuncSelectorImpl(columnExpressions));
       return ifNull(columnExpressions);
   }

    /**
     * 如果属性常量表达式集合对应的值为null则返回默认值
     *
     * @param columnExpressions 常量表达式集合
     * @return ifNull函数
     */
    SQLFunction ifNull(List<ColumnExpression> columnExpressions);

    /**
     * 获取绝对值
     *
     * @param property 属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(String property) {
        return abs(null, property);
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner 属性所属表
     * @param property 属性列
     * @return 绝对值函数
     */
    SQLFunction abs(SQLTableOwner tableOwner, String property);

    /**
     * 按照指定的小数位数进行四舍五入运算
     *
     * @param property 属性列
     * @param scale 保留小数位数
     * @return 四舍五入函数
     */
    default SQLFunction round(String property, int scale) {
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
    SQLFunction round(SQLTableOwner tableOwner, String property, int scale);

    /**
     * 对时间格式的列进行格式化 默认 yyyy-MM-dd HH:mm:ss.fff
     *
     * @param property 时间格式列
     * @return 时间格式函数
     */

    default SQLFunction dateTimeFormat(String property) {
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
    default SQLFunction dateTimeFormat(String property, String javaFormat) {
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
    SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat);

    /**
     * 对时间格式的列进行格式化
     *
     * @param property 时间格式列
     * @param format 数据库格式化
     * @return 时间格式函数
     */
    default SQLFunction dateTimeSQLFormat(String property, String format) {
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
    SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format);

    /**
     * 连接函数将多个列合并在一起
     *
     * @param property1 属性列1
     * @param property2 属性列2
     * @param properties 其他属性列
     * @return 链接函数
     */
    default SQLFunction concat(String property1, String property2, String... properties) {
        return concat(s -> {
            s.column(property1)
                    .column(property2);
            if (EasyArrayUtil.isNotEmpty(properties)) {
                for (String property : properties) {
                    s.column(property);
                }
            }
        });
    }

    /**
     * 链接函数表达式 将多个列合并在一起
     *
     * @param sqlExpression 指定多个属性列
     * @return 链接函数
     */
    default SQLFunction concat(SQLExpression1<ColumnFuncSelector> sqlExpression) {
        List<ColumnExpression> concatExpressions = new ArrayList<>();
        sqlExpression.apply(new ColumnFuncSelectorImpl(concatExpressions));
        return concat(concatExpressions);
    }

    /**
     * 链接函数 将多个列合并在一起
     *
     * @param concatExpressions 链接列或者常量等表达式
     * @return 链接函数
     */
    SQLFunction concat(List<ColumnExpression> concatExpressions);

//    default SQLFunction join(String separator, String property1, String property2, String... properties) {
//        return join(separator, s -> {
//            s.column(property1)
//                    .column(property2);
//            if (EasyArrayUtil.isNotEmpty(properties)) {
//                for (String property : properties) {
//                    s.column(property);
//                }
//            }
//        });
//    }

//    default SQLFunction join(String separator, SQLExpression1<ColumnFuncSelector> sqlExpression) {
//        List<ColumnExpression> concatExpressions = new ArrayList<>();
//        sqlExpression.apply(new ColumnFuncSelectorImpl(concatExpressions));
//        return join(separator, concatExpressions);
//    }
//
//    SQLFunction join(String separator, List<ColumnExpression> concatExpressions);

    /**
     * 当前时间函数
     *
     * @return 时间函数
     */
    SQLFunction now();
    /**
     * 当前UTC时间函数
     *
     * @return UTC时间函数
     */
    SQLFunction utcNow();
}
