package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.AnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @author xuejiaming
 */
public class DefaultSqlColumnAsSelector<T1, TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1, TR> {

    private final Class<TR> resultClass;

    public DefaultSqlColumnAsSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, sqlEntityExpression, sqlSegment0Builder);
        this.resultClass = resultClass;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = LambdaUtil.getPropertyName(alias);
        sqlSegmentBuilder.append(new ColumnSegment(table, propertyName, sqlEntityExpression, columnAsName));
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAll() {

        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        if(table.entityClass().equals(resultClass)){
            return  super.columnAll();
        }else{
            return  columnAll(table);
        }
    }
    private SqlColumnAsSelector<T1, TR> columnAll(SqlEntityTableExpression table){
        if (table instanceof AnonymousEntityTableExpression) {
            columnAnonymousAll((AnonymousEntityTableExpression) table);
        } else {
            EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
            Collection<String> properties = table.getEntityMetadata().getProperties();
            for (String property : properties) {
                ColumnMetadata columnOrNull = entityMetadata.getColumnOrNull(property);
                String alias = columnOrNull == null ? null :columnOrNull.getName();
                sqlSegmentBuilder.append(new ColumnSegment(table, property, sqlEntityExpression,alias));
            }
        }
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnFunc(Property<T1, ?> column, Property<TR, ?> alias, EasyFunc easyFunc) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = alias == null ? table.getColumnName(propertyName) : LambdaUtil.getPropertyName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegment(table, propertyName, sqlEntityExpression, easyFunc, columnAsName));
        return this;
    }

    @Override
    public <T2, TChain2> ColumnAsSelector<T2, TR, TChain2> then(ColumnAsSelector<T2, TR, TChain2> sub) {
        return sub;
    }

}
