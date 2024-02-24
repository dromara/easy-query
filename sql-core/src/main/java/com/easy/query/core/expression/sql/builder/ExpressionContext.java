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

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @FileName: QueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:05
 * @author xuejiaming
 */
public interface ExpressionContext {
    QueryRuntimeContext getRuntimeContext();

    String getQuoteName(String value);
    void deleteThrow(boolean ifDeleteThrowException);
    boolean isDeleteThrow();
    EasyBehavior getBehavior();
    void useSQLStrategy(SQLExecuteStrategyEnum sqlStrategy);
    SQLExecuteStrategyEnum getSQLStrategy();
    void setVersion(Object version);
    Object getVersion();
    void useInterceptor(String name);
    void noInterceptor(String name);
    void useInterceptor();
    void noInterceptor();
    Predicate<Interceptor> getInterceptorFilter();

   default void executeMethod(ExecuteMethodEnum executeMethod){
       executeMethod(executeMethod,false);
   }
    void executeMethod(ExecuteMethodEnum executeMethod,boolean ifUnknown);
    ExecuteMethodEnum getExecuteMethod();

    void setMaxShardingQueryLimit(int maxShardingQueryLimit);
    Integer getMaxShardingQueryLimitOrNull();
    void setConnectionMode(ConnectionModeEnum connectionMode);
    ConnectionModeEnum getConnectionModeOrNull();

    /**
     * 设置当前为sharding需要解析
     * 可以对非sharding的表达式进行优化不需要判断
     */
    void useSharding();
    boolean isSharding();
    void extract(ExpressionContext otherExpressionContext);

    boolean hasSubQuery();
    TableContext getTableContext();
    ExpressionContext cloneExpressionContext();


    //todo inculde expression repeart
    List<IncludeNavigateExpression> getIncludes();
    boolean hasIncludes();

    List<FillExpression> getFills();
    boolean hasFills();

    Map<TableAvailable, Map<String, ColumnIncludeExpression>> getColumnIncludeMaps();
    boolean hasColumnIncludeMaps();

    void filterConfigure(ValueFilter valueFilter);
    ValueFilter getValueFilter();

    Consumer<Object> getForEachConfigurer();
    void setForEachConfigurer(Consumer<Object> forEachConfigurer);
    default boolean hasForEach(){
        return getForEachConfigurer()!=null;
    }

    List<ExpressionBuilder> getDeclareExpressions();
    boolean hasDeclareExpressions();


    void setDraftPropTypes(Class<?>[] propTypes);
    Class<?>[] getDraftPropTypes();
}
