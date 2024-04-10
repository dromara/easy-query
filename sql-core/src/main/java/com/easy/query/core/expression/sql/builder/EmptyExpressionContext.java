package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.expression.sql.fill.FillExpression;
import com.easy.query.core.expression.sql.include.ColumnIncludeExpression;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.List;
import java.util.Map;
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
        return null;
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
    public void executeMethod(ExecuteMethodEnum executeMethod, boolean ifUnknown) {

    }

    @Override
    public ExecuteMethodEnum getExecuteMethod() {
        return null;
    }

    @Override
    public void setMaxShardingQueryLimit(int maxShardingQueryLimit) {

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
    public void setResultPropTypes(Class<?>[] propTypes) {

    }

    @Override
    public Class<?>[] getResultPropTypes() {
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
}
