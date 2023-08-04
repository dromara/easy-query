package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.sql.expression.EntityInsertSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/22 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertSQLExpressionImpl implements EntityInsertSQLExpression {

    protected final SQLBuilderSegment columns;
    protected final List<EntityTableSQLExpression> tables = new ArrayList<>(1);
    protected final EntitySQLExpressionMetadata entitySQLExpressionMetadata;

    protected String duplicateKey;
    protected SQLBuilderSegment duplicateKeyUpdateColumns;

    public InsertSQLExpressionImpl(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        this.entitySQLExpressionMetadata = entitySQLExpressionMetadata;
        this.tables.add(table);
        columns = new ProjectSQLBuilderSegmentImpl();
    }

    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public SQLBuilderSegment getColumns() {
        return columns;
    }

    @Override
    public SQLBuilderSegment getDuplicateKeyUpdateColumns() {
        if (duplicateKeyUpdateColumns == null) {
            duplicateKeyUpdateColumns = new ProjectSQLBuilderSegmentImpl();
        }
        return duplicateKeyUpdateColumns;
    }

    @Override
    public String getDuplicateKey() {
        return duplicateKey;
    }

    @Override
    public void setDuplicateKey(String duplicateKey) {
        this.duplicateKey = duplicateKey;
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
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (");
        Iterator<SQLSegment> iterator = columns.getSQLSegments().iterator();
        SQLSegment firstColumn = iterator.next();

        sql.append(getInsertColumn(firstColumn,toSQLContext));
        while(iterator.hasNext()){
            SQLSegment next = iterator.next();
            sql.append(",").append(getInsertColumn(next,toSQLContext));
        }

        sql.append(") VALUES (").append(columns.toSQL(toSQLContext)).append(")");
        return sql.toString();
    }

    protected String getInsertColumn(SQLSegment sqlSegment,ToSQLContext toSQLContext){
        if(sqlSegment instanceof ColumnInsertSegment){
            ColumnInsertSegment columnInsertSegment = (ColumnInsertSegment) sqlSegment;
            return columnInsertSegment.toInsertColumn(toSQLContext);
        }
        throw new EasyQueryInvalidOperationException("insert column error:"+ EasyClassUtil.getInstanceSimpleName(sqlSegment));
    }

    @Override
    public EntityInsertSQLExpression cloneSQLExpression() {

        ExpressionFactory expressionFactory = entitySQLExpressionMetadata.getRuntimeContext().getExpressionFactory();

        EntityInsertSQLExpression easyInsertSQLExpression = expressionFactory.createEasyInsertSQLExpression(entitySQLExpressionMetadata, tables.get(0).cloneSQLExpression());
        if (EasySQLSegmentUtil.isNotEmpty(columns)) {
            columns.copyTo(easyInsertSQLExpression.getColumns());
        }
        if (EasySQLSegmentUtil.isNotEmpty(duplicateKeyUpdateColumns)) {
            duplicateKeyUpdateColumns.copyTo(easyInsertSQLExpression.getDuplicateKeyUpdateColumns());
        }
        easyInsertSQLExpression.setDuplicateKey(duplicateKey);
        return easyInsertSQLExpression;
    }


    protected SQLBuilderSegment getRealDuplicateKeyUpdateColumns() {
        if (EasySQLSegmentUtil.isNotEmpty(duplicateKeyUpdateColumns)) {
            return duplicateKeyUpdateColumns;
        }
        return columns;
    }

    protected Set<String> getColumnsSet(SQLBuilderSegment sqlBuilderSegment) {
        List<SQLSegment> sqlSegments = sqlBuilderSegment.getSQLSegments();
        if (EasyCollectionUtil.isEmpty(sqlSegments)) {
            return Collections.emptySet();
        }
        HashSet<String> set = new HashSet<>(sqlSegments.size());
        for (SQLSegment sqlSegment : sqlSegments) {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                set.add(sqlEntitySegment.getPropertyName());
            }
        }
        return set;
    }
}
