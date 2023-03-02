package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.expression.segment.ColumnSegment;

import java.util.Collection;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @Created by xuejiaming
 */
public class AbstractSqlColumnSelector<T1,TChain> implements ColumnSelector<T1, TChain> {
    private final int index;
    private final SqlContext sqlContext;
    private final SqlBuilderSegment sqlSegmentBuilder;

    public AbstractSqlColumnSelector(int index, SqlContext sqlContext, SqlBuilderSegment sqlSegmentBuilder){
        this.index = index;

        this.sqlContext = sqlContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public  int getIndex(){
        return this.index;
    }

    @Override
    public TChain column(Property<T1, ?> column) {
        SqlTableInfo table = sqlContext.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnSegment(table,propertyName,sqlContext));
        return (TChain)this;
    }

    @Override
    public TChain columnAll() {
        SqlTableInfo table = sqlContext.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnSegment(table, property,sqlContext));
        }
        return (TChain)this;
    }


    public SqlContext getSqlContext() {
        return sqlContext;
    }
}
