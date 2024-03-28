package com.easy.query.core.expression.parser.core.base.scec.core;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;

/**
 * create time 2023/10/12 13:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeChainExpressionContextImpl implements SQLNativeChainExpressionContext {
    private final TableAvailable table;
    private final SQLNativeExpressionContext sqlNativeExpressionContext;

    public SQLNativeChainExpressionContextImpl(TableAvailable table,SQLNativeExpressionContext sqlNativeExpressionContext){
        this.table = table;

        this.sqlNativeExpressionContext = sqlNativeExpressionContext;
    }

    @Override
    public SQLNativeExpressionContext getSQLNativeExpressionContext() {
        return sqlNativeExpressionContext;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return sqlNativeExpressionContext.getExpressionContext();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return sqlNativeExpressionContext.getRuntimeContext();
    }

    @Override
    public TableAvailable getDefaultTable() {
        return table;
    }

    @Override
    public void expression(String property) {
        sqlNativeExpressionContext.expression(this.table,property);
    }
    @Override
    public void expression(TableAvailable table, String property) {
        sqlNativeExpressionContext.expression(table,property);
    }

    @Override
    public <TEntity> void expression(Query<TEntity> subQuery) {
        sqlNativeExpressionContext.expression(subQuery);
    }

    @Override
    public void columnName(String columnName) {
        sqlNativeExpressionContext.columnName(this.table,columnName);
    }

    @Override
    public void columnName(TableAvailable table, String columnName) {
        sqlNativeExpressionContext.columnName(table,columnName);
    }

    @Override
    public void value(Object val) {
        sqlNativeExpressionContext.value(val);
    }

    @Override
    public <T> void collection(Collection<T> values) {
        sqlNativeExpressionContext.collection(values);
    }

    @Override
    public void sql(SQLSegment sqlSegment) {
        sqlNativeExpressionContext.sql(sqlSegment);
    }

    @Override
    public void format(Object formatVal) {
        sqlNativeExpressionContext.format(formatVal);
    }

    @Override
    public void setAlias(String alias) {
        sqlNativeExpressionContext.setAlias(alias);
    }

    @Override
    public void keepStyle() {
        sqlNativeExpressionContext.keepStyle();
    }

    @Override
    public void messageFormat() {
        sqlNativeExpressionContext.messageFormat();
    }

    @Override
    public void expressionAlias(String property) {
        sqlNativeExpressionContext.expressionAlias(property);
    }

    @Override
    public void setPropertyAlias(String property) {
        sqlNativeExpressionContext.setPropertyAlias(property);
    }

    @Override
    public void setResultEntityMetadata(EntityMetadata entityMetadata) {
        sqlNativeExpressionContext.setResultEntityMetadata(entityMetadata);
    }
}
