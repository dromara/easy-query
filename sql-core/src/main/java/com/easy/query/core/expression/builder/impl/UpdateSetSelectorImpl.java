package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.UpdateSetSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/6/25 17:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateSetSelectorImpl implements UpdateSetSelector {

    private final QueryRuntimeContext runtimeContext;
    private final SQLBuilderSegment sqlSegmentBuilder;

    public UpdateSetSelectorImpl(QueryRuntimeContext runtimeContext, SQLBuilderSegment sqlSegmentBuilder){

        this.runtimeContext = runtimeContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }
    @Override
    public UpdateSetSelector column(TableAvailable table, String property) {
        sqlSegmentBuilder.append(new ColumnPropertyPredicate(table, property, runtimeContext));
        return this;
    }

    @Override
    public UpdateSetSelector columnAll(TableAvailable table) {
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnPropertyPredicate(table, property, runtimeContext));
        }
        return this;
    }

    @Override
    public UpdateSetSelector columnIgnore(TableAvailable table, String property) {
        sqlSegmentBuilder.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), property);
            }
            return false;
        });
        return this;
    }
}
