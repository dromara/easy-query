package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/22 22:49
 * 文件说明
 *
 * @author xuejiaming
 */
public  class UpdateSQLExpressionImpl implements EntityUpdateSQLExpression {
    private static final Log log= LogFactory.getLog(UpdateSQLExpressionImpl.class);
    protected final SQLBuilderSegment setColumns;
    protected final PredicateSegment where;
    protected final List<EntityTableSQLExpression> tables=new ArrayList<>(1);
    private final EntitySQLExpressionMetadata entitySQLExpressionMetadata;

    public UpdateSQLExpressionImpl(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        this.entitySQLExpressionMetadata = entitySQLExpressionMetadata;
        this.tables.add(table);
        this.setColumns = new UpdateSetSQLBuilderSegment();
        this.where = new AndPredicateSegment(true);
    }

    @Override
    public SQLBuilderSegment getSetColumns() {
        return setColumns;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }


    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public EntitySQLExpressionMetadata getExpressionMetadata() {
        return entitySQLExpressionMetadata;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return entitySQLExpressionMetadata.getRuntimeContext();
    }


    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        if(EasySQLSegmentUtil.isEmpty(setColumns)){
            log.warn("'UPDATE' statement without 'SET',not generate sql execute");
            return null;
        }
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        return "UPDATE " + tableName + " SET " + setColumns.toSQL(toSQLContext) + " WHERE " +
                where.toSQL(toSQLContext);
    }

    @Override
    public EntityUpdateSQLExpression cloneSQLExpression() {

        ExpressionFactory expressionFactory = entitySQLExpressionMetadata.getRuntimeContext().getExpressionFactory();
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata,tables.get(0).cloneSQLExpression());
        if(EasySQLSegmentUtil.isNotEmpty(where)){
            where.copyTo(easyUpdateSQLExpression.getWhere());
        }if(EasySQLSegmentUtil.isNotEmpty(setColumns)){
            setColumns.copyTo(easyUpdateSQLExpression.getSetColumns());
        }
        return easyUpdateSQLExpression;
    }
}
