package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLParameterCollector implements ToSQLContext {
    private final List<SQLParameter> parameters;
    private final SQLRewriteUnit sqlRewriteUnit;
    private int invokeCount;
    public DefaultSQLParameterCollector(){
        this(10,null);
    }
    public DefaultSQLParameterCollector(SQLRewriteUnit sqlRewriteUnit){
        this(10,sqlRewriteUnit);
    }
    public DefaultSQLParameterCollector(int initialCapacity,SQLRewriteUnit sqlRewriteUnit){
        this.parameters=new ArrayList<>(initialCapacity);
        this.sqlRewriteUnit = sqlRewriteUnit;
        this.invokeCount=0;
    }

    @Override
    public int expressionInvokeCountGetIncrement() {
        int oldInvokeCount = invokeCount;
        invokeCount++;
        return oldInvokeCount;
    }

    @Override
    public int currentInvokeCount() {
        return invokeCount;
    }

    @Override
    public void addParameter(SQLParameter sqlParameter) {
        parameters.add(sqlParameter);
    }

    @Override
    public List<SQLParameter> getParameters() {
        return parameters;
    }

    @Override
    public SQLRewriteUnit getSqlRewriteUnit() {
        return sqlRewriteUnit;
    }

    public static ToSQLContext defaultCollector(){
        return defaultCollector(10,null);
    }
    public static ToSQLContext defaultCollector(SQLRewriteUnit sqlRewriteUnit){
        return defaultCollector(10,sqlRewriteUnit);
    }
    public static ToSQLContext defaultCollector(int initialCapacity,SQLRewriteUnit sqlRewriteUnit){
        return new DefaultSQLParameterCollector(initialCapacity,sqlRewriteUnit);
    }
}
