package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptorEntry;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

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
//    protected final List<SQLParameter> params;
    protected final EasyBehavior easyBehavior;
    protected final Set<String> useInterceptors;
    protected final Set<String> noInterceptors;
    private int aliasSeq = -1;
    private boolean deleteThrowException;
    private Object version;
    private ExecuteMethodEnum executeMethod=ExecuteMethodEnum.UNKNOWN;
    private SqlExecuteStrategyEnum sqlStrategy = SqlExecuteStrategyEnum.DEFAULT;

    public EasyExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias) {

        this.runtimeContext = runtimeContext;
        this.deleteThrowException = runtimeContext.getEasyQueryConfiguration().deleteThrow();
        this.alias = alias;
//        params = new ArrayList<>();
        this.easyBehavior=new EasyBehavior();
        this.useInterceptors =new HashSet<>();
        this.noInterceptors =new HashSet<>();
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
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

//    @Override
//    public void extractParameters(ExpressionContext sqlExpressionContext) {
//        if (!Objects.equals(this, sqlExpressionContext) && !sqlExpressionContext.getParameters().isEmpty()) {
//            params.addAll(sqlExpressionContext.getParameters());
//        }
//
//    }
//
//    @Override
//    public void clearParameters() {
//        if (!params.isEmpty()) {
//            params.clear();
//        }
//    }

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
    public void useSqlStrategy(SqlExecuteStrategyEnum sqlStrategy) {
        this.sqlStrategy = sqlStrategy;
    }

    @Override
    public SqlExecuteStrategyEnum getSqlStrategy() {
        return sqlStrategy;
    }

    public Object getVersion() {
        return version;
    }


    @Override
    public void setVersion(Object version) {
        this.version = version;
    }

    @Override
    public void useInterceptor(String name) {
        noInterceptors.remove(name);
        useInterceptors.add(name);
    }

    @Override
    public void noInterceptor(String name) {
        useInterceptors.remove(name);
        noInterceptors.add(name);
    }

    @Override
    public void useInterceptor() {
        useInterceptors.clear();
        noInterceptors.clear();
        getBehavior().addBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
    }

    @Override
    public void noInterceptor() {
        useInterceptors.clear();
        noInterceptors.clear();
        getBehavior().removeBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
    }

    @Override
    public Stream<EasyInterceptorEntry> getInterceptorFilter(List<EasyInterceptorEntry> queryInterceptors) {
        boolean interceptorBehavior = getBehavior().hasBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
        //如果当前操作存在interceptor的behavior那么就不应该在interceptors里面
        //否则应该在interceptors里面
        return queryInterceptors.stream().filter(o->{
            //如果是启用了的
            if(interceptorBehavior){
                //拦截器手动指定使用的或者默认要用的并且没有说不用的
                return useInterceptors.contains(o.getName())||(o.isDefaultEnable()&&!noInterceptors.contains(o.getName()));
            }else{
                //手动指定要用的并且不在不使用里面
                return useInterceptors.contains(o.getName())&&!noInterceptors.contains(o.getName());
            }
        });
    }

    @Override
    public void executeMethod(ExecuteMethodEnum executeMethod,boolean ifUnknown) {
        if(ifUnknown){
            if(Objects.equals(ExecuteMethodEnum.UNKNOWN,this.executeMethod)){
                this.executeMethod=executeMethod;
            }
        }else{
            this.executeMethod=executeMethod;
        }
    }

    @Override
    public ExecuteMethodEnum getExecuteMethod() {
        return this.executeMethod;
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
