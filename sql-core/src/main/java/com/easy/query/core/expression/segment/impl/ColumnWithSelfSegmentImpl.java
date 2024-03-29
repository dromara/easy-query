package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * @author xuejiaming
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 */
public class ColumnWithSelfSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    private static final String INCREMENT = "+ ?";
    private static final String DECREMENT = "- ?";
    private final boolean increment;
    private final String propertyName;
    private final Object val;
    private final QueryRuntimeContext runtimeContext;
    private final TableAvailable entityTable;

    public ColumnWithSelfSegmentImpl(boolean increment, TableAvailable entityTable, String propertyName, Object val, QueryRuntimeContext runtimeContext) {
        this.increment = increment;
        this.entityTable = entityTable;
        this.propertyName = propertyName;
        this.val = val;
        this.runtimeContext = runtimeContext;
    }

    private String getOperator() {
        return increment ? INCREMENT : DECREMENT;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext, new EasyConstSQLParameter(entityTable, propertyName, val));
        ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(propertyName);
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, entityTable, columnMetadata, toSQLContext,true,false);
        return sqlColumnSegment + getOperator();
    }

    @Override
    public TableAvailable getTable() {
        return entityTable;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(propertyName);
        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, entityTable, columnMetadata, toSQLContext,true,false);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext, entityTable, propertyName, toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new ColumnWithSelfSegmentImpl(increment, entityTable, propertyName, val, runtimeContext);
    }
}
