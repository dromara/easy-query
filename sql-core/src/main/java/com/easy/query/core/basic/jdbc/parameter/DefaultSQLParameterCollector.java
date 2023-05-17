package com.easy.query.core.basic.jdbc.parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/4/23 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLParameterCollector implements SQLParameterCollector {
    private final List<SQLParameter> parameters;
    private int invokeCount;
    public DefaultSQLParameterCollector(){
        this(10);
    }
    public DefaultSQLParameterCollector(int initialCapacity){
        this.parameters=new ArrayList<>(initialCapacity);
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

    public static SQLParameterCollector defaultCollector(){
        return defaultCollector(10);
    }
    public static SQLParameterCollector defaultCollector(int initialCapacity){
        return new DefaultSQLParameterCollector();
    }
}
