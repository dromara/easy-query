package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.sharding.manager.SequenceCountLine;
import com.easy.query.core.sharding.manager.SequenceCountNode;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.dynamic.condition.internal.ObjectQueryPropertyNode;
import com.easy.query.core.api.dynamic.condition.internal.ObjectQueryBuilderImpl;
import com.easy.query.core.api.dynamic.condition.ObjectQuery;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.internal.ObjectSortBuilderImpl;
import com.easy.query.core.api.dynamic.sort.internal.ObjectSortPropertyNode;
import com.easy.query.core.basic.api.select.Queryable2;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryFirstOrNotNullException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyLambdaUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 */
public abstract class AbstractQueryable<T1> implements Queryable<T1> {
    protected final Class<T1> t1Class;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final QueryRuntimeContext runtimeContext;
    protected SQLExpressionProvider<T1> sqlExpressionProvider1;

    @Override
    public Class<T1> queryClass() {
        return t1Class;
    }

    public AbstractQueryable(Class<T1> t1Class, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.t1Class = t1Class;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
    }


    @Override
    public SQLExpressionProvider<T1> getSQLExpressionProvider1() {
        if (sqlExpressionProvider1 == null) {
            sqlExpressionProvider1 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(0, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider1;
    }

    @Override
    public Queryable<T1> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().cloneQueryable(this);
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
        List<Long> result = toInternalListWithExpression(countQueryExpressionBuilder, Long.class);
        return EasyCollectionUtil.sum(result);
    }

    private EntityQueryExpressionBuilder createCountQueryExpressionBuilder() {
        EntityQueryExpressionBuilder queryExpressionBuilder = entityQueryExpressionBuilder.cloneEntityExpressionBuilder();
        EntityQueryExpressionBuilder countSQLEntityExpressionBuilder = EasySQLExpressionUtil.getCountEntityQueryExpression(queryExpressionBuilder);
        if (countSQLEntityExpressionBuilder == null) {
            return cloneQueryable().select("COUNT(1)").getSQLEntityExpressionBuilder();
        }
        return countSQLEntityExpressionBuilder;
    }

    @Override
    public long countDistinct(SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        ProjectSQLBuilderSegmentImpl sqlSegmentBuilder = new ProjectSQLBuilderSegmentImpl();
        SQLColumnSelector<T1> sqlColumnSelector = getSQLExpressionProvider1().getSQLColumnSelector(sqlSegmentBuilder);
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
    public boolean all(SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        setExecuteMethod(ExecuteMethodEnum.ALL);
        Queryable<T1> cloneQueryable = cloneQueryable();
        EntityQueryExpressionBuilder cloneSQLEntityQueryExpressionBuilder1 = cloneQueryable.getSQLEntityExpressionBuilder();
        SQLExpressionProvider<T1> sqlExpressionProvider = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(0, cloneSQLEntityQueryExpressionBuilder1);
        SQLWherePredicate<T1> sqlAllPredicate = sqlExpressionProvider.getSQLAllPredicate();
        whereExpression.apply(sqlAllPredicate);
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = cloneQueryable.select(" 1 ").getSQLEntityExpressionBuilder();
        List<Long> result = toInternalListWithExpression(sqlEntityExpressionBuilder, Long.class);
        return EasyCollectionUtil.all(result, o -> o == 1L);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def) {
        setExecuteMethod(ExecuteMethodEnum.SUM);
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(column, sumFunction);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.SUM);
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(column, sumFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Comparable<?>> TMember maxOrDefault(Property<T1, TMember> column, TMember def) {

        setExecuteMethod(ExecuteMethodEnum.MAX);
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(column, maxFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.MIN);
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(column, minFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number> TMember avgOrDefault(Property<T1, TMember> column, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.AVG);
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TMember> result = selectAggregateList(column, avgFunction);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public Integer lenOrDefault(Property<T1, ?> column, Integer def) {
        setExecuteMethod(ExecuteMethodEnum.LEN);
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        String ownerColumn = EasySQLExpressionUtil.getSQLOwnerColumn(entityQueryExpressionBuilder.getRuntimeContext(), table.getEntityTable(), propertyName);

        ColumnFunction lenFunction = runtimeContext.getColumnFunctionFactory().createLenFunction();
        List<Integer> result = cloneQueryable().select(lenFunction.getFuncColumn(ownerColumn)).toList(Integer.class);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    private <TMember> List<TMember> selectAggregateList(Property<T1, ?> column, ColumnFunction columnFunction) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        FuncColumnSegmentImpl funcColumnSegment = new FuncColumnSegmentImpl(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction);
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return cloneQueryable().select(funcColumnSegment, true).toList((Class<TMember>) columnMetadata.getPropertyType());
    }


    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        setExecuteMethod(ExecuteMethodEnum.FIRST);
        List<TR> list = cloneQueryable().limit(1).toList(resultClass);

        return EasyCollectionUtil.firstOrNull(list);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        TR result = firstOrNull(resultClass);
        if (result == null) {
            throw new EasyQueryFirstOrNotNullException(msg, code);
        }
        return result;
    }

    @Override
    public List<T1> toList() {
        return toList(queryClass());
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

    /**
     * 补齐select操作
     *
     * @param resultClass
     */
    protected void compensateSelect(Class<?> resultClass) {

        if (EasySQLExpressionUtil.shouldCloneSQLEntityQueryExpressionBuilder(entityQueryExpressionBuilder)) {
            select(resultClass);
        }
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
        compensateSelect(resultClass);
        return toInternalListWithExpression(entityQueryExpressionBuilder, resultClass);
//        //todo 检查是否存在分片对象的查询
//        boolean shardingQuery = EasyShardingUtil.isShardingQuery(sqlEntityExpression);
//        if(shardingQuery){
//            compensateSelect(resultClass);
//            //解析sql where 和join on的表达式返回datasource+sql的组合可以利用强制tableNameAs来实现
//            sqlEntityExpression.getRuntimeContext()
//        }else{
//        }
    }

    protected <TR> List<TR> toInternalListWithExpression(EntityQueryExpressionBuilder entityQueryExpressionBuilder, Class<TR> resultClass) {
        ExpressionContext expressionContext = this.entityQueryExpressionBuilder.getExpressionContext();
        boolean tracking = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
        ExecuteMethodEnum executeMethod = expressionContext.getExecuteMethod();
        EntityExpressionExecutor entityExpressionExecutor = this.entityQueryExpressionBuilder.getRuntimeContext().getEntityExpressionExecutor();
        List<TR> result = entityExpressionExecutor.query(ExecutorContext.create(this.entityQueryExpressionBuilder.getRuntimeContext(), true, executeMethod, tracking), resultClass, entityQueryExpressionBuilder);
        //将当前方法设置为unknown
        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
        return result;
    }

    /**
     * 只有select操作运行操作当前projects
     *
     * @param selectExpression
     * @return
     */
    @Override
    public Queryable<T1> select(SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        SQLColumnSelector<T1> sqlColumnSelector = getSQLExpressionProvider1().getSQLColumnSelector(entityQueryExpressionBuilder.getProjects());
        selectExpression.apply(sqlColumnSelector);
        if (EasyCollectionUtil.isSingle(entityQueryExpressionBuilder.getTables())) {
            return this;
        }
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable(queryClass(), entityQueryExpressionBuilder);
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression1<SQLColumnAsSelector<T1, TR>> selectExpression) {
        SQLColumnAsSelector<T1, TR> sqlColumnSelector = getSQLExpressionProvider1().getSQLColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        if (EasySQLSegmentUtil.isEmpty(entityQueryExpressionBuilder.getProjects())) {
            SQLExpression1<SQLColumnAsSelector<T1, TR>> selectAllExpression = SQLColumnAsSelector::columnAll;
            selectAllExpression.apply(sqlColumnSelector);
        }
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public Queryable<T1> select(String columns) {
        entityQueryExpressionBuilder.getProjects().getSQLSegments().clear();
        entityQueryExpressionBuilder.getProjects().append(new SelectConstSegment(columns));
        return this;
    }

    @Override
    public Queryable<T1> select(ColumnSegment columnSegment, boolean clearAll) {
        if (clearAll) {
            entityQueryExpressionBuilder.getProjects().getSQLSegments().clear();
        }
        entityQueryExpressionBuilder.getProjects().append(columnSegment);
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass) {
        SQLExpression1<SQLColumnAsSelector<T1, TR>> selectExpression = SQLColumnAsSelector::columnAll;
        SQLColumnAsSelector<T1, TR> sqlColumnSelector = getSQLExpressionProvider1().getSQLAutoColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public Queryable<T1> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        if (condition) {
            SQLWherePredicate<T1> sqlPredicate = getSQLExpressionProvider1().getSQLWherePredicate();
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> whereById(boolean condition, Object id) {
        if (condition) {

            PredicateSegment where = entityQueryExpressionBuilder.getWhere();
            TableAvailable table = entityQueryExpressionBuilder.getTable(0).getEntityTable();
            String keyProperty = getSingleKeyPropertyName(table);
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SQLPredicateCompareEnum.EQ, entityQueryExpressionBuilder.getRuntimeContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }


    private String getSingleKeyPropertyName(TableAvailable table) {
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if (EasyCollectionUtil.isEmpty(keyProperties)) {
            throw new EasyQueryException("对象:" + EasyClassUtil.getSimpleName(t1Class) + "未找到主键信息");
        }
        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryException("对象:" + EasyClassUtil.getSimpleName(t1Class) + "存在多个主键");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    @Override
    public Queryable<T1> whereByIds(boolean condition, Object... ids) {
        if (condition) {
            Collection<?> extractIds = extractIds(ids);
            return whereByIds(true, extractIds);
        }
        return this;
    }

    @Override
    public <TProperty> Queryable<T1> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            PredicateSegment where = entityQueryExpressionBuilder.getWhere();
            TableAvailable table = entityQueryExpressionBuilder.getTable(0).getEntityTable();
            String keyProperty = getSingleKeyPropertyName(table);
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnCollectionPredicate(table, keyProperty, ids, SQLPredicateCompareEnum.IN, entityQueryExpressionBuilder.getRuntimeContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }


    private Collection<?> extractIds(Object... ids) {
        if (ids == null || ids.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(ids);
    }

    /**
     * 匹配
     *
     * @param propertyQueryEntityClass
     * @return
     */
    protected Class<?> matchQueryEntityClass(Class<?> propertyQueryEntityClass) {
        if (Objects.equals(t1Class, propertyQueryEntityClass)) {
            return t1Class;
        }
        return null;
    }

    /**
     * 匹配查询表达式
     *
     * @param entityClass
     * @return
     */
    protected SQLWherePredicate<?> matchWhereObjectSQLPredicate(Class<?> entityClass) {
        if (entityClass == t1Class) {
            return getSQLExpressionProvider1().getSQLWherePredicate();
        }
        return null;
    }

    protected SQLColumnSelector<?> matchOrderBySQLColumnSelector(Class<?> entityClass, boolean asc) {
        if (entityClass == t1Class) {
            return getSQLExpressionProvider1().getSQLOrderColumnSelector(asc);
        }
        return null;
    }

    private String getObjectPropertyMappingPropertyName(ObjectQueryPropertyNode entityPropertyNode, EasyWhereCondition easyWhereCondition, String fieldName) {
        String configureMapProperty = entityPropertyNode != null ? entityPropertyNode.getProperty() : null;
        if (configureMapProperty != null) {
            return configureMapProperty;
        }
        String propName = easyWhereCondition.propName();
        return EasyStringUtil.isBlank(propName) ? fieldName : propName;
    }


    @Override
    public Queryable<T1> whereObject(boolean condition, Object object) {
        if (condition) {
            if (object != null) {
                EntityMetadataManager entityMetadataManager = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager();

                List<Field> allFields = EasyClassUtil.getAllFields(object.getClass());


                ObjectQueryBuilderImpl<Object> objectObjectQueryBuilder = new ObjectQueryBuilderImpl<>();
                boolean strictMode = true;
                if (ObjectQuery.class.isAssignableFrom(object.getClass())) {
                    ObjectQuery<Object> configuration = (ObjectQuery<Object>) object;
                    configuration.configure(objectObjectQueryBuilder);
                    strictMode = configuration.useStrictMode();
                }
                for (Field field : allFields) {
                    boolean accessible = field.isAccessible();

                    try {
                        field.setAccessible(true);
                        EasyWhereCondition q = field.getAnnotation(EasyWhereCondition.class);
                        if (q == null) {
                            continue;
                        }
                        ObjectQueryPropertyNode entityPropertyNode = objectObjectQueryBuilder.getPropertyMapping(field.getName());
                        Class<?> property2Class = entityPropertyNode != null ? entityPropertyNode.getEntityClass() : q.entityClass();
                        Class<?> propertyQueryEntityClass = Objects.equals(Object.class, property2Class) ? t1Class : matchQueryEntityClass(property2Class);
                        if (propertyQueryEntityClass == null) {
                            if (!strictMode) {
                                continue;
                            }
                            throw new EasyQueryWhereInvalidOperationException(EasyClassUtil.getSimpleName(property2Class) + " not found query entity class");
                        }
                        SQLWherePredicate<?> sqlPredicate = matchWhereObjectSQLPredicate(propertyQueryEntityClass);
                        if (sqlPredicate == null) {
                            if (!strictMode) {
                                continue;
                            }
                            throw new EasyQueryWhereInvalidOperationException("not found sql predicate,entity class:" + EasyClassUtil.getSimpleName(propertyQueryEntityClass));
                        }
                        Object val = field.get(object);

                        if (Objects.isNull(val)) {
                            continue;
                        }
                        if (val instanceof String) {
                            if (EasyStringUtil.isBlank(String.valueOf(val)) && !q.allowEmptyStrings()) {
                                continue;
                            }
                        }
                        String objectPropertyName = getObjectPropertyMappingPropertyName(entityPropertyNode, q, field.getName());
                        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(propertyQueryEntityClass);
                        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(objectPropertyName);
                        FastBean fastBean = EasyBeanUtil.getFastBean(propertyQueryEntityClass);
                        Property propertyLambda = fastBean.getBeanGetter(objectPropertyName, columnMetadata.getPropertyType());

                        switch (q.type()) {
                            case EQUAL:
                                sqlPredicate.eq(propertyLambda, val);
                                break;
                            case GREATER_THAN:
                            case RANGE_LEFT_OPEN:
                                sqlPredicate.gt(propertyLambda, val);
                                break;
                            case LESS_THAN:
                            case RANGE_RIGHT_OPEN:
                                sqlPredicate.lt(propertyLambda, val);
                                break;
                            case LIKE:
                                sqlPredicate.like(propertyLambda, val);
                                break;
                            case LIKE_MATCH_LEFT:
                                sqlPredicate.likeMatchLeft(propertyLambda, val);
                                break;
                            case LIKE_MATCH_RIGHT:
                                sqlPredicate.likeMatchRight(propertyLambda, val);
                                break;
                            case GREATER_THAN_EQUAL:
                            case RANGE_LEFT_CLOSED:
                                sqlPredicate.ge(propertyLambda, val);
                                break;
                            case LESS_THAN_EQUAL:
                            case RANGE_RIGHT_CLOSED:
                                sqlPredicate.le(propertyLambda, val);
                                break;
                            case IN:
                                if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                                    sqlPredicate.in(propertyLambda, (Collection<?>) val);
                                }
                                break;
                            case NOT_IN:
                                if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                                    sqlPredicate.notIn(propertyLambda, (Collection<?>) val);
                                }
                                break;
                            case NOT_EQUAL:
                                sqlPredicate.ne(propertyLambda, val);
                                break;
                            default:
                                break;
                        }

                    } catch (Exception e) {
                        throw new EasyQueryException(e);
                    } finally {
                        field.setAccessible(accessible);
                    }

                }
            }
        }
        return this;
    }

    @Override
    public Queryable<T1> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        if (condition) {
            SQLGroupBySelector<T1> sqlPredicate = getSQLExpressionProvider1().getSQLGroupColumnSelector();
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> having(boolean condition, SQLExpression1<SQLAggregatePredicate<T1>> predicateExpression) {

        if (condition) {
            SQLAggregatePredicate<T1> sqlAggregatePredicate = getSQLExpressionProvider1().getSQLAggregatePredicate();
            predicateExpression.apply(sqlAggregatePredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> orderBy(boolean condition, SQLExpression1<SQLColumnSelector<T1>> selectExpression, boolean asc) {
        if (condition) {
            SQLColumnSelector<T1> sqlPredicate = getSQLExpressionProvider1().getSQLOrderColumnSelector(asc);
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> orderByDynamic(boolean condition, ObjectSort configuration) {

        if (condition) {
            if (configuration != null) {
                boolean strictMode = configuration.useStrictMode();
                EntityMetadataManager entityMetadataManager = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager();

                ObjectSortBuilderImpl orderByBuilder = new ObjectSortBuilderImpl(configuration.dynamicMode());
                configuration.configure(orderByBuilder);
                Map<String, ObjectSortPropertyNode> orderProperties = orderByBuilder.getProperties();
                for (String property : orderProperties.keySet()) {
                    ObjectSortPropertyNode orderByPropertyNode = orderProperties.get(property);

                    Class<?> orderByEntityClass = matchQueryEntityClass(orderByPropertyNode.getEntityClass());
                    if (orderByEntityClass == null) {
                        if (!strictMode) {
                            continue;
                        }
                        throw new EasyQueryOrderByInvalidOperationException(property, EasyClassUtil.getSimpleName(orderByPropertyNode.getEntityClass()) + " not found query entity class");
                    }
                    EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(orderByEntityClass);
                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                    FastBean fastBean = EasyBeanUtil.getFastBean(orderByEntityClass);
                    Property propertyLambda = fastBean.getBeanGetter(columnMetadata.getProperty());
                    SQLColumnSelector<?> sqlColumnSelector = matchOrderBySQLColumnSelector(orderByEntityClass, orderByPropertyNode.isAsc());
                    if (sqlColumnSelector == null) {
                        if (!strictMode) {
                            continue;
                        }
                        throw new EasyQueryOrderByInvalidOperationException(property, "not found sql column selector,entity class:" + EasyClassUtil.getSimpleName(orderByEntityClass));
                    }
                    sqlColumnSelector.column(propertyLambda);
                }
            }
        }
        return this;
    }

    @Override
    public Queryable<T1> limit(boolean condition, long offset, long rows) {
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
            doWithOutAnonymousAndClearExpression(anonymousEntityTableExpression.getEntityQueryExpressionBuilder(), consumer);
        } else {
            consumer.accept(sqlEntityExpression);
        }
    }

    @Override
    public Queryable<T1> distinct(boolean condition) {
        if (condition) {
            doWithOutAnonymousAndClearExpression(entityQueryExpressionBuilder, exp -> {
                exp.setDistinct(true);
            });
        }
        return this;
    }

    @Override
    public EasyPageResult<T1> toPageResult(long pageIndex, long pageSize, long pageTotal) {
        return doPageResult(pageIndex, pageSize, t1Class, pageTotal);
    }

    protected <TR> EasyPageResult<TR> doPageResult(long pageIndex, long pageSize, Class<TR> clazz, long pageTotal) {
        //设置每次获取多少条
        long take = pageSize <= 0 ? 1 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        long total = pageTotal < 0 ? this.count() : pageTotal;
        EasyPageResultProvider easyPageResultProvider = entityQueryExpressionBuilder.getRuntimeContext().getEasyPageResultProvider();
        if (total <= offset) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, Collections.emptyList());
        }//获取剩余条数
        long remainingCount = total - offset;
        //当剩余条数小于take数就取remainingCount
        long realTake = Math.min(remainingCount, take);
        if (realTake <= 0) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, Collections.emptyList());
        }
        this.limit(offset, realTake);
        List<TR> data = this.toInternalList(clazz);
        return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, data);
    }

    @Override
    public EasyPageResult<T1> toShardingPageResult(long pageIndex, long pageSize, SequenceCountLine sequenceCountLine) {
        return doShardingPageResult(pageIndex, pageSize, t1Class, sequenceCountLine);
    }

    protected <TR> EasyPageResult<TR> doShardingPageResult(long pageIndex, long pageSize, Class<TR> clazz, SequenceCountLine sequenceCountLine) {
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
            if (sequenceCountLine != null) {
                List<SequenceCountNode> countNodes = sequenceCountLine.getCountNodes();
                for (SequenceCountNode countNode : countNodes) {
                    shardingQueryCountManager.addCountResult(countNode.getTotal(), true);
                }
            }
            long total = this.count();
            if (sequenceCountLine != null) {
                total = EasyCollectionUtil.sumLong(shardingQueryCountManager.getCountResult(), SequenceCountNode::getTotal);
            }
            EasyPageResultProvider easyPageResultProvider = runtimeContext.getEasyPageResultProvider();
            if (total <= offset) {
                return easyPageResultProvider.createShardingPageResult(pageIndex, pageSize, total, Collections.emptyList(), shardingQueryCountManager.getSequenceCountLine());
            }
            //获取剩余条数
            long remainingCount = total - offset;
            //当剩余条数小于take数就取remainingCount
            long realTake = Math.min(remainingCount, take);
            if (realTake <= 0) {
                return easyPageResultProvider.createShardingPageResult(pageIndex, pageSize, total, Collections.emptyList(), shardingQueryCountManager.getSequenceCountLine());
            }
            this.limit(offset, realTake);
            List<TR> data = this.toInternalList(clazz);
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
    public <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> on) {
        //todo 需要判断当前的表达式是否存在where group order之类的操作,是否是一个clear expression如果不是那么就需要先select all如果没有select过然后创建一个anonymous的table去join
        //简单理解就是queryable需要支持join操作 还有queryable 和queryable之间如何join

        Queryable<T2> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public Queryable<T1> union(Collection<Queryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        return internalUnion(unionQueries, SQLUnionEnum.UNION);
    }

    @Override
    public Queryable<T1> unionAll(Collection<Queryable<T1>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            return this;
        }
        return internalUnion(unionQueries, SQLUnionEnum.UNION_ALL);
    }

    protected Queryable<T1> internalUnion(Collection<Queryable<T1>> unionQueries, SQLUnionEnum sqlUnion) {

        List<Queryable<T1>> selectUnionQueries = new ArrayList<>(unionQueries.size() + 1);
        Queryable<T1> myQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(this);
        selectUnionQueries.add(myQueryable);
        for (Queryable<T1> unionQuery : unionQueries) {
            Queryable<T1> unionQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(unionQuery);
            selectUnionQueries.add(unionQueryable);
        }
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createUnionQueryable(entityQueryExpressionBuilder.getRuntimeContext(), sqlUnion, selectUnionQueries);
    }

    @Override
    public Queryable<T1> useLogicDelete(boolean enable) {
        if (enable) {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        } else {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
        return this;
    }

    @Override
    public Queryable<T1> noInterceptor() {
        entityQueryExpressionBuilder.getExpressionContext().noInterceptor();
        return this;
    }

    @Override
    public Queryable<T1> useInterceptor(String name) {
        entityQueryExpressionBuilder.getExpressionContext().useInterceptor(name);
        return this;
    }

    @Override
    public Queryable<T1> noInterceptor(String name) {
        entityQueryExpressionBuilder.getExpressionContext().noInterceptor(name);
        return this;
    }

    @Override
    public Queryable<T1> useInterceptor() {
        entityQueryExpressionBuilder.getExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public Queryable<T1> asTracking() {
        entityQueryExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.USE_TRACKING);
        return this;
    }

    @Override
    public Queryable<T1> asNoTracking() {
        entityQueryExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.USE_TRACKING);
        return this;
    }

    @Override
    public Queryable<T1> asTable(Function<String, String> tableNameAs) {
        entityQueryExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public Queryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        entityQueryExpressionBuilder.getExpressionContext().setMaxShardingQueryLimit(maxShardingQueryLimit);
        entityQueryExpressionBuilder.getExpressionContext().setConnectionMode(connectionMode);
        return this;
    }

    @Override
    public Queryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        entityQueryExpressionBuilder.getExpressionContext().setMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public Queryable<T1> useConnectionMode(ConnectionModeEnum connectionMode) {
        entityQueryExpressionBuilder.getExpressionContext().setConnectionMode(connectionMode);
        return this;
    }

    @Override
    public Queryable<T1> queryLargeColumn(boolean queryLarge) {
        if (queryLarge) {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
        } else {
            entityQueryExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
        }
        return this;
    }
}
