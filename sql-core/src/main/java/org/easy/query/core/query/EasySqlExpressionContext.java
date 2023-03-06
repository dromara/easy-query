package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @FileName: EasyQueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:06
 * @Created by xuejiaming
 */
public class EasySqlExpressionContext implements SqlExpressionContext {
    private final EasyQueryRuntimeContext runtimeContext;
    private final String alias;
    protected final List<SQLParameter> params;
    private int aliasSeq=-1;

    public EasySqlExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias){

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
    public void extractParameters(SqlExpressionContext sqlExpressionContext) {
        if(!Objects.equals(this,sqlExpressionContext) &&!sqlExpressionContext.getParameters().isEmpty())
        {
            params.addAll(sqlExpressionContext.getParameters());
        }

    }

    @Override
    public void clearParameters() {
        if(!params.isEmpty()){
            params.clear();
        }
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
