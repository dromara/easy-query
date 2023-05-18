package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @author xuejiaming
 */
public class DefaultSQLColumnAsSelector<T1, TR> extends AbstractSQLColumnSelector<T1, SQLColumnAsSelector<T1, TR>> implements SQLColumnAsSelector<T1, TR> {

    protected final Class<TR> resultClass;
    protected final EntityMetadata resultEntityMetadata;

    public DefaultSQLColumnAsSelector(int index, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, entityExpressionBuilder, sqlSegment0Builder);
        this.resultClass = resultClass;
        this.resultEntityMetadata = entityExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        String aliasColumnName = getResultColumnName(alias);
        sqlSegmentBuilder.append(new ColumnSegmentImpl(table.getEntityTable(), propertyName, entityExpressionBuilder.getRuntimeContext(), aliasColumnName));
        return this;
    }

    protected String getResultColumnName(Property<TR, ?> alias){
        String aliasPropertyName = EasyLambdaUtil.getPropertyName(alias);
        ColumnMetadata columnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
        return columnMetadata.getName();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnAll() {

        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        if(table.getEntityClass().equals(resultClass)){
              super.columnAll();
            return this;
        }else{
            return  columnAll(table);
        }
    }
    private SQLColumnAsSelector<T1, TR> columnAll(EntityTableExpressionBuilder table){
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<String> properties = entityMetadata.getProperties();
            for (String property : properties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                String columnName = columnMetadata.getName();
                String aliasPropertyName = resultEntityMetadata.getPropertyNameOrNull(columnName);
                if(aliasPropertyName!=null){
                    ColumnMetadata resultColumnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
                    String aliasColumnName = resultColumnMetadata.getName();
                    String alias = Objects.equals(columnName,aliasColumnName)?null:aliasColumnName;
                    sqlSegmentBuilder.append(new ColumnSegmentImpl(table.getEntityTable(), property, entityExpressionBuilder.getRuntimeContext(),alias));
                }
            }
        }
        return this;
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnFunc(Property<T1, ?> column, Property<TR, ?> alias, ColumnFunction columnFunction) {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        String columnAsName = alias == null ? table.getColumnName(propertyName) : getResultColumnName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegmentImpl(table.getEntityTable(), propertyName, entityExpressionBuilder.getRuntimeContext(), columnFunction, columnAsName));
        return this;
    }

}
