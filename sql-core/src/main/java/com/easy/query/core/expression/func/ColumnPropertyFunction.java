package com.easy.query.core.expression.func;

/**
 * create time 2023/5/21 22:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnPropertyFunction {
    ColumnFunction getColumnFunction();
    String getPropertyName();
}
