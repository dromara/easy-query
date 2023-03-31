package com.easy.query.core.expression.sql.def;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.UpdateStrategyEnum;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.internal.EasyBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @FileName: EasyQueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:06
 * @author xuejiaming
 */
public class EasyExpressionContext implements ExpressionContext {
    private final EasyQueryRuntimeContext runtimeContext;
    private final String alias;
    protected final List<SQLParameter> params;
    protected final EasyBehavior easyBehavior;
    private int aliasSeq = -1;
    private boolean deleteThrowException;
    private Object version;
    private UpdateStrategyEnum updateStrategy = UpdateStrategyEnum.DEFAULT;

    public EasyExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias) {

        this.runtimeContext = runtimeContext;
        this.deleteThrowException = runtimeContext.getEasyQueryConfiguration().deleteThrow();
        this.alias = alias;
        params = new ArrayList<>();
        this.easyBehavior=new EasyBehavior();
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
    public void extractParameters(ExpressionContext sqlExpressionContext) {
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
    public EasyBehavior getBehavior() {
        return easyBehavior;
    }

    @Override
    public void useUpdateStrategy(UpdateStrategyEnum updateStrategy) {
        this.updateStrategy=updateStrategy;
    }

    @Override
    public UpdateStrategyEnum getUpdateStrategy() {
        return updateStrategy;
    }

    public Object getVersion() {
        return version;
    }


    @Override
    public void setVersion(Object version) {
        this.version = version;
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
