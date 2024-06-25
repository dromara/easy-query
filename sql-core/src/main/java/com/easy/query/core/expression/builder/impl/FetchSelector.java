package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;

/**
 * create time 2024/2/18 21:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class FetchSelector implements Selector {
    private final Collection<String> properties;

    public FetchSelector(Collection<String> properties){

        this.properties = properties;
    }
    @Override
    public Selector groupKeys(int index) {
       throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnKeys(TableAvailable table) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector column(TableAvailable table, String property) {
        properties.add(property);
        return this;
    }

    @Override
    public Selector columnAs(TableAvailable table, String property, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnFunc(TableAvailable table, SQLFunction sqlFunction, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnIgnore(TableAvailable table, String property) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnIfAbsent(TableAvailable table, String property) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnAll(TableAvailable table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        Collection<ColumnMetadata> columns = entityMetadata.getColumns();
        for (ColumnMetadata columnMetadata : columns) {
            appendColumnMetadata(table, columnMetadata, true, true, null);
        }
        return this;
    }
    protected void appendColumnMetadata(TableAvailable table, ColumnMetadata columnMetadata, boolean checkAutoSelect, boolean ignoreValueObject, String alias) {

        if (columnMetadata.isValueObject()) {
            if (!ignoreValueObject) {
                for (ColumnMetadata metadata : columnMetadata.getValueObjectColumnMetadataList()) {
                    appendColumnMetadata(table, metadata, checkAutoSelect, false, alias);
                }
            }
            return;
        }
        if (checkAutoSelect && !columnMetadata.isAutoSelect()) {
            return;
        }
        properties.add(columnMetadata.getPropertyName());
    }

    @Override
    public Selector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        throw new UnsupportedOperationException();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        throw new UnsupportedOperationException();
    }
}
