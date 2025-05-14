package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.PartitionBySQLFunction;

/**
 * create time 2024/8/5 13:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLPartitionByFunc {
    PartitionBySQLFunction rowNumberOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction rankNumberOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction denseRankNumberOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction countOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction sumOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction avgOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction maxOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    PartitionBySQLFunction minOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression);
}
