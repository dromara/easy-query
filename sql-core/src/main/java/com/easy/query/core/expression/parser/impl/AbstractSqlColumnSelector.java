package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.query.AnonymousEntityTableExpression;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.EasyUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 * @Created by xuejiaming
 */
public class AbstractSqlColumnSelector<T1, TChain> implements ColumnSelector<T1, TChain> {
    private final int index;
    protected final SqlEntityExpression sqlEntityExpression;
    protected final SqlBuilderSegment sqlSegmentBuilder;

    public AbstractSqlColumnSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
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
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        String propertyName = table.getPropertyName(column);
        sqlSegmentBuilder.append(new ColumnSegment(table, propertyName, sqlEntityExpression));
        return (TChain) this;
    }

    @Override
    public TChain columnIgnore(Property<T1, ?> column) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
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
        SqlEntityTableExpression table = sqlEntityExpression.getTable(index);
        if (table instanceof AnonymousEntityTableExpression) {
            SqlEntityQueryExpression sqlEntityQueryExpression = ((AnonymousEntityTableExpression) table).getSqlEntityQueryExpression();
            List<SqlSegment> sqlSegments = sqlEntityQueryExpression.getProjects().getSqlSegments();
            for (SqlSegment sqlSegment : sqlSegments) {
                if (sqlSegment instanceof SqlEntitySegment) {
                    String propertyName = EasyUtil.getAnonymousColumnName((SqlEntitySegment) sqlSegment);
                    sqlSegmentBuilder.append(new ColumnSegment(table, propertyName, sqlEntityExpression));
                } else {
                    throw new EasyQueryException("columnAll函数无法获取指定列" + ClassUtil.getInstanceSimpleName(sqlSegment));
                }
            }
        } else {

            Collection<String> properties = table.getEntityMetadata().getProperties();
            for (String property : properties) {
                sqlSegmentBuilder.append(new ColumnSegment(table, property, sqlEntityExpression));
            }
        }
        return (TChain) this;
    }


    public SqlEntityExpression getSqlEntityExpression() {
        return sqlEntityExpression;
    }
}
