package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 * @author xuejiaming
 */
public class ColumnWithSelfNativeSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    private static final String INCREMENT = " + ?";
    private static final String DECREMENT = " - ?";
    private final boolean increment;
    private final SQLNativeSegment sqlNativeSegment;
    private final Object val;
    private final ExpressionContext expressionContext;
    private final TableAvailable entityTable;
    private final ColumnMetadata columnMetadata;

    public ColumnWithSelfNativeSegmentImpl(boolean increment, TableAvailable entityTable, ColumnMetadata columnMetadata, SQLNativeSegment sqlNativeSegment, Object val, ExpressionContext expressionContext) {
        this.increment = increment;
        this.entityTable = entityTable;
        this.columnMetadata = columnMetadata;
        this.sqlNativeSegment = sqlNativeSegment;
        this.val = val;
        this.expressionContext = expressionContext;
    }

    private String getOperator() {
        return increment ? INCREMENT : DECREMENT;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext, new EasyConstSQLParameter(entityTable, columnMetadata.getPropertyName(), val));
//        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, entityTable, columnMetadata, toSQLContext,true,false);
        return sqlNativeSegment.toSQL(toSQLContext) + getOperator();
    }

    @Override
    public TableAvailable getTable() {
        return entityTable;
    }

    @Override
    public String getPropertyName() {
        return columnMetadata.getPropertyName();
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return sqlNativeSegment.toSQL(toSQLContext);
//        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, entityTable, columnMetadata, toSQLContext,true,false);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext, entityTable, propertyName, toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new ColumnWithSelfNativeSegmentImpl(increment, entityTable, columnMetadata,sqlNativeSegment, val, expressionContext);
    }
}
