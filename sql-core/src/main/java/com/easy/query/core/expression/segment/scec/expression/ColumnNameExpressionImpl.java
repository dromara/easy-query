package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/7/29 19:02
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ColumnNameExpressionImpl implements ColumnPropertyParamExpression {
    private final TableAvailable table;
    private final String columnName;

    public ColumnNameExpressionImpl(TableAvailable table, String columnName) {
        this.table = table;
        this.columnName = columnName;
    }

    @Override
    public String toSQL(QueryRuntimeContext runtimeContext, ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext, table, columnName, toSQLContext);
    }
}
