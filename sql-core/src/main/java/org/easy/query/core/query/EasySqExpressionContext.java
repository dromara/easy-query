package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasyQueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:06
 * @Created by xuejiaming
 */
public class EasySqExpressionContext implements SqlExpressionContext {
    private final EasyQueryRuntimeContext runtimeContext;
    private final String alias;
    protected final List<SQLParameter> params;
    private int aliasSeq=-1;

    public EasySqExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias){

        this.runtimeContext = runtimeContext;
        this.alias = alias;
        params=new ArrayList<>();
    }
    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public List<SQLParameter> getParameters() {
        return params;
    }

    @Override
    public void addParameter(SQLParameter parameter) {
        params.add(parameter);
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String createTableAlias() {
        aliasSeq++;
        return aliasSeq==0?alias:(alias+1);
    }

    @Override
    public String getQuoteName(String value) {
        return runtimeContext.getEasyQueryConfiguration().getDialect().getQuoteName(value);
    }

    @Override
    public SqlExpressionContext cloneSqlExpressionContext() {
        EasySqExpressionContext easySqExpressionContext = new EasySqExpressionContext(runtimeContext,alias);
        easySqExpressionContext.aliasSeq=this.aliasSeq;
        easySqExpressionContext.params.addAll(this.params);
        return easySqExpressionContext;
    }

    //    @Override
//    public String getSqlColumnSegment(SqlEntityTableExpressionSegment table, String propertyName) {
//        String alias = table.getAlias();
//        String columnName = table.getColumnName(propertyName);
//        String quoteName = getQuoteName(columnName);
//        if(alias==null){
//            return quoteName;
//        }else{
//            return alias+"."+quoteName;
//        }
//    }
}
