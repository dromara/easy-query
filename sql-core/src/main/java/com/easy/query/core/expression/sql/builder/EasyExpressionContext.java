package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.basic.extension.conversion.ColumnReader;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ContextTypeEnum;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilterFactory;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.impl.AnonymousCteTableQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.expression.sql.builder.internal.ExpressionContextInterceptor;
import com.easy.query.core.expression.sql.fill.FillExpression;
import com.easy.query.core.expression.sql.include.ColumnIncludeExpression;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.RelationExtraMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    protected final ContextTypeEnum type;
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
    private List<FillExpression> fills;
    private List<ExpressionBuilder> declareExpressions;
    private Consumer<Object> forEachConfigurer;

    private Map<TableAvailable, Map<String, ColumnIncludeExpression>> columnIncludeMaps;
    private ResultColumnMetadata[] propTypes;
    private Map<String, ColumnReader> valueConverterMap;
    private Function<Class<?>, Boolean> relationLogicDelete;
    private RelationExtraMetadata relationExtraMetadata;
    private Integer groupSize;
    private long resultSizeLimit;
    private Boolean printSQL;
    private Boolean printNavSQL;
    private ConfigureArgument configureArgument;
    private boolean reverseOrder;
    private Map<Object,Object> flatClassMap;

    public EasyExpressionContext(QueryRuntimeContext runtimeContext, ContextTypeEnum type) {

        this.runtimeContext = runtimeContext;
        QueryConfiguration queryConfiguration = runtimeContext.getQueryConfiguration();
        this.deleteThrowException = queryConfiguration.deleteThrow();
//        params = new ArrayList<>();
        //如果他是不查询大列的就去掉
        this.easyBehavior = new EasyBehavior();
        EasyQueryOption easyQueryOption = queryConfiguration.getEasyQueryOption();

        if (easyQueryOption.isDefaultTrack()) {
            easyBehavior.addBehavior(EasyBehaviorEnum.USE_TRACKING);
        }
        this.expressionContextInterceptor = new ExpressionContextInterceptor();
        this.tableContext = new TableContext();
        this.maxShardingQueryLimit = null;
        this.connectionMode = null;
        this.sharding = false;
        this.reverseOrder = true;
        this.type = type;
        this.configureArgument = new ConfigureArgument();

        ValueFilterFactory valueFilterFactory = runtimeContext.getValueFilterFactory();
        if (type == ContextTypeEnum.QUERY) {
            this.valueFilter = valueFilterFactory.getQueryValueFilter();
        } else if (type == ContextTypeEnum.INSERT) {
            this.valueFilter = valueFilterFactory.getInsertValueFilter();
        } else if (type == ContextTypeEnum.UPDATE) {
            this.valueFilter = valueFilterFactory.getUpdateValueFilter();
        } else if (type == ContextTypeEnum.DELETE) {
            this.valueFilter = valueFilterFactory.getDeleteValueFilter();
        } else if (type == ContextTypeEnum.EXECUTE) {
            this.valueFilter = valueFilterFactory.getExecuteValueFilter();
        } else {
            throw new EasyQueryInvalidOperationException("unknown context type:" + type);
        }
        this.groupSize = easyQueryOption.getRelationGroupSize();
        this.resultSizeLimit = easyQueryOption.getResultSizeLimit();
        this.flatClassMap = new HashMap<>();
    }

    @Override
    public ContextTypeEnum getType() {
        return null;
    }

    @Override
    public ExpressionContextInterceptor getExpressionContextInterceptor() {
        return expressionContextInterceptor;
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
    public void setMaxShardingQueryLimit(Integer maxShardingQueryLimit) {
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
        if (this == otherExpressionContext) {
            return;
        }
        if (otherExpressionContext.isSharding()) {
            this.sharding = true;
        }
        this.hasSubQuery = true;
        tableContext.extract(otherExpressionContext.getTableContext());
        if (otherExpressionContext.hasDeclareExpressions()) {
            copyDeclareExpression(getDeclareExpressions(), otherExpressionContext.getDeclareExpressions());
        }
    }
    private void copyDeclareExpression(List<ExpressionBuilder> sourceDeclareExpressions,List<ExpressionBuilder> targetDeclareExpressions){

        Set<String> cteTables = sourceDeclareExpressions.stream().filter(o -> (o instanceof AnonymousCteTableQueryExpressionBuilder)).map(o -> ((AnonymousCteTableQueryExpressionBuilder) o).getCteTableName()).collect(Collectors.toSet());
        for (ExpressionBuilder declareExpression : targetDeclareExpressions) {
            if(declareExpression instanceof AnonymousCteTableQueryExpressionBuilder){
                if(cteTables.contains(((AnonymousCteTableQueryExpressionBuilder) declareExpression).getCteTableName())){
                    continue;
                }
            }
            getDeclareExpressions().add(declareExpression);
        }
    }

    @Override
    public void extendFrom(ExpressionContext otherExpressionContext) {
        this.easyBehavior.copyTo(otherExpressionContext.getBehavior());
        expressionContextInterceptor.copyTo(otherExpressionContext.getExpressionContextInterceptor());
        otherExpressionContext.deleteThrow(this.deleteThrowException);
        otherExpressionContext.setVersion(this.version);
        otherExpressionContext.executeMethod(this.executeMethod);
        otherExpressionContext.useSQLStrategy(this.sqlStrategy);
        otherExpressionContext.setMaxShardingQueryLimit(this.maxShardingQueryLimit);
        otherExpressionContext.setConnectionMode(this.connectionMode);
        otherExpressionContext.filterConfigure(this.valueFilter);
        otherExpressionContext.setConfigureArgument(this.configureArgument);
        otherExpressionContext.setReverseOrder(this.reverseOrder);
        if (hasRelationExtraMetadata()) {
            this.relationExtraMetadata.copyTo(otherExpressionContext.getRelationExtraMetadata());
        }
        otherExpressionContext.setPrintSQL(this.printSQL);
        otherExpressionContext.setPrintNavSQL(this.printNavSQL);
        if (hasIncludes()) {
            otherExpressionContext.getIncludes().putAll(this.includes);
        }
        if (hasFills()) {
            otherExpressionContext.getFills().addAll(this.fills);
        }
        if (hasColumnIncludeMaps()) {
            otherExpressionContext.getColumnIncludeMaps().putAll(this.columnIncludeMaps);
        }
        if (hasDeclareExpressions()) {

            copyDeclareExpression(otherExpressionContext.getDeclareExpressions(), this.declareExpressions);
        }
        if (this.propTypes != null) {
            otherExpressionContext.setResultPropTypes(new ResultColumnMetadata[this.propTypes.length]);
            System.arraycopy(this.propTypes, 0, otherExpressionContext.getResultPropTypes(), 0, this.propTypes.length);
        }
        if (this.valueConverterMap != null) {
            otherExpressionContext.getResultValueConverterMap(true).putAll(this.valueConverterMap);
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
    public List<FillExpression> getFills() {
        if (fills == null) {
            fills = new ArrayList<>();
        }
        return fills;
    }

    @Override
    public boolean hasFills() {
        return EasyCollectionUtil.isNotEmpty(fills);
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
        EasyExpressionContext easyExpressionContext = new EasyExpressionContext(runtimeContext, this.type);
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
        easyExpressionContext.valueFilter = this.valueFilter;
        easyExpressionContext.hasSubQuery = this.hasSubQuery;
        easyExpressionContext.relationExtraMetadata = this.relationExtraMetadata;
        easyExpressionContext.printSQL = this.printSQL;
        easyExpressionContext.printNavSQL = this.printNavSQL;
        easyExpressionContext.configureArgument = this.configureArgument;
        easyExpressionContext.reverseOrder = this.reverseOrder;
        if (hasIncludes()) {
            easyExpressionContext.getIncludes().putAll(this.includes);
        }
        if (hasFills()) {
            easyExpressionContext.getFills().addAll(this.fills);
        }
        if (hasColumnIncludeMaps()) {
            easyExpressionContext.getColumnIncludeMaps().putAll(this.columnIncludeMaps);
        }
        if (hasDeclareExpressions()) {
            easyExpressionContext.getDeclareExpressions().addAll(this.declareExpressions);
        }
        if (this.propTypes != null) {
            easyExpressionContext.propTypes = new ResultColumnMetadata[this.propTypes.length];
            System.arraycopy(this.propTypes, 0, easyExpressionContext.propTypes, 0, this.propTypes.length);
        }
        if (this.valueConverterMap != null) {
            easyExpressionContext.valueConverterMap = new HashMap<>(this.valueConverterMap);
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
    public Map<String, ColumnReader> getResultValueConverterMap(boolean createIfNull) {
        if (createIfNull && valueConverterMap == null) {
            valueConverterMap = new HashMap<>();
        }
        return valueConverterMap;
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

    @Override
    public Boolean getPrintSQL() {
        return printSQL;
    }

    @Override
    public void setPrintSQL(Boolean printSQL) {
        this.printSQL = printSQL;
    }

    @Override
    public Boolean getPrintNavSQL() {
        return printNavSQL;
    }

    @Override
    public void setPrintNavSQL(Boolean printSQL) {
        this.printNavSQL = printSQL;
    }

    @Override
    public void setConfigureArgument(Object arg) {
        this.configureArgument.setArg(arg);
    }

    @Override
    public ConfigureArgument getConfigureArgument() {
        return configureArgument;
    }

    @Override
    public void setReverseOrder(boolean reverseOrder) {
        this.reverseOrder = reverseOrder;
    }

    @Override
    public boolean isReverseOrder() {
        return this.reverseOrder;
    }

    @Override
    public Map<Object, Object> getFlatClassMap() {
        return flatClassMap;
    }
}
