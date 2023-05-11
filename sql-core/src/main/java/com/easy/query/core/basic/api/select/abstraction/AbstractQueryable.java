package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.core.SqlAggregatePredicate;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.dynamic.where.internal.DynamicWherePropertyNode;
import com.easy.query.core.api.dynamic.where.internal.DefaultEasyWhereBuilder;
import com.easy.query.core.api.dynamic.where.EasyWhere;
import com.easy.query.core.api.dynamic.order.EasyOrderBy;
import com.easy.query.core.api.dynamic.order.internal.DefaultOrderByBuilder;
import com.easy.query.core.api.dynamic.order.internal.DynamicOrderByPropertyNode;
import com.easy.query.core.basic.api.select.Queryable2;
import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.lambda.SqlExpression2;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegmentImpl;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryNotFoundException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.LambdaUtil;
import com.easy.query.core.util.SqlExpressionUtil;
import com.easy.query.core.util.StringUtil;

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
    //    protected final SqlEntityTableExpression sqlTable;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    //    protected final Select1SqlProvider<T1> sqlPredicateProvider;

    @Override
    public Class<T1> queryClass() {
        return t1Class;
    }

    public AbstractQueryable(Class<T1> t1Class, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.t1Class = t1Class;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
    }

    @Override
    public Queryable<T1> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().cloneQueryable(this);
    }

    private void setExecuteMethod(ExecuteMethodEnum executeMethod){
        setExecuteMethod(executeMethod,false);
    }
    private void setExecuteMethod(ExecuteMethodEnum executeMethod,boolean ifUnknown){
        entityQueryExpressionBuilder.getExpressionContext().executeMethod(executeMethod,ifUnknown);
    }
    @Override
    public long count() {
        EntityQueryExpressionBuilder queryExpressionBuilder = entityQueryExpressionBuilder.cloneEntityExpressionBuilder();
        setExecuteMethod(ExecuteMethodEnum.COUNT);
        EntityQueryExpressionBuilder countSqlEntityExpressionBuilder = SqlExpressionUtil.getCountEntityQueryExpression(queryExpressionBuilder);
        if (countSqlEntityExpressionBuilder == null) {
            List<Long> result = cloneQueryable().select(" COUNT(1) ").toList(Long.class);
            return EasyCollectionUtil.firstOrDefault(result, 0L);
        }

        List<Long> result = toInternalListWithExpression(countSqlEntityExpressionBuilder, Long.class);
        return EasyCollectionUtil.sum(result);
    }

    @Override
    public long countDistinct(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        ProjectSqlBuilderSegmentImpl sqlSegmentBuilder = new ProjectSqlBuilderSegmentImpl();
        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(sqlSegmentBuilder);
        selectExpression.apply(sqlColumnSelector);

        setExecuteMethod(ExecuteMethodEnum.COUNT_DISTINCT);
        List<Long> result = cloneQueryable().select(EasyAggregate.COUNT_DISTINCT.getFuncColumn(sqlSegmentBuilder.toSql(null))).toList(Long.class);

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
    public boolean all(SqlExpression<SqlWherePredicate<T1>> whereExpression) {
        setExecuteMethod(ExecuteMethodEnum.ALL);
        Queryable<T1> cloneQueryable = cloneQueryable();
        SqlWherePredicate<T1> sqlAllPredicate = cloneQueryable.getSqlBuilderProvider1().getSqlAllPredicate1();
        whereExpression.apply(sqlAllPredicate);
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = cloneQueryable.select(" 1 ").getSqlEntityExpressionBuilder();
        List<Long> result = toInternalListWithExpression(sqlEntityExpressionBuilder,Long.class);
        return EasyCollectionUtil.all(result,o->o==1L);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def) {
        setExecuteMethod(ExecuteMethodEnum.SUM);
        List<TMember> result = selectAggregateList(column, EasyAggregate.SUM);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.SUM);
        List<TMember> result = selectAggregateList(column, EasyAggregate.SUM);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Comparable<?>> TMember maxOrDefault(Property<T1, TMember> column, TMember def) {

        setExecuteMethod(ExecuteMethodEnum.MAX);
        List<TMember> result = selectAggregateList(column, EasyAggregate.MAX);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.MIN);
        List<TMember> result = selectAggregateList(column, EasyAggregate.MIN);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number> TMember avgOrDefault(Property<T1, TMember> column, TMember def) {
        setExecuteMethod(ExecuteMethodEnum.AVG);
        List<TMember> result = selectAggregateList(column, EasyAggregate.AVG);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public Integer lenOrDefault(Property<T1, ?> column, Integer def) {
        setExecuteMethod(ExecuteMethodEnum.LEN);
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
        String propertyName = LambdaUtil.getPropertyName(column);
        String ownerColumn = SqlExpressionUtil.getSqlOwnerColumn(entityQueryExpressionBuilder.getRuntimeContext(), table.getEntityTable(), propertyName);
        List<Integer> result = cloneQueryable().select(EasyAggregate.LEN.getFuncColumn(ownerColumn)).toList(Integer.class);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    private <TMember> List<TMember> selectAggregateList(Property<T1, ?> column, EasyFunc easyFunc) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
        String propertyName = LambdaUtil.getPropertyName(column);
        FuncColumnSegmentImpl funcColumnSegment = new FuncColumnSegmentImpl(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), easyFunc);
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        return cloneQueryable().select(funcColumnSegment,true).toList((Class<TMember>) columnMetadata.getProperty().getPropertyType());
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
            throw new EasyQueryNotFoundException(msg, code);
        }
        return result;
    }

    @Override
    public List<T1> toList() {
        return toList(queryClass());
    }

    @Override
    public List<Map<String, Object>> toMaps() {
        List maps = toQueryMaps();
        return (List<Map<String, Object>>) maps;
    }

    private List<Map> toQueryMaps() {
        setExecuteMethod(ExecuteMethodEnum.LIST);
        if (SqlExpressionUtil.shouldCloneSqlEntityQueryExpressionBuilder(entityQueryExpressionBuilder)) {
            return select(queryClass()).toList(Map.class);
        }
        return toList(Map.class);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        setExecuteMethod(ExecuteMethodEnum.LIST,true);
        return toInternalList(resultClass);
    }

    protected void compensateSelect(Class<?> resultClass) {

        if (SqlExpressionUtil.shouldCloneSqlEntityQueryExpressionBuilder(entityQueryExpressionBuilder)) {
            select(resultClass);
        }
    }

    @Override
    public <TR> String toSql(Class<TR> resultClass, SqlParameterCollector sqlParameterCollector) {
        compensateSelect(resultClass);
//        if (SqlExpressionUtil.shouldCloneSqlEntityQueryExpression(sqlEntityExpression)) {
//            return select(resultClass).toSql();
//        }
        return entityQueryExpressionBuilder.toExpression().toSql(sqlParameterCollector);
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
        List<TR> result = entityExpressionExecutor.query(ExecutorContext.create(this.entityQueryExpressionBuilder.getRuntimeContext(), false, executeMethod, tracking), resultClass, entityQueryExpressionBuilder);
        //将当前方法设置为unknown
        setExecuteMethod(ExecuteMethodEnum.UNKNOWN);
        return result;
    }
//    protected <TR> List<TR> toInternalListWithSql(String sql,Class<TR> resultClass){
//        EasyExecutor easyExecutor = sqlEntityExpression.getRuntimeContext().getEasyExecutor();
//        boolean tracking = sqlEntityExpression.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
//        return easyExecutor.query(ExecutorContext.create(sqlEntityExpression.getRuntimeContext(),tracking ), resultClass, sql, sqlEntityExpression.getParameters());
//
//    }

    /**
     * 只有select操作运行操作当前projects
     *
     * @param selectExpression
     * @return
     */
    @Override
    public Queryable<T1> select(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(entityQueryExpressionBuilder.getProjects());
        selectExpression.apply(sqlColumnSelector);
        return entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable(queryClass(), entityQueryExpressionBuilder);
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        SqlColumnAsSelector<T1, TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnAsSelector1(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        return entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public Queryable<T1> select(String columns) {
        entityQueryExpressionBuilder.getProjects().getSqlSegments().clear();
        entityQueryExpressionBuilder.getProjects().append(new SelectConstSegment(columns));
        return this;
    }

    @Override
    public Queryable<T1> select(ColumnSegment columnSegment, boolean clearAll) {
        if(clearAll){
            entityQueryExpressionBuilder.getProjects().getSqlSegments().clear();
        }
        entityQueryExpressionBuilder.getProjects().append(columnSegment);
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass) {
        SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression = SqlColumnAsSelector::columnAll;
        SqlColumnAsSelector<T1, TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlAutoColumnAsSelector1(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        return entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public Queryable<T1> where(boolean condition, SqlExpression<SqlWherePredicate<T1>> whereExpression) {
        if (condition) {
            SqlWherePredicate<T1> sqlPredicate = getSqlBuilderProvider1().getSqlWherePredicate1();
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> whereById(boolean condition, Object id) {
        if (condition) {

            PredicateSegment where = entityQueryExpressionBuilder.getWhere();
            EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
            Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
            if (keyProperties.isEmpty()) {
                throw new EasyQueryException("对象:" + ClassUtil.getSimpleName(t1Class) + "未找到主键信息");
            }
            if (keyProperties.size() > 1) {
                throw new EasyQueryException("对象:" + ClassUtil.getSimpleName(t1Class) + "存在多个主键");
            }
            String keyProperty = keyProperties.iterator().next();
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(table.getEntityTable(), keyProperty, id, SqlPredicateCompareEnum.EQ, entityQueryExpressionBuilder.getRuntimeContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
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
    protected SqlWherePredicate<?> matchWhereObjectSqlPredicate(Class<?> entityClass) {
        if (entityClass == t1Class) {
            return getSqlBuilderProvider1().getSqlWherePredicate1();
        }
        return null;
    }

    protected SqlColumnSelector<?> matchOrderBySqlColumnSelector(Class<?> entityClass, boolean asc) {
        if (entityClass == t1Class) {
            return getSqlBuilderProvider1().getSqlOrderColumnSelector1(asc);
        }
        return null;
    }

    private String getObjectPropertyMappingPropertyName(DynamicWherePropertyNode entityPropertyNode, EasyWhereCondition easyWhereCondition, String fieldName) {
        String configureMapProperty = entityPropertyNode != null ? entityPropertyNode.getProperty() : null;
        if (configureMapProperty != null) {
            return configureMapProperty;
        }
        String propName = easyWhereCondition.propName();
        return StringUtil.isBlank(propName) ? fieldName : propName;
    }


    @Override
    public Queryable<T1> whereObject(boolean condition, Object object) {
        if (condition) {
            if (object != null) {
                EntityMetadataManager entityMetadataManager = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager();

                List<Field> allFields = ClassUtil.getAllFields(object.getClass());


                DefaultEasyWhereBuilder<Object> objectObjectQueryBuilder = new DefaultEasyWhereBuilder<>();
                boolean strictMode = true;
                if (EasyWhere.class.isAssignableFrom(object.getClass())) {
                    EasyWhere<Object> configuration = (EasyWhere<Object>) object;
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
                        DynamicWherePropertyNode entityPropertyNode = objectObjectQueryBuilder.getPropertyMapping(field.getName());
                        Class<?> property2Class = entityPropertyNode != null ? entityPropertyNode.getEntityClass() : q.entityClass();
                        Class<?> propertyQueryEntityClass = Objects.equals(Object.class, property2Class) ? t1Class : matchQueryEntityClass(property2Class);
                        if (propertyQueryEntityClass == null) {
                            if (!strictMode) {
                                continue;
                            }
                            throw new EasyQueryWhereInvalidOperationException(ClassUtil.getSimpleName(property2Class) + " not found query entity class");
                        }
                        SqlWherePredicate<?> sqlPredicate = matchWhereObjectSqlPredicate(propertyQueryEntityClass);
                        if (sqlPredicate == null) {
                            if (!strictMode) {
                                continue;
                            }
                            throw new EasyQueryWhereInvalidOperationException("not found sql predicate,entity class:" + ClassUtil.getSimpleName(propertyQueryEntityClass));
                        }
                        Object val = field.get(object);

                        if (Objects.isNull(val)) {
                            continue;
                        }
                        if (val instanceof String) {
                            if (StringUtil.isBlank(String.valueOf(val)) && !q.allowEmptyStrings()) {
                                continue;
                            }
                        }
                        String objectPropertyName = getObjectPropertyMappingPropertyName(entityPropertyNode, q, field.getName());
                        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(propertyQueryEntityClass);
                        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(objectPropertyName);
                        FastBean fastBean = EasyUtil.getFastBean(propertyQueryEntityClass);
                        Property propertyLambda = fastBean.getBeanGetter(objectPropertyName, columnMetadata.getProperty().getPropertyType());

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
    public Queryable<T1> groupBy(boolean condition, SqlExpression<SqlGroupBySelector<T1>> selectExpression) {
        if (condition) {
            SqlGroupBySelector<T1> sqlPredicate = getSqlBuilderProvider1().getSqlGroupColumnSelector1();
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> having(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateExpression) {

        if (condition) {
            SqlAggregatePredicate<T1> sqlAggregatePredicate = getSqlBuilderProvider1().getSqlAggregatePredicate1();
            predicateExpression.apply(sqlAggregatePredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> orderBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression, boolean asc) {
        if (condition) {
            SqlColumnSelector<T1> sqlPredicate = getSqlBuilderProvider1().getSqlOrderColumnSelector1(asc);
            selectExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> orderByDynamic(boolean condition, EasyOrderBy configuration) {

        if (condition) {
            if (configuration != null) {
                boolean strictMode = configuration.useStrictMode();
                EntityMetadataManager entityMetadataManager = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager();

                DefaultOrderByBuilder orderByBuilder = new DefaultOrderByBuilder(configuration.dynamicMode());
                configuration.configure(orderByBuilder);
                Map<String, DynamicOrderByPropertyNode> orderProperties = orderByBuilder.getOrderProperties();
                for (String property : orderProperties.keySet()) {
                    DynamicOrderByPropertyNode orderByPropertyNode = orderProperties.get(property);

                    Class<?> orderByEntityClass = matchQueryEntityClass(orderByPropertyNode.getEntityClass());
                    if (orderByEntityClass == null) {
                        if (!strictMode) {
                            continue;
                        }
                        throw new EasyQueryOrderByInvalidOperationException(property, ClassUtil.getSimpleName(orderByPropertyNode.getEntityClass()) + " not found query entity class");
                    }
                    EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(orderByEntityClass);
                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                    FastBean fastBean = EasyUtil.getFastBean(orderByEntityClass);
                    Property propertyLambda = fastBean.getBeanGetter(columnMetadata.getProperty());
                    SqlColumnSelector<?> sqlColumnSelector = matchOrderBySqlColumnSelector(orderByEntityClass, orderByPropertyNode.isAsc());
                    if (sqlColumnSelector == null) {
                        if (!strictMode) {
                            continue;
                        }
                        throw new EasyQueryOrderByInvalidOperationException(property, "not found sql column selector,entity class:" + ClassUtil.getSimpleName(orderByEntityClass));
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
        if (SqlExpressionUtil.limitAndOrderNotSetCurrent(sqlEntityExpression)) {
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
            return easyPageResultProvider.createPageResult(total, new ArrayList<>(0));
        }//获取剩余条数
        long remainingCount = total - offset;
        //当剩余条数小于take数就取remainingCount
        long realTake = Math.min(remainingCount, take);
        this.limit(offset, realTake);
        List<TR> data = this.toInternalList(clazz);
        return easyPageResultProvider.createPageResult(total, data);
    }


    @Override
    public EntityQueryExpressionBuilder getSqlEntityExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on) {
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on) {
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on) {
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlWherePredicate<T1>, SqlWherePredicate<T2>> on) {
        //todo 需要判断当前的表达式是否存在where group order之类的操作,是否是一个clear expression如果不是那么就需要先select all如果没有select过然后创建一个anonymous的table去join
        //简单理解就是queryable需要支持join操作 还有queryable 和queryable之间如何join

        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }
//    @SafeVarargs
//    @SuppressWarnings("varargs")
//    @Override
//    public final Queryable<T1> unionAll(Queryable<T1>... otherQueryables) {
//        Queryable<T1> t1Queryable =SqlExpressionUtil.cloneAndSelectAllQueryable(this);
//        Queryable<T1> t1Queryable1 = SqlExpressionUtil.cloneAndSelectAllQueryable(otherQueryable);
//
//    }

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
    public abstract EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1();

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
}
