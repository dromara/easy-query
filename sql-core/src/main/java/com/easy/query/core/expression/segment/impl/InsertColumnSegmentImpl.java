package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.extension.increment.IncrementSQLColumnGenerator;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/7 11:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertColumnSegmentImpl extends AbstractInsertUpdateSetColumnSQLSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    public InsertColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        super(table,propertyName,runtimeContext);
    }
    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
       return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new InsertColumnSegmentImpl(table,propertyName,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        IncrementSQLColumnGenerator incrementSQLColumnGenerator = columnMetadata.getIncrementSQLColumnGenerator();
        if(incrementSQLColumnGenerator!=null){
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
            incrementSQLColumnGenerator.configure(table,columnMetadata,sqlPropertyConverter,runtimeContext);
            return sqlPropertyConverter.toSQL(toSQLContext);
        }else{
            PropertySQLParameter sqlParameter = new PropertySQLParameter(table, propertyName);
            return toSQLWithParameter(toSQLContext,sqlParameter);
        }
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
