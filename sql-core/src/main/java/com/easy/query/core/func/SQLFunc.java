package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2023/10/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunc extends AggregateSQLFunc,SQLStringFunc{
    /**
     * 如果property对应的值为null则返回def值
     *
     * @param property 属性列
     * @param def 默认值
     * @return ifNull函数
     */
    default SQLFunction valueOrDefault(String property, Object def) {
        return valueOrDefault(s->{
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
   SQLFunction valueOrDefault(SQLExpression1<ColumnFuncSelector> sqlExpression);

    /**
     * 获取绝对值
     *
     * @param property 属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(String property) {
        return abs(o->o.column(property));
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner 属性所属表
     * @param property 属性列
     * @return 绝对值函数
     */
   default SQLFunction abs(SQLTableOwner tableOwner, String property){
       return abs(o->o.column(tableOwner,property));
   }
    SQLFunction abs(SQLExpression1<ColumnFuncSelector> sqlExpression);

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
