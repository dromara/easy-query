package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.plugin.interceptor.InterceptorEntry;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author xuejiaming
 * @FileName: EasyQueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:06
 */
public class EasyExpressionContext implements ExpressionContext {
    private final QueryRuntimeContext runtimeContext;
    private final String alias;
    //    protected final List<SQLParameter> params;
    protected final EasyBehavior easyBehavior;
    protected final Set<String> useInterceptors;
    protected final Set<String> noInterceptors;
    private int aliasSeq = -1;
    private boolean deleteThrowException;
    private Object version;
    private ExecuteMethodEnum executeMethod = ExecuteMethodEnum.UNKNOWN;
    private SQLExecuteStrategyEnum sqlStrategy = SQLExecuteStrategyEnum.DEFAULT;

    private Integer maxShardingQueryLimit;
    private ConnectionModeEnum connectionMode;
    private boolean sharding;

    public EasyExpressionContext(QueryRuntimeContext runtimeContext, String alias) {

        this.runtimeContext = runtimeContext;
        QueryConfiguration queryConfiguration = runtimeContext.getQueryConfiguration();
        this.deleteThrowException = queryConfiguration.deleteThrow();
        this.alias = alias;
//        params = new ArrayList<>();
        //如果他是不查询大列的就去掉
        this.easyBehavior = new EasyBehavior();
        if(!queryConfiguration.getEasyQueryOption().isQueryLargeColumn()){
            easyBehavior.removeBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
        }
        this.useInterceptors = new HashSet<>();
        this.noInterceptors = new HashSet<>();
        this.maxShardingQueryLimit = null;
        this.connectionMode = null;
        this.sharding = false;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
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
        return runtimeContext.getQueryConfiguration().getDialect().getQuoteName(value);
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
    public void useSQLStrategy(SQLExecuteStrategyEnum sqlStrategy) {
        this.sqlStrategy = sqlStrategy;
    }

    @Override
    public SQLExecuteStrategyEnum getSQLStrategy() {
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
    public Stream<InterceptorEntry> getInterceptorFilter(List<InterceptorEntry> queryInterceptors) {
        boolean interceptorBehavior = getBehavior().hasBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
        //如果当前操作存在interceptor的behavior那么就不应该在interceptors里面
        //否则应该在interceptors里面
        return queryInterceptors.stream().filter(o -> {
            //如果是启用了的
            if (interceptorBehavior) {
                //拦截器手动指定使用的或者默认要用的并且没有说不用的
                return useInterceptors.contains(o.getName()) || (o.isDefaultEnable() && !noInterceptors.contains(o.getName()));
            } else {
                //手动指定要用的并且不在不使用里面
                return useInterceptors.contains(o.getName()) && !noInterceptors.contains(o.getName());
            }
        });
    }

    @Override
    public void executeMethod(ExecuteMethodEnum executeMethod, boolean ifUnknown) {
        if (ifUnknown) {
            if (Objects.equals(ExecuteMethodEnum.UNKNOWN, this.executeMethod)) {
                this.executeMethod = executeMethod;
            }
        } else {
            this.executeMethod = executeMethod;
        }
    }

    @Override
    public ExecuteMethodEnum getExecuteMethod() {
        return this.executeMethod;
    }

    @Override
    public void setMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.maxShardingQueryLimit = maxShardingQueryLimit;
    }

    @Override
    public Integer getMaxShardingQueryLimitOrNull() {
        return maxShardingQueryLimit;
    }

    @Override
    public void setConnectionMode(ConnectionModeEnum connectionMode) {
        this.connectionMode = connectionMode;
    }

    @Override
    public ConnectionModeEnum getConnectionModeOrNull() {
        return connectionMode;
    }

    @Override
    public void useSharding() {
        this.sharding=true;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public void extract(ExpressionContext otherExpressionContext) {
        if(otherExpressionContext.isSharding()){
            this.sharding=true;
        }
    }
}
