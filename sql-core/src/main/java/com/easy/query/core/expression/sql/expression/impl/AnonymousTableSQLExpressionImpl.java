package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.AnonymousTableSQLExpression;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.util.SQLExpressionUtil;

/**
 * create time 2023/4/23 16:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousTableSQLExpressionImpl extends TableSQLExpressionImpl implements AnonymousTableSQLExpression {
    private final QuerySQLExpression easyQuerySQLExpression;

    public AnonymousTableSQLExpressionImpl(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QuerySQLExpression easyQuerySQLExpression, EasyQueryRuntimeContext runtimeContext) {
        super(entityTable, multiTableType, runtimeContext);
        this.easyQuerySQLExpression = easyQuerySQLExpression;
    }


    @Override
    public String getTableName() {
        if (tableNameAs != null) {
            return tableNameAs.apply(getAlias());
        }
        return getAlias();
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        SQLExpressionUtil.expressionInvokeRoot(sqlParameterCollector);

        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append("(").append(easyQuerySQLExpression.toSQL(sqlParameterCollector)).append(")");
        String tableName = getTableName();
        if (tableName != null) {
            sql.append(" ").append(tableName);
        }
        return sql.toString();
    }

}
