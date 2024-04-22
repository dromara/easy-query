package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.sql.Statement;

/**
 * @author xuejiaming
 * @FileName: ExecutorContext.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:51
 */
public class ExecutorContext {
    private final ExpressionContext expressionContext;
    private final boolean isQuery;
    private boolean mapToBeanStrict = true;
    private final ExecuteMethodEnum executeMethod;
    private final boolean tracking;
    private final EasyQueryOption easyQueryOption;
    private SQLConsumer<Statement> configurer;

    public ExecutorContext(ExpressionContext expressionContext, boolean isQuery, ExecuteMethodEnum executeMethod) {
        this(expressionContext, isQuery, executeMethod, false);
    }

    public ExecutorContext(ExpressionContext expressionContext, boolean isQuery, ExecuteMethodEnum executeMethod, boolean tracking) {
        this.expressionContext = expressionContext;
        this.easyQueryOption = expressionContext.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
        this.isQuery = isQuery;
        this.executeMethod = executeMethod;
        this.tracking = tracking;
        this.mapToBeanStrict = easyQueryOption.isMapToBeanStrict();
    }

    public static ExecutorContext create(ExpressionContext expressionContext, boolean isQuery, ExecuteMethodEnum executeMethod) {
        return new ExecutorContext(expressionContext, isQuery, executeMethod);
    }

    public static ExecutorContext create(ExpressionContext expressionContext, boolean isQuery, ExecuteMethodEnum executeMethod, boolean tracking) {
        return new ExecutorContext(expressionContext, isQuery, executeMethod, tracking);
    }


    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return expressionContext.getRuntimeContext();
    }

    public boolean isTracking() {
        return tracking;
    }



    public boolean isQuery() {
        return isQuery;
    }


    public ExecuteMethodEnum getExecuteMethod() {
        return executeMethod;
    }

    public EasyQueryOption getEasyQueryOption() {
        return easyQueryOption;
    }

    public boolean isMapToBeanStrict() {
        return mapToBeanStrict;
    }

    public void setMapToBeanStrict(boolean mapToBeanStrict) {
        this.mapToBeanStrict = mapToBeanStrict;
    }

    /**
     * 可为空
     * @return
     */
    public SQLConsumer<Statement> getConfigurer() {
        return configurer;
    }

    public void setConfigurer(SQLConsumer<Statement> configurer) {
        this.configurer = configurer;
    }

    public SQLConsumer<Statement> getConfigurer(boolean isSharding){
        if(isSharding){
            int shardingFetchSize = easyQueryOption.getShardingFetchSize();
            if(this.configurer==null){
                return statement -> statement.setFetchSize(shardingFetchSize);
            }else{
                return statement -> {
                    statement.setFetchSize(shardingFetchSize);
                    configurer.accept(statement);
                };
            }
        }
        return configurer;
    }
}
