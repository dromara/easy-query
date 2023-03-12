package com.easy.query.core.query;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

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
    private int aliasSeq = -1;
    private boolean deleteThrowException;
    private boolean useLogicDelete = true;
    private boolean useInterceptor = true;

    public EasySqlExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias) {

        this.runtimeContext = runtimeContext;
        this.deleteThrowException = runtimeContext.getEasyQueryConfiguration().cantDelete();
        this.alias = alias;
        params = new ArrayList<>();
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
        return aliasSeq == 0 ? alias : (alias + aliasSeq);
    }

    @Override
    public String getQuoteName(String value) {
        return runtimeContext.getEasyQueryConfiguration().getDialect().getQuoteName(value);
    }

    @Override
    public void extractParameters(SqlExpressionContext sqlExpressionContext) {
        if (!Objects.equals(this, sqlExpressionContext) && !sqlExpressionContext.getParameters().isEmpty()) {
            params.addAll(sqlExpressionContext.getParameters());
        }

    }

    @Override
    public void clearParameters() {
        if (!params.isEmpty()) {
            params.clear();
        }
    }

    @Override
    public void deleteThrow(boolean ifDeleteThrowException) {
        this.deleteThrowException = ifDeleteThrowException;
    }

    @Override
    public boolean isDeleteThrow() {
        return deleteThrowException;
    }

    @Override
    public void disableLogicDelete() {
        this.useLogicDelete = false;
    }

    @Override
    public void enableLogicDelete() {
        this.useLogicDelete = true;
    }

    @Override
    public boolean isUseLogicDelete() {
        return useLogicDelete;
    }

    @Override
    public void useInterceptor() {
        this.useInterceptor = true;
    }

    @Override
    public void noInterceptor() {
        this.useInterceptor = false;
    }

    @Override
    public boolean isUseInterceptor() {
        return useInterceptor;
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
