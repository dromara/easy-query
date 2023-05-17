package com.easy.query.core.expression.func;

/**
 * create time 2023/5/17 12:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultColumnFunctionFactory implements ColumnFunctionFactory{
    @Override
    public ColumnFunction createCountFunction(boolean distinct) {
        if(distinct){
            return DefaultColumnFunction.COUNT_DISTINCT;
        }
        return DefaultColumnFunction.COUNT;
    }

    @Override
    public ColumnFunction createSumFunction(boolean distinct) {
        if(distinct){
            return DefaultColumnFunction.SUM_DISTINCT;
        }
        return DefaultColumnFunction.SUM;
    }

    @Override
    public ColumnFunction createMaxFunction() {
        return DefaultColumnFunction.MAX;
    }

    @Override
    public ColumnFunction createMinFunction() {
        return DefaultColumnFunction.MIN;
    }

    @Override
    public ColumnFunction createAvgFunction(boolean distinct) {
        if(distinct){
            return DefaultColumnFunction.AVG_DISTINCT;
        }
        return DefaultColumnFunction.AVG;
    }

    @Override
    public ColumnFunction createLenFunction() {
        return DefaultColumnFunction.LEN;
    }
}
