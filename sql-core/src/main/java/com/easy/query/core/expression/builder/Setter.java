package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.builder.core.SQLSetNative;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/12/7 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Setter extends SQLSetNative<Setter>, RuntimeContextAvailable {
    SQLBuilderSegment getSQLBuilderSegment();
    /**
     * set column=val
     */
    default Setter set(TableAvailable table, String property, Object val) {
        return set(true,table, property, val);
    }

    /**
     * set column=val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    Setter set(boolean condition,TableAvailable table, String property, Object val);

    default Setter setWithColumn(TableAvailable table,String property1, String property2) {
        return setWithColumn(true,table, property1, property2);
    }

    Setter setWithColumn(boolean condition,TableAvailable table, String property1, String property2);

    default Setter setIncrement(TableAvailable table,String property) {
        return setIncrement(true,table, property);
    }

    default Setter setIncrement(boolean condition,TableAvailable table, String property) {
        return setIncrementNumber(condition,table, property, 1);
    }

    default Setter setIncrement(TableAvailable table,String property, int val) {
        return setIncrementNumber(true,table, property, val);
    }

    default Setter setIncrement(boolean condition,TableAvailable table, String property, int val) {
        return setIncrementNumber(condition,table, property, val);
    }

    default Setter setIncrement(TableAvailable table,String property, long val) {
        return setIncrementNumber(true,table, property, val);
    }

    default Setter setIncrement(boolean condition,TableAvailable table, String property, long val) {
        return setIncrementNumber(condition,table, property, val);
    }


    default Setter setIncrement(TableAvailable table,String property, Number val) {
        return setIncrementNumber(true,table, property, val);
    }

    Setter setIncrementNumber(boolean condition,TableAvailable table, String property, Number val);

    default Setter setDecrement(TableAvailable table,String property) {
        return setDecrement(true,table, property);
    }

    default Setter setDecrement(boolean condition,TableAvailable table, String property) {
        return setDecrementNumber(condition,table, property, 1);
    }

    default Setter setDecrement(TableAvailable table,String property, int val) {
        return setDecrementNumber(true,table, property, val);
    }

    default Setter setDecrement(boolean condition,TableAvailable table, String property, int val) {
        return setDecrementNumber(condition,table, property, val);
    }

    default Setter setDecrement(TableAvailable table,String property, long val) {
        return setDecrementNumber(true,table, property, val);
    }

    default Setter setDecrement(boolean condition,TableAvailable table, String property, long val) {
        return setDecrementNumber(condition,table, property, val);
    }


    default Setter setDecrement(TableAvailable table,String property, Number val) {
        return setDecrementNumber(true,table, property, val);
    }

    Setter setDecrementNumber(boolean condition,TableAvailable table, String property, Number val);
    Setter setFunc(TableAvailable table, String property, SQLFunction sqlFunction);
}
