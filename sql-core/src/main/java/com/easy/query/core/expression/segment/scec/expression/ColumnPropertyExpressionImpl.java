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
public final class ColumnPropertyExpressionImpl implements ColumnPropertyParamExpression {
    private final TableAvailable table;
    private final String property;
    public ColumnPropertyExpressionImpl(TableAvailable table, String property) {
        this.table = table;
        this.property = property;
    }

    @Override
    public String toSQL(QueryRuntimeContext runtimeContext,ToSQLContext toSQLContext) {
        return  EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,property,toSQLContext);
    }
}
