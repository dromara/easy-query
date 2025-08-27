package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.basic.extension.conversion.ColumnReader;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ContextTypeEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEOption;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.expression.sql.builder.internal.ExpressionContextInterceptor;
import com.easy.query.core.expression.sql.fill.FillExpression;
import com.easy.query.core.expression.sql.include.ColumnIncludeExpression;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.RelationExtraMetadata;
import com.easy.query.core.metadata.TreeDeepItem;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * create time 2023/10/20 23:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyExpressionContext implements ExpressionContext{
    private final QueryRuntimeContext runtimeContext;


    public EmptyExpressionContext(QueryRuntimeContext runtimeContext){

        this.runtimeContext = runtimeContext;
    }

    @Override
    public ContextTypeEnum getType() {
        return null;
    }

    @Override
    public TreeCTEOption getTreeCTEOption() {
        return null;
    }

    @Override
    public void setTreeCTEOption(TreeCTEOption treeCTEOption) {

    }

    @Override
    public List<TreeDeepItem> getDeepItems() {
        return Collections.emptyList();
    }

    @Override
    public ExpressionContextInterceptor getExpressionContextInterceptor() {
        return null;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public String getQuoteName(String value) {
        return null;
    }

    @Override
    public void deleteThrow(boolean ifDeleteThrowException) {

    }

    @Override
    public boolean isDeleteThrow() {
        return false;
    }

    @Override
    public EasyBehavior getBehavior() {
        return EasyBehavior.DEFAULT;
    }

    @Override
    public void useSQLStrategy(SQLExecuteStrategyEnum sqlStrategy) {

    }

    @Override
    public SQLExecuteStrategyEnum getSQLStrategy() {
        return null;
    }

    @Override
    public void setVersion(Object version) {

    }

    @Override
    public Object getVersion() {
        return null;
    }

    @Override
    public void useInterceptor(String name) {

    }

    @Override
    public void noInterceptor(String name) {

    }

    @Override
    public void useInterceptor() {

    }

    @Override
    public void noInterceptor() {

    }

    @Override
    public Predicate<Interceptor> getInterceptorFilter() {
        return null;
    }

    @Override
    public boolean isTreeCTE(NavigateMetadata navigateMetadata) {
        return false;
    }

    @Override
    public void setTreeCTE(NavigateMetadata navigateMetadata) {

    }

    @Override
    public void executeMethod(ExecuteMethodEnum executeMethod, boolean ifUnknown) {

    }

    @Override
    public ExecuteMethodEnum getExecuteMethod() {
        return null;
    }

    @Override
    public void setMaxShardingQueryLimit(Integer maxShardingQueryLimit) {

    }

    @Override
    public Integer getMaxShardingQueryLimitOrNull() {
        return null;
    }

    @Override
    public void setConnectionMode(ConnectionModeEnum connectionMode) {

    }

    @Override
    public ConnectionModeEnum getConnectionModeOrNull() {
        return null;
    }

    @Override
    public void useSharding() {

    }

    @Override
    public boolean isSharding() {
        return false;
    }

    @Override
    public void extract(ExpressionContext otherExpressionContext) {

    }

    @Override
    public void extendFrom(ExpressionContext otherExpressionContext) {

    }

    @Override
    public boolean hasSubQuery() {
        return false;
    }

    @Override
    public TableContext getTableContext() {
        return null;
    }

    @Override
    public ExpressionContext cloneExpressionContext() {
        return null;
    }

    @Override
    public Map<NavigateMetadata,IncludeNavigateExpression> getIncludes() {
        return null;
    }

    @Override
    public boolean hasIncludes() {
        return false;
    }

    @Override
    public List<FillExpression> getFills() {
        return null;
    }

    @Override
    public boolean hasFills() {
        return false;
    }
    @Override
    public Map<TableAvailable, Map<String, ColumnIncludeExpression>> getColumnIncludeMaps() {
        return null;
    }

    @Override
    public boolean hasColumnIncludeMaps() {
        return false;
    }

    @Override
    public void filterConfigure(ValueFilter valueFilter) {

    }

    @Override
    public ValueFilter getValueFilter() {
        return null;
    }

    @Override
    public Consumer<Object> getForEachConfigurer() {
        return null;
    }

    @Override
    public void setForEachConfigurer(Consumer<Object> forEachConfigurer) {

    }

    @Override
    public List<ExpressionBuilder> getDeclareExpressions() {
        return null;
    }

    @Override
    public boolean hasDeclareExpressions() {
        return false;
    }

    @Override
    public void setResultPropTypes(ResultColumnMetadata[] propTypes) {

    }

    @Override
    public ResultColumnMetadata[] getResultPropTypes() {
        return null;
    }

    @Override
    public Map<String, ColumnReader> getResultValueConverterMap(boolean createIfNull) {
        return null;
    }

    @Override
    public void setRelationLogicDelete(Function<Class<?>, Boolean> relationLogicDelete) {

    }

    @Override
    public boolean hasRelationLogicDelete() {
        return false;
    }

    @Override
    public Function<Class<?>, Boolean> getRelationLogicDelete() {
        return null;
    }

    @Override
    public RelationExtraMetadata getRelationExtraMetadata() {
        return null;
    }

    @Override
    public boolean hasRelationExtraMetadata() {
        return false;
    }

    @Override
    public void setGroupSize(Integer groupSize) {

    }

    @Override
    public Integer getGroupSize() {
        return 0;
    }

    @Override
    public void setResultSizeLimit(long resultSizeLimit) {

    }

    @Override
    public long getResultSizeLimit() {
        return 0;
    }

    @Override
    public Boolean getPrintSQL() {
        return null;
    }

    @Override
    public void setPrintSQL(Boolean printSQL) {

    }

    @Override
    public Boolean getPrintNavSQL() {
        return null;
    }

    @Override
    public void setPrintNavSQL(Boolean printSQL) {

    }

    @Override
    public void setConfigureArgument(Object configureArgument) {

    }

    @Override
    public ConfigureArgument getConfigureArgument() {
        return null;
    }

    @Override
    public void setReverseOrder(boolean reverseOrder) {

    }

    @Override
    public boolean isReverseOrder() {
        return false;
    }

    @Override
    public Map<Object, Object> getFlatClassMap() {
        return Collections.emptyMap();
    }
}
