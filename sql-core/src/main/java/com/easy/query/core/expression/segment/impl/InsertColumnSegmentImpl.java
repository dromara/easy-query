package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/7 11:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertColumnSegmentImpl extends AbstractInsertUpdateSetColumnSQLSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    public InsertColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext){
        super(table,columnMetadata,runtimeContext);
    }
    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, table, columnMetadata, toSQLContext,true,false);
//       return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new InsertColumnSegmentImpl(table,columnMetadata,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        GeneratedKeySQLColumnGenerator generatedSQLColumnGenerator = columnMetadata.getGeneratedSQLColumnGenerator();
        if(generatedSQLColumnGenerator!=null){
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
            generatedSQLColumnGenerator.configure(table,columnMetadata,sqlPropertyConverter,runtimeContext);
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
