package com.easy.query.core.expression.sql.def;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.UpdateStrategyEnum;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.internal.EasyBehavior;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
    protected final Set<String> interceptors;
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
        this.interceptors=new HashSet<>();
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

    @Override
    public void interceptor(String name) {
        interceptors.add(name);
    }

    @Override
    public void useInterceptor() {
        interceptors.clear();
        getBehavior().addBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
    }

    @Override
    public void noInterceptor() {
        interceptors.clear();
        getBehavior().removeBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
    }

    @Override
    public Stream<String> getInterceptorFilter(List<String> queryInterceptors) {
        boolean interceptorBehavior = getBehavior().hasBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
        //如果当前操作存在interceptor的behavior那么就不应该在interceptors里面
        //否则应该在interceptors里面
        return queryInterceptors.stream().filter(o->interceptors.contains(o)!=interceptorBehavior);
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
