package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.sql.AnonymousEntityTableExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @author xuejiaming
 */
public class DefaultAutoSqlColumnAsSelector<T1, TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1, TR> {

    private final Class<TR> resultClass;

    public DefaultAutoSqlColumnAsSelector(int index, EntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, sqlEntityExpression, sqlSegment0Builder);
        this.resultClass = resultClass;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAll() {

        EntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        if (table.getEntityClass().equals(resultClass)) {
            return super.columnAll();
        } else {
            return columnAll(table);
        }
    }

    private SqlColumnAsSelector<T1, TR> columnAll(EntityTableExpression table) {
        if (table instanceof AnonymousEntityTableExpression) {
            columnAnonymousAll((AnonymousEntityTableExpression) table);
        } else {
            //只查询当前对象返回结果属性名称匹配
            EntityMetadata targetEntityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
            EntityMetadata sourceEntityMetadata = table.getEntityMetadata();

            Collection<String> sourceProperties = sourceEntityMetadata.getProperties();
            for (String property : sourceProperties) {

                ColumnMetadata sourceColumnMetadata = sourceEntityMetadata.getColumnNotNull(property);
                String sourceColumnName = sourceColumnMetadata.getName();
                String targetPropertyName = targetEntityMetadata.getPropertyNameOrDefault(sourceColumnName, null);
                if (targetPropertyName != null) {

                    ColumnMetadata targetColumnMetadata = targetEntityMetadata.getColumnNotNull(targetPropertyName);
                    if (targetColumnMetadata != null) {
                        String targetColumnName = targetColumnMetadata.getName();
                        //如果当前属性和查询对象属性一致那么就返回对应的列名，对应的列名如果不一样就返回对应返回结果对象的属性上的列名
                        sqlSegmentBuilder.append(new ColumnSegment(table, property, sqlEntityExpression, Objects.equals(sourceColumnName,targetColumnName)?null: targetColumnName));
                    }
                }

            }
        }
        return this;
    }
    @Override
    public <T2, TChain2> ColumnAsSelector<T2, TR, TChain2> then(ColumnAsSelector<T2, TR, TChain2> sub) {
        throw new UnsupportedOperationException();
    }
    @Override
    public SqlColumnAsSelector<T1, TR> columnFunc(Property<T1, ?> column, Property<TR, ?> alias, EasyFunc easyFunc) {
        throw new UnsupportedOperationException();
    }

}
