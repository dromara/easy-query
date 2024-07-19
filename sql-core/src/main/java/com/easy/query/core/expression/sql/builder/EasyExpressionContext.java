package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.expression.sql.builder.internal.ExpressionContextInterceptor;
import com.easy.query.core.expression.sql.include.ColumnIncludeExpression;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.RelationExtraMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * create time 2023/3/3 23:06
 * 表达式上下文用于记录当前表达式的行为和表信息
 *
 * @author xuejiaming
 */
public class EasyExpressionContext implements ExpressionContext {
    private final QueryRuntimeContext runtimeContext;
    //    protected final List<SQLParameter> params;
    protected final EasyBehavior easyBehavior;
    protected final ExpressionContextInterceptor expressionContextInterceptor;
    protected final TableContext tableContext;
    private boolean deleteThrowException;
    private Object version;
    private ExecuteMethodEnum executeMethod = ExecuteMethodEnum.UNKNOWN;
    private SQLExecuteStrategyEnum sqlStrategy = SQLExecuteStrategyEnum.DEFAULT;

    private Integer maxShardingQueryLimit;
    private ConnectionModeEnum connectionMode;
    private boolean sharding;
    private boolean hasSubQuery;
    private ValueFilter valueFilter;
    private Map<NavigateMetadata, IncludeNavigateExpression> includes;
    private List<ExpressionBuilder> declareExpressions;
    private Consumer<Object> forEachConfigurer;

    private Map<TableAvailable, Map<String, ColumnIncludeExpression>> columnIncludeMaps;
    private ResultColumnMetadata[] propTypes;
    private Function<Class<?>, Boolean> relationLogicDelete;
    private RelationExtraMetadata relationExtraMetadata;
    private Integer groupSize;
    private long resultSizeLimit;

    public EasyExpressionContext(QueryRuntimeContext runtimeContext) {

        this.runtimeContext = runtimeContext;
        QueryConfiguration queryConfiguration = runtimeContext.getQueryConfiguration();
        this.deleteThrowException = queryConfiguration.deleteThrow();
//        params = new ArrayList<>();
        //如果他是不查询大列的就去掉
        this.easyBehavior = new EasyBehavior();
        EasyQueryOption easyQueryOption = queryConfiguration.getEasyQueryOption();
        if (!easyQueryOption.isQueryLargeColumn()) {
            easyBehavior.removeBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
        }
        if (easyQueryOption.isDefaultTrack()) {
            easyBehavior.addBehavior(EasyBehaviorEnum.USE_TRACKING);
        }
        this.expressionContextInterceptor = new ExpressionContextInterceptor();
        this.tableContext = new TableContext();
        this.maxShardingQueryLimit = null;
        this.connectionMode = null;
        this.sharding = false;
        this.valueFilter = AnyValueFilter.DEFAULT;
//        this.groupSize=easyQueryOption.getRelationGroupSize();
        this.resultSizeLimit = easyQueryOption.getResultSizeLimit();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
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
        expressionContextInterceptor.useInterceptor(name);
    }

    @Override
    public void noInterceptor(String name) {
        expressionContextInterceptor.noInterceptor(name);
    }

    @Override
    public void useInterceptor() {
        expressionContextInterceptor.useInterceptor();
        getBehavior().addBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
    }

    @Override
    public void noInterceptor() {
        expressionContextInterceptor.noInterceptor();
        getBehavior().removeBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
    }

    @Override
    public Predicate<Interceptor> getInterceptorFilter() {
        boolean interceptorBehavior = getBehavior().hasBehavior(EasyBehaviorEnum.USE_INTERCEPTOR);
        //如果当前操作存在interceptor的behavior那么就不应该在interceptors里面
        //否则应该在interceptors里面
        return o -> {
            //如果是启用了的
            if (interceptorBehavior) {
                //拦截器手动指定使用的或者默认要用的并且没有说不用的
                return expressionContextInterceptor.useContains(o.name()) || (!expressionContextInterceptor.noContains(o.name()) && o.enable());
            } else {
                //手动指定要用的并且不在不使用里面
                return expressionContextInterceptor.useContains(o.name()) && !expressionContextInterceptor.noContains(o.name());
            }
        };
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
        this.sharding = true;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public void extract(ExpressionContext otherExpressionContext) {
        if (otherExpressionContext.isSharding()) {
            this.sharding = true;
        }
        this.hasSubQuery = true;
        tableContext.extract(otherExpressionContext.getTableContext());
        if (otherExpressionContext.hasDeclareExpressions()) {
            for (ExpressionBuilder declareExpression : otherExpressionContext.getDeclareExpressions()) {
                getDeclareExpressions().add(declareExpression);
            }
        }
    }

    @Override
    public boolean hasSubQuery() {
        return hasSubQuery;
    }


    @Override
    public Map<NavigateMetadata, IncludeNavigateExpression> getIncludes() {
        if (includes == null) {
            includes = new LinkedHashMap<>();
        }
        return includes;
    }

    @Override
    public boolean hasIncludes() {
        return includes != null && !includes.isEmpty();
    }

    @Override
    public Map<TableAvailable, Map<String, ColumnIncludeExpression>> getColumnIncludeMaps() {
        if (columnIncludeMaps == null) {
            this.columnIncludeMaps = new HashMap<>();
        }
        return columnIncludeMaps;
    }

    @Override
    public boolean hasColumnIncludeMaps() {
        return columnIncludeMaps != null && !columnIncludeMaps.isEmpty();
    }

    @Override
    public void filterConfigure(ValueFilter valueFilter) {
        Objects.requireNonNull(valueFilter, "valueFilter can not be null");
        this.valueFilter = valueFilter;
    }

    @Override
    public ValueFilter getValueFilter() {
        return valueFilter;
    }

    @Override
    public Consumer<Object> getForEachConfigurer() {
        return forEachConfigurer;
    }

    @Override
    public void setForEachConfigurer(Consumer<Object> forEachConfigurer) {
        this.forEachConfigurer = forEachConfigurer;
    }

    @Override
    public TableContext getTableContext() {
        return tableContext;
    }

    @Override
    public ExpressionContext cloneExpressionContext() {
        EasyExpressionContext easyExpressionContext = new EasyExpressionContext(runtimeContext);
        this.easyBehavior.copyTo(easyExpressionContext.easyBehavior);
        expressionContextInterceptor.copyTo(easyExpressionContext.expressionContextInterceptor);
        this.tableContext.copyTo(easyExpressionContext.tableContext);
        easyExpressionContext.deleteThrowException = this.deleteThrowException;
        easyExpressionContext.version = this.version;
        easyExpressionContext.executeMethod = this.executeMethod;
        easyExpressionContext.sqlStrategy = this.sqlStrategy;
        easyExpressionContext.maxShardingQueryLimit = this.maxShardingQueryLimit;
        easyExpressionContext.connectionMode = this.connectionMode;
        easyExpressionContext.sharding = this.sharding;
        easyExpressionContext.hasSubQuery = this.hasSubQuery;
        easyExpressionContext.relationExtraMetadata = this.relationExtraMetadata;
        if (hasIncludes()) {
            easyExpressionContext.getIncludes().putAll(this.includes);
        }
        if (hasColumnIncludeMaps()) {
            easyExpressionContext.getColumnIncludeMaps().putAll(this.columnIncludeMaps);
        }
        if (this.propTypes != null) {
            easyExpressionContext.propTypes = new ResultColumnMetadata[this.propTypes.length];
            System.arraycopy(this.propTypes, 0, easyExpressionContext.propTypes, 0, this.propTypes.length);
        }
        return easyExpressionContext;
    }

    @Override
    public List<ExpressionBuilder> getDeclareExpressions() {
        if (declareExpressions == null) {
            declareExpressions = new ArrayList<>();
        }
        return declareExpressions;
    }

    @Override
    public boolean hasDeclareExpressions() {
        return EasyCollectionUtil.isNotEmpty(declareExpressions);
    }

    @Override
    public void setResultPropTypes(ResultColumnMetadata[] propTypes) {
        this.propTypes = propTypes;
    }

    @Override
    public ResultColumnMetadata[] getResultPropTypes() {
        return this.propTypes;
    }

    @Override
    public void setRelationLogicDelete(Function<Class<?>, Boolean> relationLogicDelete) {
        this.relationLogicDelete = relationLogicDelete;
    }

    @Override
    public boolean hasRelationLogicDelete() {
        return this.relationLogicDelete != null;
    }

    @Override
    public Function<Class<?>, Boolean> getRelationLogicDelete() {
        return this.relationLogicDelete;
    }

    @Override
    public RelationExtraMetadata getRelationExtraMetadata() {
        if (relationExtraMetadata == null) {
            this.relationExtraMetadata = new RelationExtraMetadata();
        }
        return relationExtraMetadata;
    }

    @Override
    public boolean hasRelationExtraMetadata() {
        return this.relationExtraMetadata != null && !this.relationExtraMetadata.getRelationExtraColumnMap().isEmpty();
    }

    @Override
    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    @Override
    public Integer getGroupSize() {
        return groupSize;
    }

    @Override
    public void setResultSizeLimit(long resultSizeLimit) {
        this.resultSizeLimit = resultSizeLimit;
    }

    @Override
    public long getResultSizeLimit() {
        return resultSizeLimit;
    }
}
