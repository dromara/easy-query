package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlEntityAliasSegment;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @author xuejiaming
 */
public class AbstractSqlColumnSelector<T1, TChain> implements ColumnSelector<T1, TChain> {
    private final int index;
    protected final EntityExpressionBuilder sqlEntityExpression;
    protected final SqlBuilderSegment sqlSegmentBuilder;

    public AbstractSqlColumnSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        this.index = index;

        this.sqlEntityExpression = sqlEntityExpression;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }

    @Override
    public int getIndex() {
        return this.index;
    }


    @Override
    public TChain column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnSegmentImpl(table, propertyName, sqlEntityExpression));
        return (TChain) this;
    }


    @Override
    public TChain columnIgnore(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.getSqlSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SqlEntitySegment) {
                SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), propertyName);
            }
            return false;
        });
        return (TChain) this;
    }

    @Override
    public TChain columnAll() {
        EntityTableExpressionBuilder table = sqlEntityExpression.getTable(index);
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            Collection<String> properties = table.getEntityMetadata().getProperties();
            for (String property : properties) {
                sqlSegmentBuilder.append(new ColumnSegmentImpl(table, property, sqlEntityExpression));
            }
        }
        return (TChain) this;
    }

    protected TChain columnAnonymousAll(AnonymousEntityTableExpressionBuilder table){
        EntityQueryExpressionBuilder sqlEntityQueryExpression = table.getEntityQueryExpressionBuilder();
        List<SqlSegment> sqlSegments = sqlEntityQueryExpression.getProjects().getSqlSegments();
        for (SqlSegment sqlSegment : sqlSegments) {
            if (sqlSegment instanceof SqlEntityAliasSegment) {
//                String propertyName = EasyUtil.getAnonymousPropertyName((SqlEntityAliasSegment) sqlSegment);
                sqlSegmentBuilder.append(new ColumnSegmentImpl(table,((SqlEntityAliasSegment) sqlSegment).getPropertyName() , sqlEntityExpression));
            } else {
                throw new EasyQueryException("columnAll函数无法获取指定列" + ClassUtil.getInstanceSimpleName(sqlSegment));
            }
        }
        return (TChain) this;
    }


    public EntityExpressionBuilder getSqlEntityExpression() {
        return sqlEntityExpression;
    }
}
