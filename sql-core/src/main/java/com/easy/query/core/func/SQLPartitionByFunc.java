package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.PartitionBySQLFunction;

/**
 * create time 2024/8/5 13:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLPartitionByFunc {
    PartitionBySQLFunction rowNumberOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction rankNumberOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction denseRankNumberOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction countOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction sumOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction avgOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction maxOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction minOver(SQLExpression1<ColumnFuncSelector> sqlExpression);
}
