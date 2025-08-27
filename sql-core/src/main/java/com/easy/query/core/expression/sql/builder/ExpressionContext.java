package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.basic.extension.conversion.ColumnReader;
import com.easy.query.core.enums.ContextTypeEnum;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
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

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @FileName: QueryExpressionContext.java
 * @Description: 文件说明
 * create time 2023/3/3 23:05
 * @author xuejiaming
 */
public interface ExpressionContext extends RuntimeContextAvailable {
    ContextTypeEnum getType();

    ExpressionContextInterceptor getExpressionContextInterceptor();

    TreeCTEOption getTreeCTEOption();
    List<TreeDeepItem> getDeepItems();
    void setTreeCTEOption(TreeCTEOption treeCTEOption);
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

    boolean isTreeCTE(NavigateMetadata navigateMetadata);
    void setTreeCTE(NavigateMetadata navigateMetadata);

   default void executeMethod(ExecuteMethodEnum executeMethod){
       executeMethod(executeMethod,false);
   }
    void executeMethod(ExecuteMethodEnum executeMethod,boolean ifUnknown);
    ExecuteMethodEnum getExecuteMethod();

    void setMaxShardingQueryLimit(Integer maxShardingQueryLimit);
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
    void extendFrom(ExpressionContext otherExpressionContext);

    boolean hasSubQuery();
    TableContext getTableContext();
    ExpressionContext cloneExpressionContext();


    //todo inculde expression repeart
    Map<NavigateMetadata,IncludeNavigateExpression> getIncludes();
    boolean hasIncludes();

    Map<TableAvailable, Map<String, ColumnIncludeExpression>> getColumnIncludeMaps();
    boolean hasColumnIncludeMaps();


    List<FillExpression> getFills();
    boolean hasFills();

    void filterConfigure(ValueFilter valueFilter);
    ValueFilter getValueFilter();

    Consumer<Object> getForEachConfigurer();
    void setForEachConfigurer(Consumer<Object> forEachConfigurer);
    default boolean hasForEach(){
        return getForEachConfigurer()!=null;
    }

    List<ExpressionBuilder> getDeclareExpressions();
    boolean hasDeclareExpressions();


    void setResultPropTypes(ResultColumnMetadata[] propTypes);
    ResultColumnMetadata[] getResultPropTypes();

    Map<String, ColumnReader> getResultValueConverterMap(boolean createIfNull);

    void setRelationLogicDelete(Function<Class<?>,Boolean> relationLogicDelete);
    boolean hasRelationLogicDelete();
    Function<Class<?>,Boolean> getRelationLogicDelete();
    RelationExtraMetadata getRelationExtraMetadata();
    boolean hasRelationExtraMetadata();
    void setGroupSize(Integer groupSize);
    Integer getGroupSize();

    void setResultSizeLimit(long resultSizeLimit);
    long getResultSizeLimit();

    Boolean getPrintSQL();
    void setPrintSQL(Boolean printSQL);
    Boolean getPrintNavSQL();
    void setPrintNavSQL(Boolean printSQL);


    void setConfigureArgument(Object configureArgument);
    ConfigureArgument getConfigureArgument();
    void setReverseOrder(boolean reverseOrder);
    boolean isReverseOrder();
    Map<Object,Object> getFlatClassMap();

}
