package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/7 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateColumnSetSegmentImpl extends AbstractInsertUpdateSetColumnSQLSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    protected final Object val;

    public UpdateColumnSetSegmentImpl(TableAvailable table, String propertyName, Object val, QueryRuntimeContext runtimeContext){
        super(table,propertyName,runtimeContext);
        this.val = val;
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSetSegmentImpl(table,propertyName,val,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasyConstSQLParameter sqlParameter = new EasyConstSQLParameter(table, propertyName, val);
        return toSQLWithParameter(toSQLContext,sqlParameter);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, table, columnMetadata, toSQLContext,true,false);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
