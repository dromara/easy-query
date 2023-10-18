package com.easy.query.core.expression.func;

/**
 * create time 2023/5/17 12:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public interface ColumnFunctionFactory {
    ColumnFunction createCountFunction(boolean distinct);
    ColumnFunction createSumFunction(boolean distinct);
    ColumnFunction createMaxFunction();
    ColumnFunction createMinFunction();
    ColumnFunction createAvgFunction(boolean distinct);
    ColumnFunction createLenFunction();
}
