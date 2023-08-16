package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.AnonymousEntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.function.Function;

/**
 * create time 2023/4/23 16:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousEntityTableSQLExpressionImpl extends TableSQLExpressionImpl implements AnonymousEntityTableSQLExpression {
    private final EntityQuerySQLExpression easyQuerySQLExpression;

    public AnonymousEntityTableSQLExpressionImpl(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQuerySQLExpression easyQuerySQLExpression, QueryRuntimeContext runtimeContext) {
        super(entityTable, multiTableType, runtimeContext);
        this.easyQuerySQLExpression = easyQuerySQLExpression;
    }

    @Override
    public void setTableNameAs(Function<String, String> tableNameAs) {
    }

    @Override
    public void setSchemaAs(Function<String, String> schemaAs) {
    }


    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);

        StringBuilder sql = new StringBuilder();

        sql.append(" ").append(getSelectTableSource()).append(" (").append(easyQuerySQLExpression.toSQL(toSQLContext)).append(")");
        String tableAlias = EasySQLExpressionUtil.getTableAlias(toSQLContext, entityTable);
        if (tableAlias != null) {
            sql.append(" ").append(tableAlias);
        }
        return sql.toString();
    }

    @Override
    public EntityQuerySQLExpression getEntityQuerySQLExpression() {
        return easyQuerySQLExpression;
    }

    @Override
    public EntityTableSQLExpression cloneSQLExpression() {

        EntityTableSQLExpression tableSQLExpression = runtimeContext.getExpressionFactory().createAnonymousEntityTableSQLExpression(entityTable, multiTableType, easyQuerySQLExpression.cloneSQLExpression(), runtimeContext);
        if (EasySQLSegmentUtil.isNotEmpty(on)) {
            PredicateSegment predicateSegment = on.clonePredicateSegment();
            tableSQLExpression.setOn(predicateSegment);
        }
        tableSQLExpression.setLinkAs(linkAs);
        return tableSQLExpression;
    }
}
