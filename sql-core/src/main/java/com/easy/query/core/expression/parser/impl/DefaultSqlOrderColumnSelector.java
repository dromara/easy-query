package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.expression.segment.OrderColumnSegment;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlEntityTableExpression;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @author xuejiaming
 */
public  class DefaultSqlOrderColumnSelector<T1> implements SqlColumnSelector<T1> {
    private final int index;
    private final SqlEntityQueryExpression sqlEntityExpression;
    private boolean asc;

    public DefaultSqlOrderColumnSelector(int index, SqlEntityQueryExpression sqlEntityExpression){
        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
    }
    @Override
    public  int getIndex(){
        return this.index;
    }

    @Override
    public SqlColumnSelector<T1> column(Property<T1, ?> column) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlEntityExpression.getOrder().append(new OrderColumnSegment(table,propertyName, sqlEntityExpression,asc));
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnIgnore(Property<T1, ?> column) {

        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlEntityExpression.getOrder().getSqlSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SqlEntitySegment) {
                SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), propertyName);
            }
            return false;
        });
        return this;
    }

    @Override
    public SqlColumnSelector<T1> columnAll() {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlEntityExpression.getOrder().append(new OrderColumnSegment(table, property, sqlEntityExpression,asc));
        }
        return this;
    }


    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
