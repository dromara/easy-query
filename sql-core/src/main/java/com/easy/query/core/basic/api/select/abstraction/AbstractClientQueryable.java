package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.basic.api.select.JdbcResultWrap;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.executor.MethodQuery;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.StreamIterable;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.common.IncludeCirculateChecker;
import com.easy.query.core.common.IncludePath;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.include.IncludeProcessor;
import com.easy.query.core.expression.include.IncludeProcessorFactory;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurerImpl;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEOption;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.expression.sql.include.IncludeParserEngine;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.MappingPathTreeBuilder;
import com.easy.query.core.metadata.MappingPathTreeNode;
import com.easy.query.core.metadata.NavigateFlatMetadata;
import com.easy.query.core.metadata.NavigateJoinMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyJdbcExecutorUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author xuejiaming
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 */
public abstract class AbstractClientQueryable<T1> implements ClientQueryable<T1> {
    private static final Log log = LogFactory.getLog(AbstractClientQueryable.class);
    protected final Class<T1> t1Class;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;
    protected SQLExpressionProvider<T1> sqlExpressionProvider1;

    @Override
    public Class<T1> queryClass() {
        return t1Class;
    }

    public AbstractClientQueryable(Class<T1> t1Class, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.t1Class = t1Class;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
        this.sqlSegmentFactory = this.runtimeContext.getSQLSegmentFactory();
    }


    @Override
    public SQLExpressionProvider<T1> getSQLExpressionProvider1() {
        if (sqlExpressionProvider1 == null) {
            sqlExpressionProvider1 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(0, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider1;
    }

    @Override
    public ClientQueryable<T1> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(this);
    }

    private void setExecuteMethod(ExecuteMethodEnum executeMethod) {
        setExecuteMethod(executeMethod, false);
    }

    private void setExecuteMethod(ExecuteMethodEnum executeMethod, boolean ifUnknown) {
        entityQueryExpressionBuilder.getExpressionContext().executeMethod(executeMethod, ifUnknown);
    }

    @Override
    public long count() {
        setExecuteMethod(ExecuteMethodEnum.COUNT);
        EntityQueryExpressionBuilder countQueryExpressionBuilder = createCountQueryExpressionBuilder();
        List<Long> result = toInternalListByExpression(countQueryExpressionBuilder, Long.class, false, null);
        return EasyCollectionUtil.sum(result);
    }

    private EntityQueryExpressionBuilder createCountQueryExpressionBuilder() {
        EntityQueryExpressionBuilder queryExpressionBuilder = entityQueryExpressionBuilder.cloneEntityExpressionBuilder();
        EntityQueryExpressionBuilder countSQLEntityExpressionBuilder = EasySQLExpressionUtil.getCountEntityQueryExpression(queryExpressionBuilder, queryExpressionBuilder.isDistinct());
        if (countSQLEntityExpressionBuilder == null) {
            return cloneQueryable().select("COUNT(*)").getSQLEntityExpressionBuilder();
        }
        return countSQLEntityExpressionBuilder;
    }
//    private EntityQueryExpressionBuilder createSumQueryExpressionBuilder(Class<?> numberClass,String property) {
//        EntityQueryExpressionBuilder queryExpressionBuilder = entityQueryExpressionBuilder.cloneEntityExpressionBuilder();
//        EntityQueryExpressionBuilder countSQLEntityExpressionBuilder = EasySQLExpressionUtil.getCountEntityQueryExpression(queryExpressionBuilder, queryExpressionBuilder.isDistinct());
//        if (countSQLEntityExpressionBuilder == null) {
//            return cloneQueryable().select(numberClass,t->t.sqlFunc(t.fx().sum(property))).getSQLEntityExpressionBuilder();
//        }
//        return countSQLEntityExpressionBuilder;
//    }

    @Override
    public ClientQueryable<Long> selectCount() {
        return this.selectCount(Long.class);
    }

    @Override
    public <TNumber extends Number> ClientQueryable<TNumber> selectCount(Class<TNumber> numberClass) {
        EntityQueryExpressionBuilder countQueryExpressionBuilder = createCountQueryExpressionBuilder();
        if (countQueryExpressionBuilder == null) {
            return cloneQueryable().select(numberClass, o -> o.sqlNativeSegment("COUNT(*)"));
        }
        return new EasyClientQueryable<>(numberClass, countQueryExpressionBuilder);
    }

    @Override
    public <TNumber extends Number> ClientQueryable<TNumber> selectSum(Class<TNumber> numberClass, String property) {
        return cloneQueryable().select(numberClass, o -> o.sqlFunc(o.fx().sum(property)));
    }

    @Override
    public ClientQueryable<BigDecimal> selectAvg(String property) {
        return cloneQueryable().select(BigDecimal.class, o -> o.sqlFunc(o.fx().avg(property)));
    }

    @Override
    public <TMember> ClientQueryable<TMember> selectMax(Class<TMember> memberClass, String property) {
        return cloneQueryable().select(memberClass, o -> o.sqlFunc(o.fx().max(property)));
    }

    @Override
    public <TMember> ClientQueryable<TMember> selectMin(Class<TMember> memberClass, String property) {
        return cloneQueryable().select(memberClass, o -> o.sqlFunc(o.fx().min(property)));
    }

    @Override
    public long countDistinct(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        ProjectSQLBuilderSegmentImpl sqlSegmentBuilder = new ProjectSQLBuilderSegmentImpl();
        ColumnSelector<T1> sqlColumnSelector = getSQLExpressionProvider1().getColumnSelector(sqlSegmentBuilder);
        selectExpression.apply(sqlColumnSelector);

        setExecuteMethod(ExecuteMethodEnum.COUNT_DISTINCT);
        ColumnFunction countFunction = runtimeContext.getColumnFunctionFactory().createCountFunction(true);
        List<Long> result = cloneQueryable().select(countFunction.getFuncColumn(sqlSegmentBuilder.toSQL(null))).toList(Long.class);

        if (result.isEmpty()) {
            return 0L;
        }
        Long r = result.get(0);
        if (r == null) {
            return 0L;
        }
        return r;
    }

    @Override
    public boolean any() {
        setExecuteMethod(ExecuteMethodEnum.ANY);
        List<Long> result = cloneQueryable().limit(1).select(" 1 ").toList(Long.class);
        return !result.isEmpty();
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(String property, BigDecimal def) {
        setExecuteMethod(ExecuteMethodEnum.SUM);
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
        List<TMember> result = selectAggregateList(entityTable, sumFunction, property, null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(String property, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.SUM);
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
        List<TMember> result = selectAggregateList(entityTable, sumFunction, property, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Comparable<?>> TMember maxOrDefault(String property, TMember def) {

        setExecuteMethod(ExecuteMethodEnum.MAX);
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
        List<TMember> result = selectAggregateList(entityTable, maxFunction, property, null);
        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
        TMember tMember = EasyCollectionUtil.firstOrNull(result);
        if (tMember == null) {
            return def;
        }
        ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(property);
        Object value = EasyJdbcExecutorUtil.fromValue(new EntityResultColumnMetadata(0, entityTable.getEntityMetadata(), columnMetadata), tMember);
        if (value == null) {
            return def;
        }
        return EasyObjectUtil.typeCastNullable(value);
    }

    @Override
    public <TMember> TMember minOrDefault(String property, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.MIN);
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
        List<TMember> result = selectAggregateList(entityTable, minFunction, property, null);
        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
        TMember tMember = EasyCollectionUtil.firstOrNull(result);
        if (tMember == null) {
            return def;
        }
        ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(property);
        Object value = EasyJdbcExecutorUtil.fromValue(new EntityResultColumnMetadata(0, entityTable.getEntityMetadata(), columnMetadata), tMember);
        if (value == null) {
            return def;
        }
        return EasyObjectUtil.typeCastNullable(value);
    }


//    @Override
//    public <TMember extends Comparable<?>> TMember maxOrDefault(String property, TMember def) {
//
//        setExecuteMethod(ExecuteMethodEnum.MAX);
////        ColumnFunction maxFunction1 = runtimeContext.getColumnFunctionFactory().createMaxFunction();
//        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
//        ClientQueryable<T1> cloneQueryable = cloneQueryable();
//        cloneQueryable.getSQLEntityExpressionBuilder().getProjects().clear();
//        SQLFunction SQLFunction = runtimeContext.fx().max(property);
//        ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(property);
//        List<TMember> result =  cloneQueryable.select(x->x.sqlFunc(SQLFunction)).toList((Class<TMember>)columnMetadata.getPropertyType());
//        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
//        TMember tMember = EasyCollectionUtil.firstOrNull(result);
//        if(tMember==null){
//            return def;
//        }
//        Object value = EasyJdbcExecutorUtil.fromValue(new EntityResultColumnMetadata(0, entityTable.getEntityMetadata(), columnMetadata), tMember);
//        if(value==null){
//            return def;
//        }
//        return EasyObjectUtil.typeCastNullable(value);
//    }
//
//    @Override
//    public <TMember> TMember minOrDefault(String property, TMember def) {
//        setExecuteMethod(ExecuteMethodEnum.MIN);
////        ColumnFunction maxFunction1 = runtimeContext.getColumnFunctionFactory().createMaxFunction();
//        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
//        ClientQueryable<T1> cloneQueryable = cloneQueryable();
//        cloneQueryable.getSQLEntityExpressionBuilder().getProjects().clear();
//        SQLFunction minSQLFunction = runtimeContext.fx().min(property);
//        ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(property);
//        List<TMember> result =  cloneQueryable.select(x->x.sqlFunc(minSQLFunction)).toList((Class<TMember>)columnMetadata.getPropertyType());
//        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
//        TMember tMember = EasyCollectionUtil.firstOrNull(result);
//        if(tMember==null){
//            return def;
//        }
//        Object value = EasyJdbcExecutorUtil.fromValue(new EntityResultColumnMetadata(0, entityTable.getEntityMetadata(), columnMetadata), tMember);
//        if(value==null){
//            return def;
//        }
//        return EasyObjectUtil.typeCastNullable(value);
//    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(String property, TResult def, Class<TResult> resultClass) {
        setExecuteMethod(ExecuteMethodEnum.AVG);
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);

        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
        List<TResult> result = selectAggregateList(entityTable, avgFunction, property, resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> List<TMember> selectAggregateList(TableAvailable table, ColumnFunction columnFunction, String property, Class<TMember> resultClass) {

        Class<TMember> tMemberClass = resultClass == null ? (Class<TMember>) table.getEntityMetadata().getColumnNotNull(property).getPropertyType() : resultClass;
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, property, entityQueryExpressionBuilder.getExpressionContext(), columnFunction, null);
        return cloneQueryable().select(funcColumnSegment, true).toList(tMemberClass);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        setExecuteMethod(ExecuteMethodEnum.FIRST);
        List<TR> list = limit(1).toList(resultClass);

        return EasyCollectionUtil.firstOrNull(list);
    }

    @NotNull
    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        TR result = firstOrNull(resultClass);
        if (result == null) {
            RuntimeException runtimeException = throwFunc.get();
            assert runtimeException != null;
            throw runtimeException;
        }
        return result;
    }

    @Override
    public T1 findOrNull(Object id) {
        setExecuteMethod(ExecuteMethodEnum.FIND);
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(queryClass());
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if (EasyCollectionUtil.isEmpty(keyProperties)) {
            throw new EasyQueryNoPrimaryKeyException(EasyClassUtil.getSimpleName(queryClass()));
        }
        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryMultiPrimaryKeyException(EasyClassUtil.getSimpleName(queryClass()));
        }
        List<T1> list = whereById(id).toList();

        return EasyCollectionUtil.firstOrNull(list);
    }

    @NotNull
    @Override
    public T1 findNotNull(Object id, Supplier<RuntimeException> throwFunc) {
        T1 result = findOrNull(id);
        if (result == null) {
            RuntimeException runtimeException = throwFunc.get();
            assert runtimeException != null;
            throw runtimeException;
        }
        return result;
    }

    @NotNull
    @Override
    public <TR> TR singleNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc) {
        TR result = singleOrNull(resultClass);
        if (result == null) {
            RuntimeException runtimeException = throwFunc.get();
            assert runtimeException != null;
            throw runtimeException;
        }
        return result;
    }

    @Override
    public <TR> TR singleOrNull(Class<TR> resultClass) {
        setExecuteMethod(ExecuteMethodEnum.SINGLE);
        boolean autoAllColumn = compensateSelect(resultClass);
        boolean printSql = runtimeContext.getQueryConfiguration().getEasyQueryOption().isPrintSql();
        JdbcResultWrap<TR> jdbcResultWrap = toInternalStreamByExpression(entityQueryExpressionBuilder, resultClass, autoAllColumn, statement -> {
            statement.setFetchSize(2);
        });
        TR next = null;
        try (JdbcStreamResult<TR> jdbcStreamResult = jdbcResultWrap.getJdbcResult().getJdbcStreamResult()) {
            StreamIterable<TR> streamResult = jdbcStreamResult.getStreamIterable();

            Iterator<TR> iterator = streamResult.iterator();
            boolean firstHasNext = iterator.hasNext();
            if (!firstHasNext) {
                if (printSql) {
                    log.info("<== Total: 0");
                }
                return null;
            }
            int i = 0;
            do {
//                if(next!=null){ //因为存在查询单个属性单个属性可能为null
//                    throw new EasyQuerySingleMoreElementException("single query has more element in result set.");
//                }
                if (i >= 1) {
                    throw runtimeContext.getAssertExceptionFactory().createSingleMoreElementException(this);
                }
                next = iterator.next();
                i++;
            } while (iterator.hasNext());
            if (printSql) {
                log.info("<== Total: 1");
            }

        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
        if (next != null) {
            ExpressionContext expressionContext = jdbcResultWrap.getExpressionContext();
            EntityMetadata entityMetadata = jdbcResultWrap.getEntityMetadata();
            doIncludes(expressionContext, entityMetadata, Collections.singletonList(next));
        }
        //将当前方法设置为unknown
        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);

        return next;
    }

    @Override
    public Map<String, Object> toMap() {
        limit(1);
        List<Map<String, Object>> maps = toMaps();
        return EasyCollectionUtil.firstOrNull(maps);
    }

    @Override
    public List<Map<String, Object>> toMaps() {
        List<Map> queryMaps = toQueryMaps();
        return EasyObjectUtil.typeCastNullable(queryMaps);
    }

    private List<Map> toQueryMaps() {
        setExecuteMethod(ExecuteMethodEnum.LIST);
        if (EasySQLExpressionUtil.shouldCloneSQLEntityQueryExpressionBuilder(entityQueryExpressionBuilder)) {
            return select(queryClass()).toList(Map.class);
        }
        return toList(Map.class);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        setExecuteMethod(ExecuteMethodEnum.LIST, true);
        return toInternalList(resultClass);
    }

    @Override
    public <TR> JdbcStreamResult<TR> toStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        setExecuteMethod(ExecuteMethodEnum.StreamResult, true);
        return toInternalStreamResult(resultClass, configurer);
    }

    /**
     * 补齐select操作
     *
     * @param resultClass
     * @return
     */
    protected boolean compensateSelect(Class<?> resultClass) {

        if (EasySQLExpressionUtil.shouldCloneSQLEntityQueryExpressionBuilder(entityQueryExpressionBuilder)) {
            selectOnly(resultClass);
            return Objects.equals(queryClass(), resultClass) && !entityQueryExpressionBuilder.getTable(0).getEntityTable().isAnonymous();
        } else {
            ExpressionContext expressionContext = entityQueryExpressionBuilder.getExpressionContext();
            if (expressionContext.hasIncludes()) {
                ColumnAsSelector<T1, ?> sqlColumnSelector = getSQLExpressionProvider1().getAutoColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
                EasySQLExpressionUtil.appendSelfExtraTargetProperty(entityQueryExpressionBuilder, sqlColumnSelector.getSQLNative(), sqlColumnSelector.getTable());
            }
        }
        return false;
    }

    @Override
    public <TR> String toSQL(Class<TR> resultClass, ToSQLContext toSQLContext) {
        compensateSelect(resultClass);
        return entityQueryExpressionBuilder.toExpression().toSQL(toSQLContext);
    }

    /**
     * 子类实现方法
     *
     * @return
     */
    protected <TR> List<TR> toInternalList(Class<TR> resultClass) {
        boolean autoAllColumn = compensateSelect(resultClass);
        return toInternalListByExpression(entityQueryExpressionBuilder, resultClass, autoAllColumn, null);
    }

    protected <TR> JdbcStreamResult<TR> toInternalStreamResult(Class<TR> resultClass, SQLConsumer<Statement> configurer) {
        boolean autoAllColumn = compensateSelect(resultClass);
        JdbcResultWrap<TR> jdbcResultWrap = toInternalStreamByExpression(entityQueryExpressionBuilder, resultClass, autoAllColumn, configurer);
        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
        return jdbcResultWrap.getJdbcResult().getJdbcStreamResult();
    }

    protected <TR> List<TR> toInternalListByExpression(EntityQueryExpressionBuilder entityQueryExpressionBuilder, Class<TR> resultClass, boolean autoAllColumn, SQLConsumer<Statement> configurer) {

        JdbcResultWrap<TR> jdbcResultWrap = toInternalStreamByExpression(entityQueryExpressionBuilder, resultClass, autoAllColumn, configurer);
        List<TR> result = jdbcResultWrap.getJdbcResult().toList();
        ExecuteMethodEnum executeMethod = jdbcResultWrap.getExecuteMethod();
        ExpressionContext expressionContext = jdbcResultWrap.getExpressionContext();
        EntityMetadata entityMetadata = jdbcResultWrap.getEntityMetadata();
        if (ExecuteMethodEnum.LIST == executeMethod || ExecuteMethodEnum.FIRST == executeMethod || ExecuteMethodEnum.SINGLE == executeMethod || ExecuteMethodEnum.FIND == executeMethod) {
            if (EasyCollectionUtil.isNotEmpty(result)) {
                doIncludes(expressionContext, entityMetadata, result);
            }
        }
        //将当前方法设置为unknown
        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
        return result;
    }

    /**
     * 获取流式结果
     *
     * @param entityQueryExpressionBuilder
     * @param resultClass
     * @param autoAllColumn
     * @param <TR>
     * @return
     */
    protected <TR> JdbcResultWrap<TR> toInternalStreamByExpression(EntityQueryExpressionBuilder entityQueryExpressionBuilder, Class<TR> resultClass, boolean autoAllColumn, SQLConsumer<Statement> configurer) {
        ExpressionContext expressionContext = this.entityQueryExpressionBuilder.getExpressionContext();
        boolean tracking = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
        ExecuteMethodEnum executeMethod = expressionContext.getExecuteMethod();
        EntityExpressionExecutor entityExpressionExecutor = this.entityQueryExpressionBuilder.getRuntimeContext().getEntityExpressionExecutor();
        EntityMetadata entityMetadata = this.entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
        ExecutorContext executorContext = ExecutorContext.create(expressionContext, true, executeMethod, tracking);
        executorContext.setConfigurer(configurer);
        // todo data reader
        DataReader dataReader = autoAllColumn && expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN) ? entityMetadata.getDataReader() : null;
        JdbcResult<TR> jdbcResult = entityExpressionExecutor.queryStreamResultSet(executorContext, new EntityResultMetadata<>(entityMetadata, dataReader), entityQueryExpressionBuilder);
        return new JdbcResultWrap<>(executeMethod, expressionContext, entityMetadata, jdbcResult);
    }

    @Override
    public <TR> TR streamBy(Function<Stream<T1>, TR> fetcher, SQLConsumer<Statement> configurer) {
        ExpressionContext expressionContext = this.entityQueryExpressionBuilder.getExpressionContext();
        //为了支持streamBy下的include处理必须要进行stream的二次迭代
        if (expressionContext.hasIncludes()) {
            List<T1> result = this.toList();
            return fetcher.apply(result.stream());
        } else {
            try (JdbcStreamResult<T1> streamResult = toStreamResult(configurer)) {
                StreamIterable<T1> streamIterable = streamResult.getStreamIterable();
                Stream<T1> stream = StreamSupport.stream(streamIterable.spliterator(), false);
                return fetcher.apply(stream);
            } catch (SQLException sqlException) {
                throw new EasyQuerySQLCommandException(sqlException);
            }
        }
    }

    private <TR> void doIncludes(ExpressionContext expressionContext, EntityMetadata entityMetadata, List<TR> result) {

        if (expressionContext.hasIncludes()) {
            IncludeProcessorFactory includeProcessorFactory = runtimeContext.getIncludeProcessorFactory();
            IncludeParserEngine includeParserEngine = runtimeContext.getIncludeParserEngine();
            for (IncludeNavigateExpression include : expressionContext.getIncludes().values()) {

                IncludeParserResult includeParserResult = includeParserEngine.process(this.entityQueryExpressionBuilder.getExpressionContext(), entityMetadata, result, include);

                IncludeProcessor includeProcess = includeProcessorFactory.createIncludeProcess(includeParserResult, runtimeContext);
                includeProcess.process();
            }
        }
    }


    /**
     * 只有select操作运行操作当前projects
     *
     * @param selectExpression
     * @return
     */
    @Override
    public ClientQueryable<T1> select(SQLExpression1<ColumnSelector<T1>> selectExpression) {
        ColumnSelector<T1> sqlColumnSelector = getSQLExpressionProvider1().getColumnSelector(entityQueryExpressionBuilder.getProjects());
        selectExpression.apply(sqlColumnSelector);
        processorIncludeRelationProperty(sqlColumnSelector.getSQLNative(), sqlColumnSelector.getTable());
        if (EasyCollectionUtil.isSingle(entityQueryExpressionBuilder.getTables())) {
            return this;
        }
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(queryClass(), entityQueryExpressionBuilder);
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression1<ColumnAsSelector<T1, TR>> selectExpression) {
        ColumnAsSelector<T1, TR> sqlColumnSelector = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        if (EasySQLSegmentUtil.isEmpty(entityQueryExpressionBuilder.getProjects())) {
            SQLExpression1<ColumnAsSelector<T1, TR>> selectAllExpression = ColumnAsSelector::columnAll;
            selectAllExpression.apply(sqlColumnSelector);
        } else {
            processorIncludeRelationProperty(sqlColumnSelector.getSQLNative(), sqlColumnSelector.getTable());
        }
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    /**
     * 如果存在include那么就需要对select的结果进行补齐关联id
     *
     * @param sqlNative
     * @param table
     */
    private void processorIncludeRelationProperty(SQLNative<?> sqlNative, TableAvailable table) {
        EasySQLExpressionUtil.appendSelfExtraTargetProperty(getSQLEntityExpressionBuilder(), sqlNative, table);
    }

    @Override
    public ClientQueryable<T1> select(String columns) {
        entityQueryExpressionBuilder.getProjects().getSQLSegments().clear();
        SelectConstSegment selectConstSegment = sqlSegmentFactory.createSelectConstSegment(columns);
        entityQueryExpressionBuilder.getProjects().append(selectConstSegment);
        return this;
    }

    @Override
    public ClientQueryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll) {
        if (clearAll) {
            entityQueryExpressionBuilder.getProjects().getSQLSegments().clear();
        }
        for (ColumnSegment columnSegment : columnSegments) {
            entityQueryExpressionBuilder.getProjects().append(columnSegment);
        }
        return this;
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass) {
        selectOnly(resultClass);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

//    @Override
//    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, boolean replace) {
//        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
//        EntityMetadata resultEntityMetadata = entityMetadataManager.getEntityMetadata(resultClass);
//        EntityTableExpressionBuilder table = getSQLEntityExpressionBuilder().getTable(0);
//        TableAvailable entityTable = table.getEntityTable();
//        EntityMetadata entityMetadata = entityTable.getEntityMetadata();
//        selectAutoInclude0(entityMetadataManager, this, entityMetadata, resultEntityMetadata, null, replace);
//
//        return select(resultClass);
//    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLExpression1<ColumnAsSelector<T1, TR>> selectExpression, boolean replace) {
        selectAutoInclude0(resultClass, replace);
        if (selectExpression != null) {
            ColumnAsSelector<T1, TR> sqlColumnSelector = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            selectExpression.apply(sqlColumnSelector);
        }
        return select(resultClass);
    }

    protected <TR> void selectAutoInclude0(Class<TR> resultClass, boolean replace) {
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        EntityMetadata resultEntityMetadata = entityMetadataManager.getEntityMetadata(resultClass);
        EntityTableExpressionBuilder table = getSQLEntityExpressionBuilder().getTable(0);
        TableAvailable entityTable = table.getEntityTable();
        EntityMetadata entityMetadata = entityTable.getEntityMetadata();
        selectAutoInclude0(entityMetadataManager, this, entityMetadata, resultEntityMetadata, null, replace, 0);
        selectAutoIncludeFlat0(entityMetadataManager, this, entityMetadata, resultEntityMetadata);
        selectAutoIncludeJoin0(resultClass, this, resultEntityMetadata);
    }

    private void selectAutoIncludeFlat0(EntityMetadataManager entityMetadataManager, ClientQueryable<?> clientQueryable, EntityMetadata entityMetadata, EntityMetadata resultEntityMetadata) {
        Collection<NavigateFlatMetadata> navigateFlatMetadatas = resultEntityMetadata.getNavigateFlatMetadatas();
        if (EasyCollectionUtil.isNotEmpty(navigateFlatMetadatas)) {
            MappingPathTreeNode mappingPathTreeRoot = getMappingPathTree(navigateFlatMetadatas, resultEntityMetadata);
            selectAutoIncludeFlat0(entityMetadataManager, clientQueryable, entityMetadata, mappingPathTreeRoot, true);
//            for (MappingPathTreeNode mappingPathTree : mappingPathTreeRoot.getChildren()) {
//                
//            }
        }
    }

    private <TR> void selectAutoIncludeJoin0(Class<TR> resultClass, ClientQueryable<?> clientQueryable, EntityMetadata resultEntityMetadata) {
        Collection<NavigateJoinMetadata> navigateJoinMetadatas = resultEntityMetadata.getNavigateJoinMetadatas();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = clientQueryable.getSQLEntityExpressionBuilder();
        if (EasyCollectionUtil.isNotEmpty(navigateJoinMetadatas)) {
            AsSelector asSelector = new AsSelectorImpl(sqlEntityExpressionBuilder, sqlEntityExpressionBuilder.getProjects(), resultClass);
            for (NavigateJoinMetadata navigateJoinMetadata : navigateJoinMetadatas) {
                TableAvailable relationTable = null;
                if (navigateJoinMetadata.getMappingPath().length < 2) {
                    throw new EasyQueryInvalidOperationException("navigate join mapping length < 2");
                }
                for (int i = 0; i < navigateJoinMetadata.getMappingPath().length - 1; i++) {
                    String navigateEntityProperty = navigateJoinMetadata.getMappingPath()[i];
                    relationTable = EasyRelationalUtil.getRelationTable(sqlEntityExpressionBuilder, sqlEntityExpressionBuilder.getTable(0).getEntityTable(), navigateEntityProperty);
                }
                String navigateBasicTypeProperty = navigateJoinMetadata.getMappingPath()[navigateJoinMetadata.getMappingPath().length - 1];
                asSelector.columnAs(relationTable, navigateBasicTypeProperty, navigateJoinMetadata.getProperty());
            }
        }
    }

    private MappingPathTreeNode getMappingPathTree(Collection<NavigateFlatMetadata> navigateFlatMetadataCollection, EntityMetadata navigateEntityMetadata) {
        MappingPathTreeNode root = new MappingPathTreeNode("EASY-QUERY-ROOT");
        for (NavigateFlatMetadata navigateFlatMetadata : navigateFlatMetadataCollection) {
            String[] mappingPath = navigateFlatMetadata.getMappingPath();
            MappingPathTreeBuilder.insertPath(root, mappingPath, navigateFlatMetadata, path -> {
                NavigateMetadata navigateOrNull = navigateEntityMetadata.getNavigateOrNull(path);
                if (navigateOrNull != null) {
                    throw new EasyQueryInvalidOperationException(String.format("In the selectAutoInclude query, the relational propoerty [%s] of the class [%s] should appear in both @Navigate and @NavigateFlat.", path, EasyClassUtil.getSimpleName(navigateEntityMetadata.getEntityClass())));
                }
            });
        }
        return root;
//        return EasyUtil.groupBy(navigateFlatMetadataCollection.stream(), o -> o.getMappingPath()[0])
//                .map(o -> {
//                    return new MappingPathIterator(o.values());
//                }).collect(Collectors.toList());

    }

    private void selectAutoInclude0(EntityMetadataManager entityMetadataManager, ClientQueryable<?> clientQueryable, EntityMetadata entityMetadata, EntityMetadata resultEntityMetadata, IncludeCirculateChecker includeCirculateChecker, boolean replace, int deep) {
        Collection<NavigateMetadata> resultNavigateMetadatas = resultEntityMetadata.getNavigateMetadatas();
        if (EasyCollectionUtil.isEmpty(resultNavigateMetadatas)) {
            return;
        }

        for (NavigateMetadata resultNavigateMetadata : resultNavigateMetadatas) {
            NavigateMetadata entityNavigateMetadata = entityMetadata.getNavigateOrNull(resultNavigateMetadata.getPropertyName());
            if (entityNavigateMetadata == null) {
                continue;
            }
            //循环引用检查
            IncludeCirculateChecker circulateChecker = includeCirculateChecker == null ? new IncludeCirculateChecker() : includeCirculateChecker;
            if (circulateChecker.includePathRepeat(new IncludePath(entityNavigateMetadata.getNavigatePropertyType(), resultNavigateMetadata.getNavigatePropertyType(), resultNavigateMetadata.getPropertyName(), deep))) {
                continue;
            }
//            String navigatePropName = resultNavigateMetadata.isBasicType() ? resultNavigateMetadata.getMappingProp().split("//.")[0] : resultNavigateMetadata.getPropertyName();

            clientQueryable
                    .include(t -> {
                        t.getIncludeNavigateParams().setReplace(replace);
//                        ClientQueryable<Object> with = t.with(navigatePropName);
                        ClientQueryable<Object> with = t.with(resultNavigateMetadata.getPropertyName());
                        EntityMetadata entityEntityMetadata = entityMetadataManager.getEntityMetadata(entityNavigateMetadata.getNavigatePropertyType());
                        EntityMetadata navigateEntityMetadata = entityMetadataManager.getEntityMetadata(resultNavigateMetadata.getNavigatePropertyType());
                        selectAutoInclude0(entityMetadataManager, with, entityEntityMetadata, navigateEntityMetadata, circulateChecker, replace, deep + 1);
                        selectAutoIncludeFlat0(entityMetadataManager, with, entityEntityMetadata, navigateEntityMetadata);
//                        selectAutoIncludeJoin0(with.queryClass(), with, navigateEntityMetadata);
                        return with;
                    });
        }
    }

    private void selectAutoIncludeFlat0(EntityMetadataManager entityMetadataManager, ClientQueryable<?> clientQueryable, EntityMetadata entityMetadata, MappingPathTreeNode mappingPathTree, boolean first) {
        if (!mappingPathTree.hasChildren()) {
            return;
        }
        for (MappingPathTreeNode mappingPathTreeChild : mappingPathTree.getChildren()) {
            String propertyName = mappingPathTreeChild.getName();
            NavigateMetadata entityNavigateMetadata = entityMetadata.getNavigateOrNull(propertyName);
            //既不是导航属性也不是column属性就抛错
            if (entityNavigateMetadata == null) {
//                NavigateFlatProperty navigateFlatProperty = EasyCollectionUtil.firstOrNull(navigateFlatGroupProperty.values());
//                if (navigateFlatProperty != null) {
//
//                    if (navigateFlatProperty.getNavigateFlatMetadata().isBasicType()) {
//                        ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(propertyName);
//                        if (columnMetadata != null) {
//                            continue;
//                        }
//                    }
//                }
                ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(propertyName);
                if (columnMetadata != null) {
                    continue;
                }
                String warningMessage = String.format("NavigateFlat:%s not found navigate property:[%s]", EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()), propertyName);
                throw new EasyQueryInvalidOperationException(warningMessage);
//
//                if (log instanceof NoLoggingImpl) {
//                    System.out.println("NoLogging:" + warningMessage);
//                } else {
//                    log.warn(warningMessage);
//                }
            }
//            List<NavigateFlatProperty> flatProperties = navigateFlatGroupProperty.values().collect(Collectors.toList());
            //获取当前属性没有后续的属性了的 path最后一个是navigate那么就不可以是自定义dto只能是table
            boolean basicType = mappingPathTreeChild.getNavigateFlatMetadataList().stream().anyMatch(o -> !o.isBasicType() && o.getMappingPath().length == mappingPathTreeChild.getDeep());
//            List<MappingPathTreeNode> customPathTypes = mappingPathTreeChild.getChildren().stream().filter(o -> !o.getNavigateFlatMetadata().isBasicType() && !o.hasChildren()).collect(Collectors.toList());
//            String navigatePropName = resultNavigateMetadata.isBasicType() ? resultNavigateMetadata.getMappingProp().split("//.")[0] : resultNavigateMetadata.getPropertyName();

//            List<NavigateFlatProperty> navigateFlatBasicProps = mappingPathIterator.nextRest().stream().filter(i -> i.getNavigateFlatMetadata().isBasicType() && i.getNextCount() == 0).collect(Collectors.toList());

            //获取下一次的基本属性
            int size = mappingPathTreeChild.getNavigateFlatMetadataList().size();
            List<MappingPathTreeNode> navigateFlatBasicProps = mappingPathTreeChild.getChildren().stream().filter(o -> {
                return o.anyBasicType() && !o.hasChildren();
            }).collect(Collectors.toList());
            boolean allChildrenIsProps = navigateFlatBasicProps.size() == size;


            clientQueryable
                    .include(t -> {
                        if (first) {
                            t.getIncludeNavigateParams().setMappingFlat(true);
                            t.getIncludeNavigateParams().setNavigateFlatMetadataList(mappingPathTreeChild.getNavigateFlatMetadataList());
                            t.getIncludeNavigateParams().setFlatQueryEntityMetadata(entityMetadata);
                        }
//                        ClientQueryable<Object> with = t.with(navigatePropName);
                        ClientQueryable<?> with = t.with(propertyName);
                        EntityMetadata entityEntityMetadata = entityMetadataManager.getEntityMetadata(entityNavigateMetadata.getNavigatePropertyType());

//                        if(!entityEntityMetadata.getEntityClass().equals(entityNavigateMetadata.getNavigatePropertyType())){
//                            throw new EasyQueryInvalidOperationException("NavigateFlat 仅支持基本类型和数据库类型");
//                        }
                        selectAutoIncludeFlat0(entityMetadataManager, with, entityEntityMetadata, mappingPathTreeChild, false);

                        if (basicType && !allChildrenIsProps) {
                            //检查是否存在自定义dto
                            List<NavigateFlatMetadata> navigateFlatMetadataList = mappingPathTreeChild.getNavigateFlatMetadataList().stream().filter(o -> !o.isBasicType() && o.getMappingPath().length == mappingPathTreeChild.getDeep() && Objects.equals(propertyName, o.getMappingPath()[o.getMappingPath().length - 1])).collect(Collectors.toList());
                            if (EasyCollectionUtil.isNotEmpty(navigateFlatMetadataList)) {
                                if (EasyCollectionUtil.isNotSingle(navigateFlatMetadataList)) {
                                    throw new EasyQueryInvalidOperationException(String.format("In the class [%s], @NavigateFlat only allows one associated attribute: [%s] to exist.", EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()), propertyName));
                                }
                                NavigateFlatMetadata navigateFlatMetadata = EasyCollectionUtil.first(navigateFlatMetadataList);

                                Class<?> navigatePropertyType = navigateFlatMetadata.getNavigatePropertyType();
                                //表示VO对象并不是最终的对象
                                if (mappingPathTreeChild.hasChildren()) {
                                    if (EasyCollectionUtil.isNotEmpty(navigateFlatBasicProps) && !Objects.equals(with.queryClass(), navigatePropertyType)) {
                                        //如果存在Flat一个数据库VO那么就不可以在对VO所属的对象路径进行基本类型的获取
                                        //Flat [roles,menus] 那么就不可以Flat [roles,menus,id]
                                        throw new EasyQueryInvalidOperationException(String.format("@NavigateFlat cannot simultaneously include non-database related objects: [%s] and its object properties.", EasyClassUtil.getSimpleName(navigatePropertyType)));
                                    }
                                }
                                t.getIncludeNavigateParams().setFlatClassType(navigatePropertyType);
                            }
                        } else {
                            if (EasyCollectionUtil.isNotEmpty(navigateFlatBasicProps)) {
                                EntityQueryExpressionBuilder sqlEntityExpressionBuilder = with.getSQLEntityExpressionBuilder();
                                with = with.select(z -> {
                                    for (MappingPathTreeNode navigateFlatBasicProp : navigateFlatBasicProps) {
                                        z.column(navigateFlatBasicProp.getName());
                                    }
                                    EasySQLExpressionUtil.appendTargetExtraTargetProperty(entityNavigateMetadata, sqlEntityExpressionBuilder, z.getSQLNative(), z.getTable());
                                });
                            } else {
                                if (mappingPathTreeChild.hasChildren()) {
                                    EntityQueryExpressionBuilder sqlEntityExpressionBuilder = with.getSQLEntityExpressionBuilder();
                                    with = with.select(z -> {
//                                        z.column(entityNavigateMetadata.getSelfPropertyOrPrimary());
                                        EasySQLExpressionUtil.appendSelfExtraTargetProperty(sqlEntityExpressionBuilder, z.getSQLNative(), z.getTable());
                                        EasySQLExpressionUtil.appendTargetExtraTargetProperty(entityNavigateMetadata, sqlEntityExpressionBuilder, z.getSQLNative(), z.getTable());
                                    });
                                }
                            }
                        }
                        return with;
                    });
        }
    }

    private <TR> void selectOnly(Class<TR> resultClass) {
        SQLExpression1<ColumnAsSelector<T1, TR>> selectExpression = ColumnAsSelector::columnAll;
        ColumnAsSelector<T1, TR> sqlColumnSelector = getSQLExpressionProvider1().getAutoColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        //如果存在include那么需要对当前结果进行查询
        //todo include
        EasySQLExpressionUtil.appendSelfExtraTargetProperty(entityQueryExpressionBuilder, sqlColumnSelector.getSQLNative(), sqlColumnSelector.getTable());
    }

    @Override
    public ClientQueryable<T1> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        if (condition) {
            FilterContext whereFilterContext = getSQLExpressionProvider1().getWhereFilterContext();
            WherePredicate<T1> sqlPredicate = getSQLExpressionProvider1().getWherePredicate(whereFilterContext);
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> whereById(boolean condition, Object id) {
        if (condition) {

            PredicateSegment where = entityQueryExpressionBuilder.getWhere();
            TableAvailable table = entityQueryExpressionBuilder.getTable(0).getEntityTable();
            String keyProperty = EasySQLExpressionUtil.getSingleKeyPropertyName(table);
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(table, table.getEntityMetadata().getColumnNotNull(keyProperty), id, SQLPredicateCompareEnum.EQ, entityQueryExpressionBuilder.getExpressionContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    @Override
    public <TProperty> ClientQueryable<T1> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            PredicateSegment where = entityQueryExpressionBuilder.getWhere();
            TableAvailable table = entityQueryExpressionBuilder.getTable(0).getEntityTable();
            String keyProperty = EasySQLExpressionUtil.getSingleKeyPropertyName(table);
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnCollectionPredicate(table, keyProperty, ids, SQLPredicateCompareEnum.IN, entityQueryExpressionBuilder.getExpressionContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> whereObject(boolean condition, Object object) {
        if (condition) {
            Objects.requireNonNull(object, "where object params can not be null");
            entityQueryExpressionBuilder.getRuntimeContext()
                    .getWhereObjectQueryExecutor().whereObject(object, entityQueryExpressionBuilder);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> groupBy(boolean condition, SQLExpression1<ColumnGroupSelector<T1>> selectExpression) {
        if (condition) {
            ColumnGroupSelector<T1> sqlPredicate = getSQLExpressionProvider1().getGroupColumnSelector();
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {

        if (condition) {
            WhereAggregatePredicate<T1> sqlAggregatePredicate = getSQLExpressionProvider1().getAggregatePredicate();
            predicateExpression.apply(sqlAggregatePredicate);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> orderBy(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression, boolean asc) {
        if (condition) {
            ColumnOrderSelector<T1> sqlPredicate = getSQLExpressionProvider1().getOrderColumnSelector(asc);
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> orderByObject(boolean condition, ObjectSort objectSort) {

        if (condition) {
            Objects.requireNonNull(objectSort, "order by object param object sort can not be null");
            ObjectSortQueryExecutor objectSortQueryExecutor = entityQueryExpressionBuilder.getRuntimeContext().getObjectSortQueryExecutor();
            objectSortQueryExecutor.orderByObject(objectSort, entityQueryExpressionBuilder);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> limit(boolean condition, long offset, long rows) {
        if (condition) {
            doWithOutAnonymousAndClearExpression(entityQueryExpressionBuilder, exp -> {
                exp.setOffset(offset);
                exp.setRows(rows);
            });
        }
        return this;
    }

    private void doWithOutAnonymousAndClearExpression(EntityQueryExpressionBuilder sqlEntityExpression, Consumer<EntityQueryExpressionBuilder> consumer) {

        //如果当前只有一张表并且是匿名表,那么limit直接处理当前的匿名表的表达式
        if (EasySQLExpressionUtil.limitAndOrderNotSetCurrent(sqlEntityExpression)) {
            AnonymousEntityTableExpressionBuilder anonymousEntityTableExpression = (AnonymousEntityTableExpressionBuilder) sqlEntityExpression.getTable(0);
            if (EasySQLExpressionUtil.onlyNativeSqlExpression(anonymousEntityTableExpression.getEntityQueryExpressionBuilder())) {
                consumer.accept(sqlEntityExpression);
            } else {
                doWithOutAnonymousAndClearExpression(anonymousEntityTableExpression.getEntityQueryExpressionBuilder(), consumer);
            }
        } else {
            consumer.accept(sqlEntityExpression);
        }
    }

    @Override
    public ClientQueryable<T1> distinct(boolean condition) {
        if (condition) {
            doWithOutAnonymousAndClearExpression(entityQueryExpressionBuilder, exp -> {
                exp.setDistinct(true);
            });
        }
        return this;
    }

    @Override
    public <TResult> EasyPageResult<TResult> toPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, long pageTotal) {
        return doPageResult(pageIndex, pageSize, tResultClass, pageTotal);
    }

    protected <TR> EasyPageResult<TR> doPageResult(long pageIndex, long pageSize, Class<TR> clazz, long pageTotal) {
        //设置每次获取多少条
        long take = pageSize <= 0 ? 1 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        long total = pageTotal < 0 ? this.count() : pageTotal;

        EasyPageResultProvider easyPageResultProvider = runtimeContext.getEasyPageResultProvider();
        if (total <= offset) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList());
        }//获取剩余条数
        long remainingCount = total - offset;
        //当剩余条数小于take数就取remainingCount
        long realTake = Math.min(remainingCount, take);
        if (realTake <= 0) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList());
        }
        boolean enableReverseOrder = runtimeContext.getQueryConfiguration().getEasyQueryOption().enableReverseOrder(offset);
        OrderBySQLBuilderSegment order = entityQueryExpressionBuilder.getOrder();
        //反排序 当偏移量大于1/2 总量时 (优化深分页)
        boolean reverseOrder = enableReverseOrder && offset > (total / 2) && EasySQLSegmentUtil.isNotEmpty(order) && entityQueryExpressionBuilder.getOrder().reverseOrder();

        List<TR> data;
        if (reverseOrder) {
            this.limit(total - offset - realTake, realTake);
            data = this.toList(clazz);
            Collections.reverse(data);

        } else {
            this.limit(offset, realTake);
            data = this.toList(clazz);
        }
        return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, data);
    }

    @Override
    public <TResult> EasyPageResult<TResult> toShardingPageResult(Class<TResult> tResultClass, long pageIndex, long pageSize, List<Long> totalLines) {
        return doShardingPageResult(pageIndex, pageSize, tResultClass, totalLines);
    }

    protected <TR> EasyPageResult<TR> doShardingPageResult(long pageIndex, long pageSize, Class<TR> clazz, List<Long> totalLines) {
        //设置每次获取多少条
        long take = pageSize <= 0 ? 1 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        QueryRuntimeContext runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
        ShardingQueryCountManager shardingQueryCountManager = runtimeContext.getShardingQueryCountManager();
        try {
            shardingQueryCountManager.begin();
            boolean totalLineNotEmpty = EasyCollectionUtil.isNotEmpty(totalLines);
            if (totalLineNotEmpty) {
                for (Long totalLine : totalLines) {
                    shardingQueryCountManager.addCountResult(totalLine, true);
                }
            }
            long total = this.count();
            if (totalLineNotEmpty) {
                total = EasyCollectionUtil.sumLong(shardingQueryCountManager.getCountResult(), o -> o);
            }
            EasyPageResultProvider easyPageResultProvider = runtimeContext.getEasyPageResultProvider();
            if (total <= offset) {
                return easyPageResultProvider.createShardingPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList(), shardingQueryCountManager.getSequenceCountLine());
            }
            //获取剩余条数
            long remainingCount = total - offset;
            //当剩余条数小于take数就取remainingCount
            long realTake = Math.min(remainingCount, take);
            if (realTake <= 0) {
                return easyPageResultProvider.createShardingPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList(), shardingQueryCountManager.getSequenceCountLine());
            }
            this.limit(offset, realTake);
            List<TR> data = this.toList(clazz);
            return easyPageResultProvider.createShardingPageResult(pageIndex, pageSize, total, data, shardingQueryCountManager.getSequenceCountLine());
        } finally {
            shardingQueryCountManager.clear();
        }
    }


    @Override
    public EntityQueryExpressionBuilder getSQLEntityExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    @Override
    public <T2> ClientQueryable2<T1, T2> from(Class<T2> from2Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable2(t1Class, from2Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3> ClientQueryable3<T1, T2, T3> from(Class<T2> from2Class, Class<T3> from3Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable3(t1Class, from2Class, from3Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable4(t1Class, from2Class, from3Class, from4Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable5(t1Class, from2Class, from3Class, from4Class, from5Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable6(t1Class, from2Class, from3Class, from4Class, from5Class, from6Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3, T4, T5, T6, T7> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable7(t1Class, from2Class, from3Class, from4Class, from5Class, from6Class, from7Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3, T4, T5, T6, T7, T8> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable8(t1Class, from2Class, from3Class, from4Class, from5Class, from6Class, from7Class, from8Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3, T4, T5, T6, T7, T8, T9> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class, Class<T9> from9Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Class, from2Class, from3Class, from4Class, from5Class, from6Class, from7Class, from8Class, from9Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2, T3, T4, T5, T6, T7, T8, T9, T10> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class, Class<T9> from9Class, Class<T10> from10Class) {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable10(t1Class, from2Class, from3Class, from4Class, from5Class, from6Class, from7Class, from8Class, from9Class, from10Class, MultiTableTypeEnum.DTO, entityQueryExpressionBuilder);
    }

    @Override
    public <T2> ClientQueryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> ClientQueryable2<T1, T2> leftJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        ClientQueryable<T2> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> ClientQueryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> ClientQueryable2<T1, T2> rightJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        ClientQueryable<T2> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> ClientQueryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        ClientQueryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> ClientQueryable2<T1, T2> innerJoin(ClientQueryable<T2> joinQueryable, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> on) {
        //todo 需要判断当前的表达式是否存在where group order之类的操作,是否是一个clear expression如果不是那么就需要先select all如果没有select过然后创建一个anonymous的table去join
        //简单理解就是queryable需要支持join操作 还有queryable 和queryable之间如何join

        ClientQueryable<T2> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public ClientQueryable<T1> union(Collection<ClientQueryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        return internalUnion(unionQueries, SQLUnionEnum.UNION);
    }

    @Override
    public ClientQueryable<T1> unionAll(Collection<ClientQueryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        return internalUnion(unionQueries, SQLUnionEnum.UNION_ALL);
    }

    protected ClientQueryable<T1> internalUnion(Collection<ClientQueryable<T1>> unionQueries, SQLUnionEnum sqlUnion) {

        List<ClientQueryable<T1>> selectUnionQueries = new ArrayList<>(unionQueries.size() + 1);
        ClientQueryable<T1> myQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(this);
        selectUnionQueries.add(myQueryable);
        for (ClientQueryable<T1> unionQuery : unionQueries) {
            ClientQueryable<T1> unionQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(unionQuery);
            entityQueryExpressionBuilder.getExpressionContext().extract(unionQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
            selectUnionQueries.add(unionQueryable);
        }
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createUnionQueryable(entityQueryExpressionBuilder, sqlUnion, selectUnionQueries);
    }

    @Override
    public <TProperty> ClientQueryable<T1> include(boolean condition, SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        if (condition) {
            IncludeNavigateParams includeNavigateParams = new IncludeNavigateParams();

            NavigateInclude<T1> navigateInclude = getSQLExpressionProvider1().getNavigateInclude(includeNavigateParams);
            ClientQueryable<TProperty> clientQueryable = navigateIncludeSQLExpression.apply(navigateInclude);
            boolean hasLimit = clientQueryable.getSQLEntityExpressionBuilder().hasLimit();
            includeNavigateParams.setLimit(hasLimit);
            NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
            if (!Objects.equals(navigateMetadata.getNavigatePropertyType(), clientQueryable.queryClass())) {
                throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(queryClass()) + " include query navigate error return type should:[" + EasyClassUtil.getSimpleName(navigateMetadata.getNavigatePropertyType()) + "] actual:[" + EasyClassUtil.getSimpleName(clientQueryable.queryClass()) + "]");
            }

            SQLFuncExpression<ClientQueryable<?>> queryableExpression = () -> {
                List<Object> relationIds = includeNavigateParams.getRelationIds();
                if (hasLimit) {
                    if (EasyCollectionUtil.isNotEmpty(relationIds) && EasyCollectionUtil.isNotSingle(relationIds)) {
                        Iterator<Object> iterator = relationIds.iterator();
                        Object firstRelationId = iterator.next();
                        ClientQueryable<TProperty> firstQueryable = getRelationLimitQueryable(clientQueryable, navigateMetadata, firstRelationId);
                        ArrayList<ClientQueryable<TProperty>> otherQueryable = new ArrayList<>();
                        while (iterator.hasNext()) {
                            Object nextRelationId = iterator.next();
                            ClientQueryable<TProperty> nextQueryable = getRelationLimitQueryable(clientQueryable, navigateMetadata, nextRelationId);
                            otherQueryable.add(nextQueryable);
                        }
                        return firstQueryable.unionAll(otherQueryable);
                    }
                }
                return clientQueryable.cloneQueryable().where(o -> {
                    o.and(() -> {
                        o.in(navigateMetadata.getTargetPropertyOrPrimary(runtimeContext), relationIds);
                        if (navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
                            navigateMetadata.predicateFilterApply(o);
                        }
//                        navigateMetadata.predicateFilterApply(o);
                    });
                });
//                return appendNavigateTargetProperty(navigateQueryable,navigateMetadata);
            };
            boolean replace = navigateInclude.getIncludeNavigateParams().isReplace();
            if (replace) {
                entityQueryExpressionBuilder.getExpressionContext().getIncludes().put(includeNavigateParams.getNavigateMetadata(), new IncludeNavigateExpression(includeNavigateParams, queryableExpression));
            } else {
                entityQueryExpressionBuilder.getExpressionContext().getIncludes().putIfAbsent(includeNavigateParams.getNavigateMetadata(), new IncludeNavigateExpression(includeNavigateParams, queryableExpression));
            }
        }
        return this;
    }

    private <TProperty> ClientQueryable<TProperty> getRelationLimitQueryable(ClientQueryable<TProperty> clientQueryable, NavigateMetadata navigateMetadata, Object relationId) {

        ClientQueryable<TProperty> firstQueryable = clientQueryable.cloneQueryable();
        firstQueryable.where(o -> {
            o.and(() -> {
                o.eq(navigateMetadata.getTargetPropertyOrPrimary(runtimeContext), relationId);
                if (navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
                    navigateMetadata.predicateFilterApply(o);
                }
//               navigateMetadata.predicateFilterApply(o);
            });
        });
        return firstQueryable;
    }

    private <T> ClientQueryable<T> appendNavigateTargetProperty(ClientQueryable<T> queryable, NavigateMetadata navigateMetadata) {
        return queryable;
//        ExpressionContext expressionContext = queryable.getSQLEntityExpressionBuilder().getExpressionContext();
//        return queryable.select(t -> {
//            t.columnAll();
//            Map<String, RelationExtraColumn> relationExtraColumnMap=new HashMap<>();
//            String targetPropertyOrPrimary = navigateMetadata.getTargetPropertyOrPrimary(runtimeContext);
//            String alias = "__relation__" + targetPropertyOrPrimary;
//            ColumnMetadata columnMetadata = t.getTable().getEntityMetadata().getColumnNotNull(targetPropertyOrPrimary);
//            RelationExtraColumn relationExtraColumn = relationExtraColumnMap.putIfAbsent(alias, new RelationExtraColumn(targetPropertyOrPrimary,alias, columnMetadata));
//            if(relationExtraColumn==null){
//                t.sqlNativeSegment("{0}", c -> {
//                    c.expression(navigateMetadata.getTargetProperty());
//                    c.setAlias(alias);
//                });
//            }
//            expressionContext.setRelationExtraMetadata(new RelationExtraMetadata(relationExtraColumnMap));
//        });
    }

    @Override
    public ClientQueryable<T1> asTreeCTE(String codeProperty, String parentCodeProperty, SQLExpression1<TreeCTEConfigurer> treeExpression) {
        //将当前表达式的expression builder放入新表达式的声明里面新表达式还是当前的T类型

        TreeCTEOption treeCTEOption = new TreeCTEOption();
        TreeCTEConfigurerImpl treeCTEConfigurer = new TreeCTEConfigurerImpl(treeCTEOption);
        treeExpression.apply(treeCTEConfigurer);
        String cteTableName = treeCTEOption.getCTETableName();
        String deepColumnName = treeCTEOption.getDeepColumnName();
        int limitDeep = treeCTEOption.getLimitDeep();
        boolean up = treeCTEOption.isUp();

        ClientQueryable<T1> queryable = runtimeContext.getSQLClientApiFactory().createQueryable(queryClass(), runtimeContext);
        ClientQueryable<T1> cteQueryable = queryable.asTable(cteTableName)
                .innerJoin(queryClass(), (t, t1) -> {
                    if (up) {
                        t.eq(t1, parentCodeProperty, codeProperty);
                    } else {
                        t.eq(t1, codeProperty, parentCodeProperty);
                    }
                })
                .select(t -> t.sqlNativeSegment("{0} + 1", c -> c.columnName(deepColumnName).setAlias(deepColumnName)).columnAll());

        this.select(o -> o.sqlNativeSegment("0", c -> c.setAlias(deepColumnName)).columnAll());

        ClientQueryable<T1> t1ClientQueryable = internalUnion(Collections.singletonList(cteQueryable), treeCTEOption.sqlUnion());
        ClientQueryable<T1> myQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(queryClass(), runtimeContext);
        myQueryable.getSQLEntityExpressionBuilder().getExpressionContext().extract(this.entityQueryExpressionBuilder.getExpressionContext());
        AnonymousEntityTableExpressionBuilder table = (AnonymousEntityTableExpressionBuilder) t1ClientQueryable.getSQLEntityExpressionBuilder().getTable(0);
        EntityQueryExpressionBuilder unionAllEntityQueryExpressionBuilder = table.getEntityQueryExpressionBuilder();
        EntityQueryExpressionBuilder anonymousCTEQueryExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createAnonymousCTEQueryExpressionBuilder(cteTableName, unionAllEntityQueryExpressionBuilder, t1ClientQueryable.getSQLEntityExpressionBuilder().getExpressionContext(), t1ClientQueryable.queryClass());
        myQueryable.getSQLEntityExpressionBuilder().getExpressionContext().getDeclareExpressions().add(anonymousCTEQueryExpressionBuilder);
        myQueryable.asTable(cteTableName);
        if (limitDeep >= 0) {
            myQueryable.where(o -> o.sqlNativeSegment("{0} <= {1}", c -> {
                c.columnName(deepColumnName).value(limitDeep);
            }));
        }
        return myQueryable;
    }

    @Override
    public MethodQuery<T1> forEach(Consumer<T1> mapConfigure) {
        entityQueryExpressionBuilder.getExpressionContext().setForEachConfigurer(EasyObjectUtil.typeCastNullable(mapConfigure));
        return this;
    }

    @Override
    public ClientQueryable<T1> useLogicDelete(boolean enable) {
        if (enable) {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        } else {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> noInterceptor() {
        entityQueryExpressionBuilder.getExpressionContext().noInterceptor();
        return this;
    }

    @Override
    public ClientQueryable<T1> useInterceptor(String name) {
        entityQueryExpressionBuilder.getExpressionContext().useInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable<T1> noInterceptor(String name) {
        entityQueryExpressionBuilder.getExpressionContext().noInterceptor(name);
        return this;
    }

    @Override
    public ClientQueryable<T1> useInterceptor() {
        entityQueryExpressionBuilder.getExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public ClientQueryable<T1> asTracking() {
        TrackManager trackManager = runtimeContext.getTrackManager();

        if (!trackManager.currentThreadTracking()) {
            log.warn("current thread context tracking is not available,plz ensure use annotation [@EasyQueryTrack] or [runtimeContext.getTrackManager().begin()]");
        }
        entityQueryExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.USE_TRACKING);
        return this;
    }

    @Override
    public ClientQueryable<T1> asNoTracking() {
        entityQueryExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.USE_TRACKING);
        return this;
    }

    @Override
    public ClientQueryable<T1> asTable(Function<String, String> tableNameAs) {
        entityQueryExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ClientQueryable<T1> asSchema(Function<String, String> schemaAs) {
        entityQueryExpressionBuilder.getRecentlyTable().setSchemaAs(schemaAs);
        return this;
    }

    @Override
    public ClientQueryable<T1> asAlias(String alias) {
        entityQueryExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public ClientQueryable<T1> asTableLink(Function<String, String> linkAs) {
        entityQueryExpressionBuilder.getRecentlyTable().setTableLinkAs(linkAs);
        return this;
    }

    @Override
    public ClientQueryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        entityQueryExpressionBuilder.getExpressionContext().setMaxShardingQueryLimit(maxShardingQueryLimit);
        entityQueryExpressionBuilder.getExpressionContext().setConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ClientQueryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        entityQueryExpressionBuilder.getExpressionContext().setMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public ClientQueryable<T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        entityQueryExpressionBuilder.getExpressionContext().setConnectionMode(connectionMode);
        return this;
    }

    @Override
    public ClientQueryable<T1> queryLargeColumn(boolean queryLarge) {
        if (queryLarge) {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
        } else {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
        }
        return this;
    }

    @Override
    public ClientQueryable<T1> filterConfigure(ValueFilter valueFilter) {
        entityQueryExpressionBuilder.getExpressionContext().filterConfigure(valueFilter);
        return this;
    }

    @Override
    public ClientQueryable<T1> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        entityQueryExpressionBuilder.getRecentlyTable().setTableLogicDelete(tableLogicDel);
        return this;
    }

    @Override
    public ClientQueryable<T1> behaviorConfigure(SQLExpression1<EasyBehavior> configure) {
        if (configure != null) {
            configure.apply(entityQueryExpressionBuilder.getExpressionContext().getBehavior());
        }
        return this;
    }
}
