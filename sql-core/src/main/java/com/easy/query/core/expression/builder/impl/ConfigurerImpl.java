package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Configurer;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.ColumnConfigurerContext;
import com.easy.query.core.expression.sql.builder.EntityColumnConfigurerExpressionBuilder;

import java.util.Objects;

/**
 * create time 2023/8/6 10:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConfigurerImpl implements Configurer {
    private final QueryRuntimeContext runtimeContext;
    private final EntityColumnConfigurerExpressionBuilder entityColumnConfigurerExpressionBuilder;

    public ConfigurerImpl(EntityColumnConfigurerExpressionBuilder entityColumnConfigurerExpressionBuilder){
        this.entityColumnConfigurerExpressionBuilder = entityColumnConfigurerExpressionBuilder;

        this.runtimeContext = entityColumnConfigurerExpressionBuilder.getRuntimeContext();
    }
    @Override
    public Configurer column(TableAvailable table, String property, String sqlSegment, SQLExpression2<SQLNativeExpressionContext, SQLParameter> contextConsume) {
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl();
        PropertySQLParameter propertySQLParameter = new PropertySQLParameter(table, property);
        contextConsume.apply(sqlNativeExpressionContext,propertySQLParameter);
        entityColumnConfigurerExpressionBuilder.getColumnConfigurer().put(property,new ColumnConfigurerContext(runtimeContext,sqlSegment,sqlNativeExpressionContext));
        return this;
    }
}
