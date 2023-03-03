package org.easy.query.core.expression.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: QueryableContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:57
 * @Created by xuejiaming
 */
public class QueryableContext {
    private final List<SelectContext> selectContexts;
    protected final List<SQLParameter> params;
    private final EasyQueryRuntimeContext runtimeContext;
    private final String alias;

    public QueryableContext(EasyQueryRuntimeContext runtimeContext,String alias){
        this.runtimeContext = runtimeContext;
        this.alias = alias;
        this.params=new ArrayList<>();
        this.selectContexts = new ArrayList<>();
    }

    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
    public List<SQLParameter> getParameters() {
        return params;
    }
    public void addParameter(SQLParameter parameter) {
        params.add(parameter);
    }

    public String getAlias() {
        return alias;
    }
    public void addSelectContext(SelectContext selectContext){
        selectContexts.add(selectContext);
    }

    public String toSql(){

    }
}
