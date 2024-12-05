package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class UpdateColumnSegmentImpl extends AbstractInsertUpdateSetColumnSQLSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    public UpdateColumnSegmentImpl(Column2Segment column2Segment, ColumnValue2Segment columnValue2Segment){
        super(column2Segment,columnValue2Segment);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return columnValue2Segment.toSQL(toSQLContext);
    }

    @Override
    public TableAvailable getTable() {
        return column2Segment.getTable();
    }

    public String getPropertyName() {
        return column2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return column2Segment.toSQL(toSQLContext);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSegmentImpl(column2Segment,columnValue2Segment);
    }
}
