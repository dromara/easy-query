package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @author xuejiaming
 */
public class AbstractSqlColumnSelector<T1, TChain> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final TableAvailable table;
    protected final SqlBuilderSegment sqlSegmentBuilder;

    public AbstractSqlColumnSelector(int index, EntityExpressionBuilder entityExpressionBuilder, SqlBuilderSegment sqlSegmentBuilder) {
        this.index = index;

        this.entityExpressionBuilder = entityExpressionBuilder;
        this.table=entityExpressionBuilder.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }

    public TChain column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnSegmentImpl(table.getEntityTable(), propertyName, entityExpressionBuilder.getRuntimeContext()));
        return (TChain) this;
    }


    public TChain columnIgnore(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        String propertyName = LambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.getSqlSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SqlEntitySegment) {
                SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table.getEntityTable()) && Objects.equals(sqlEntitySegment.getPropertyName(), propertyName);
            }
            return false;
        });
        return (TChain) this;
    }

    public TChain columnAll() {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            Collection<String> properties = table.getEntityMetadata().getProperties();
            for (String property : properties) {
                sqlSegmentBuilder.append(new ColumnSegmentImpl(table.getEntityTable(), property, entityExpressionBuilder.getRuntimeContext()));
            }
        }
        return (TChain) this;
    }

    protected TChain columnAnonymousAll(AnonymousEntityTableExpressionBuilder table){
        EntityQueryExpressionBuilder sqlEntityQueryExpression = table.getEntityQueryExpressionBuilder();
        List<SqlSegment> sqlSegments = sqlEntityQueryExpression.getProjects().getSqlSegments();
        for (SqlSegment sqlSegment : sqlSegments) {
            if (sqlSegment instanceof SqlEntityAliasSegment) {
                SqlEntityAliasSegment sqlEntityAliasSegment = (SqlEntityAliasSegment) sqlSegment;
                String propertyName = EasyUtil.getAnonymousPropertyName(sqlEntityAliasSegment,table.getEntityTable());
                sqlSegmentBuilder.append(new ColumnSegmentImpl(table.getEntityTable(),propertyName , entityExpressionBuilder.getRuntimeContext(),sqlEntityAliasSegment.getAlias()));
            } else {
                throw new EasyQueryException("columnAll函数无法获取指定列" + ClassUtil.getInstanceSimpleName(sqlSegment));
            }
        }
        return (TChain) this;
    }


    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }
}
