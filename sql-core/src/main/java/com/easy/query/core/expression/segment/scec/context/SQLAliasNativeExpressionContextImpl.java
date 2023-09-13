package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyAsAliasParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.List;
import java.util.Objects;

/**
 * create time 2023/7/29 17:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAliasNativeExpressionContextImpl  implements SQLAliasNativeExpressionContext {

    private final SQLNativeExpressionContext sqlNativeExpressionContext;
    private final EntityMetadata resultEntityMetadata;

    public SQLAliasNativeExpressionContextImpl(SQLNativeExpressionContext sqlNativeExpressionContext, EntityMetadata resultEntityMetadata) {
        this.sqlNativeExpressionContext = sqlNativeExpressionContext;
        this.resultEntityMetadata = resultEntityMetadata;
    }

    @Override
    public SQLAliasNativeExpressionContext expressionAlias(String property) {
        Objects.requireNonNull(resultEntityMetadata, "result entity metadata cannot be null, plz use in select as sql context");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyAsAliasParamExpressionImpl columnPropertyAsAliasParamExpression = new ColumnPropertyAsAliasParamExpressionImpl(resultEntityMetadata.getColumnName(property));
        sqlNativeExpressionContext.getExpressions().add(columnPropertyAsAliasParamExpression);
        return this;
    }

    @Override
    public SQLAliasNativeExpressionContext setPropertyAlias(String property) {
        Objects.requireNonNull(resultEntityMetadata, "result entity metadata cannot be null, plz use in select as sql context");
        Objects.requireNonNull(property, "property cannot be null");
        this.setAlias(resultEntityMetadata.getColumnName(property));
        return this;
    }

    @Override
    public SQLAliasNativeExpressionContext expression(TableAvailable table, String property) {
        sqlNativeExpressionContext.expression(table,property);
        return this;
    }

    @Override
    public <TEntity> SQLAliasNativeExpressionContext expression(Query<TEntity> subQuery) {
        sqlNativeExpressionContext.expression(subQuery);
        return this;
    }

    @Override
    public SQLAliasNativeExpressionContext value(Object val) {
        sqlNativeExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLAliasNativeExpressionContext format(Object formatVal) {
        sqlNativeExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public List<ParamExpression> getExpressions() {
        return sqlNativeExpressionContext.getExpressions();
    }

    @Override
    public String getAlias() {
        return sqlNativeExpressionContext.getAlias();
    }

    @Override
    public SQLAliasNativeExpressionContext setAlias(String alias) {
        sqlNativeExpressionContext.setAlias(alias);
        return this;
    }
}
