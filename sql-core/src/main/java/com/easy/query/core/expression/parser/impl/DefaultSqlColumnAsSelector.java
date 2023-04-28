package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @author xuejiaming
 */
public class DefaultSqlColumnAsSelector<T1, TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1, TR> {

    private final Class<TR> resultClass;
    private final EntityMetadata resultEntityMetadata;

    public DefaultSqlColumnAsSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, sqlEntityExpression, sqlSegment0Builder);
        this.resultClass = resultClass;
        this.resultEntityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String aliasColumnName = getResultColumnName(alias);
        sqlSegmentBuilder.append(new ColumnSegment(table, propertyName, sqlEntityExpression, aliasColumnName));
        return this;
    }

    protected String getResultColumnName(Property<TR, ?> alias){
        String aliasPropertyName = LambdaUtil.getPropertyName(alias);
        ColumnMetadata columnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
        return columnMetadata.getName();
    }


    @Override
    public SqlColumnAsSelector<T1, TR> columnAll() {

        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(getIndex());
        if(table.getEntityClass().equals(resultClass)){
            return  super.columnAll();
        }else{
            return  columnAll(table);
        }
    }
    private SqlColumnAsSelector<T1, TR> columnAll(EntityTableExpressionBuilder table){
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<String> properties = entityMetadata.getProperties();
            for (String property : properties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                String columnName = columnMetadata.getName();
                String aliasPropertyName = resultEntityMetadata.getPropertyNameOrDefault(columnName);
                if(aliasPropertyName!=null){
                    ColumnMetadata resultColumnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
                    String aliasColumnName = resultColumnMetadata.getName();
                    String alias = Objects.equals(columnName,aliasColumnName)?null:aliasColumnName;
                    sqlSegmentBuilder.append(new ColumnSegment(table, property, sqlEntityExpression,alias));
                }
            }
        }
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnFunc(Property<T1, ?> column, Property<TR, ?> alias, EasyFunc easyFunc) {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = alias == null ? table.getColumnName(propertyName) : getResultColumnName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegmentImpl(table, propertyName, sqlEntityExpression, easyFunc, columnAsName));
        return this;
    }

    @Override
    public <T2, TChain2> ColumnAsSelector<T2, TR, TChain2> then(ColumnAsSelector<T2, TR, TChain2> sub) {
        return sub;
    }

}
