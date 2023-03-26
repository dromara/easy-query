package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.api.pagination.PageResult;
import com.easy.query.core.api.dynamic.where.internal.DynamicWherePropertyNode;
import com.easy.query.core.api.dynamic.where.internal.DefaultEasyDynamicWhereBuilder;
import com.easy.query.core.api.dynamic.where.EasyDynamicWhereConfiguration;
import com.easy.query.core.api.dynamic.order.EasyDynamicOrderByConfiguration;
import com.easy.query.core.api.dynamic.order.internal.DefaultEasyDynamicOrderByBuilder;
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
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate0;
import com.easy.query.core.expression.sql.AnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.util.*;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryNotFoundException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

/**
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 * @author xuejiaming
 */
public abstract class AbstractQueryable<T1> implements Queryable<T1> {
    protected final Class<T1> t1Class;
    //    protected final SqlEntityTableExpression sqlTable;
    protected final SqlEntityQueryExpression sqlEntityExpression;
//    protected final Select1SqlProvider<T1> sqlPredicateProvider;

    @Override
    public Class<T1> queryClass() {
        return t1Class;
    }

    public AbstractQueryable(Class<T1> t1Class, SqlEntityQueryExpression sqlEntityExpression) {
        this.t1Class = t1Class;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public Queryable<T1> cloneQueryable() {
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().cloneQueryable(this);
    }

    @Override
    public long count() {
        List<Long> result = cloneQueryable().select(" COUNT(1) ").toList(Long.class);
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
    public long countDistinct(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        ProjectSqlBuilderSegment sqlSegmentBuilder = new ProjectSqlBuilderSegment();
        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(sqlSegmentBuilder);
        selectExpression.apply(sqlColumnSelector);
        List<Long> result = cloneQueryable().select(EasyAggregate.COUNT_DISTINCT.getFuncColumn(sqlSegmentBuilder.toSql())).toList(Long.class);

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
        List<Integer> result = cloneQueryable().limit(1).select(" 1 ").toList(Integer.class);
        return !result.isEmpty();
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.SUM);
        TMember resultMember = ArrayUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.SUM);
        return ArrayUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(Property<T1, TMember> column, TMember def) {

        List<TMember> result = selectAggregateList(column, EasyAggregate.MAX);
        return ArrayUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.MIN);
        return ArrayUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number> TMember avgOrDefault(Property<T1, TMember> column, TMember def) {
        List<TMember> result = selectAggregateList(column, EasyAggregate.AVG);
        return ArrayUtil.firstOrDefault(result, def);
    }

    @Override
    public Integer lenOrDefault(Property<T1, ?> column, Integer def) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(0);
        String propertyName = table.getPropertyName(column);
        String ownerColumn = sqlEntityExpression.getSqlOwnerColumn(table, propertyName);
        List<Integer> result = cloneQueryable().select(EasyAggregate.LEN.getFuncColumn(ownerColumn)).toList(Integer.class);
        return ArrayUtil.firstOrDefault(result, def);
    }

    private <TMember> List<TMember> selectAggregateList(Property<T1, ?> column, EasyFunc easyFunc) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(0);
        String propertyName = table.getPropertyName(column);
        ColumnMetadata columnMetadata = EasyUtil.getColumnMetadata(table, propertyName);
        String ownerColumn = sqlEntityExpression.getSqlOwnerColumn(table, propertyName);
        return cloneQueryable().select(easyFunc.getFuncColumn(ownerColumn)).toList((Class<TMember>) columnMetadata.getProperty().getPropertyType());
    }


    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        List<TR> list = cloneQueryable().limit(1).toList(resultClass);

        return ArrayUtil.firstOrNull(list);
    }

    @Override
    public <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code) {
        TR result = firstOrNull(resultClass);
        if (result == null) {
            throw new EasyQueryNotFoundException(msg, code);
        }
        return result;
    }

    /**
     * 存在多张表或者不存在匿名表
     *
     * @return
     */
    private boolean moreTableExpressionOrNoAnonymous() {
        return sqlEntityExpression.getTables().size() != 1 || !(sqlEntityExpression.getTables().get(0) instanceof AnonymousEntityTableExpression);
    }

    @Override
    public List<T1> toList() {
        return toList(queryClass());
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return toInternalList(resultClass);
    }

    @Override
    public <TR> String toSql(Class<TR> resultClass) {

        if (SqlExpressionUtil.shouldCloneSqlEntityQueryExpression(sqlEntityExpression)) {
            return select(resultClass).toSql();
        }
        return toInternalSql();
    }

    protected abstract String toInternalSql();

    /**
     * 子类实现方法
     *
     * @return
     */
    protected <TR> List<TR> toInternalList(Class<TR> resultClass) {
        String sql = toSql(resultClass);
        EasyExecutor easyExecutor = sqlEntityExpression.getRuntimeContext().getEasyExecutor();
        return easyExecutor.query(ExecutorContext.create(sqlEntityExpression.getRuntimeContext(), sqlEntityExpression.getSqlExpressionContext().isTracking()), resultClass, sql, sqlEntityExpression.getParameters());
    }

    /**
     * 只有select操作运行操作当前projects
     *
     * @param selectExpression
     * @return
     */
    @Override
    public Queryable<T1> select(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        SqlColumnSelector<T1> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnSelector1(sqlEntityExpression.getProjects());
        selectExpression.apply(sqlColumnSelector);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(queryClass(), sqlEntityExpression);
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        SqlColumnAsSelector<T1, TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlColumnAsSelector1(sqlEntityExpression.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }

    @Override
    public Queryable<T1> select(String columns) {
        sqlEntityExpression.getProjects().getSqlSegments().clear();
        sqlEntityExpression.getProjects().append(new SelectConstSegment(columns));
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass) {
        SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression = ColumnSelector::columnAll;
        SqlColumnAsSelector<T1, TR> sqlColumnSelector = getSqlBuilderProvider1().getSqlAutoColumnAsSelector1(sqlEntityExpression.getProjects(), resultClass);
        selectExpression.apply(sqlColumnSelector);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }

    @Override
    public Queryable<T1> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression) {
        if (condition) {
            SqlPredicate<T1> sqlPredicate = getSqlBuilderProvider1().getSqlWherePredicate1();
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public Queryable<T1> whereId(boolean condition, Object id) {
        if (condition) {

            PredicateSegment where = sqlEntityExpression.getWhere();
            SqlEntityTableExpression table = sqlEntityExpression.getTable(0);
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
                    .setPredicate(new ColumnValuePredicate0(table, keyProperty, id, SqlPredicateCompareEnum.EQ, sqlEntityExpression));
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
    protected SqlPredicate<?> matchWhereObjectSqlPredicate(Class<?> entityClass) {
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
                EntityMetadataManager entityMetadataManager = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager();

                List<Field> allFields = ClassUtil.getAllFields(object.getClass());


                DefaultEasyDynamicWhereBuilder<Object> objectObjectQueryBuilder = new DefaultEasyDynamicWhereBuilder<>();
                boolean strictMode = true;
                if (EasyDynamicWhereConfiguration.class.isAssignableFrom(object.getClass())) {
                    EasyDynamicWhereConfiguration<Object> configuration = (EasyDynamicWhereConfiguration<Object>) object;
                    configuration.configure(objectObjectQueryBuilder);
                    strictMode = configuration.isStrictMode();
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
                            if(!strictMode){
                                continue;
                            }
                            throw new EasyQueryWhereInvalidOperationException(ClassUtil.getSimpleName(property2Class) + " not found query entity class");
                        }
                        SqlPredicate<?> sqlPredicate = matchWhereObjectSqlPredicate(propertyQueryEntityClass);
                        if (sqlPredicate == null) {
                            if(!strictMode){
                                continue;
                            }
                            throw new EasyQueryWhereInvalidOperationException("not found sql predicate,entity class:" + ClassUtil.getSimpleName(propertyQueryEntityClass));
                        }
                        Object val = field.get(object);

                        if (Objects.isNull(val)) {
                            continue;
                        }
                        if (val instanceof String) {
                            if (!q.allowEmptyStrings()) {
                                continue;
                            }
                        }
                        String objectPropertyName = getObjectPropertyMappingPropertyName(entityPropertyNode, q, field.getName());
                        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(propertyQueryEntityClass);
                        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(objectPropertyName);
                        Property propertyLambda = EasyUtil.getPropertyLambda(propertyQueryEntityClass, objectPropertyName, columnMetadata.getProperty().getPropertyType());

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
                            case LIKE_START:
                                sqlPredicate.likeMatchLeft(propertyLambda, val);
                                break;
                            case LIKE_END:
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
                                if (ArrayUtil.isNotEmpty((Collection<?>) val)) {
                                    sqlPredicate.in(propertyLambda, (Collection<?>) val);
                                }
                                break;
                            case NOT_IN:
                                if (ArrayUtil.isNotEmpty((Collection<?>) val)) {
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
    public Queryable<T1> groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlPredicate = getSqlBuilderProvider1().getSqlGroupColumnSelector1();
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
    public Queryable<T1> orderByDynamic(boolean condition, EasyDynamicOrderByConfiguration configuration) {

        if (condition) {
            if (configuration != null) {
                boolean strictMode = configuration.isStrictMode();
                EntityMetadataManager entityMetadataManager = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager();

                DefaultEasyDynamicOrderByBuilder orderByBuilder = new DefaultEasyDynamicOrderByBuilder(configuration.dynamicMode());
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
                    Property propertyLambda = EasyUtil.getPropertyLambda(orderByEntityClass, property, columnMetadata.getProperty().getPropertyType());
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
            doWithOutAnonymousAndClearExpression(sqlEntityExpression, exp -> {
                exp.setOffset(offset);
                exp.setRows(rows);
            });
        }
        return this;
    }

    private void doWithOutAnonymousAndClearExpression(SqlEntityQueryExpression sqlEntityExpression, Consumer<SqlEntityQueryExpression> consumer) {

        //如果当前只有一张表并且是匿名表,那么limit直接处理当前的匿名表的表达式
        if (SqlExpressionUtil.limitAndOrderNotSetCurrent(sqlEntityExpression)) {
            AnonymousEntityTableExpression anonymousEntityTableExpression = (AnonymousEntityTableExpression) sqlEntityExpression.getTable(0);
            doWithOutAnonymousAndClearExpression(anonymousEntityTableExpression.getSqlEntityQueryExpression(), consumer);
        } else {
            consumer.accept(sqlEntityExpression);
        }
    }

    @Override
    public Queryable<T1> distinct(boolean condition) {
        if (condition) {
            doWithOutAnonymousAndClearExpression(sqlEntityExpression, exp -> {
                exp.setDistinct(true);
            });
        }
        return this;
    }

    @Override
    public PageResult<T1> toPageResult(long pageIndex, long pageSize) {
        return doPageResult(pageIndex, pageSize, t1Class);
    }

    protected <TR> PageResult<TR> doPageResult(long pageIndex, long pageSize, Class<TR> clazz) {
        //设置每次获取多少条
        long take = pageSize <= 0 ? 1 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        long total = this.count();
        if (total <= offset) {
            return new PageResult<TR>(total, new ArrayList<>(0));
        }//获取剩余条数
        long remainingCount = total - offset;
        //当剩余条数小于take数就取remainingCount
        long realTake = Math.min(remainingCount, take);
        this.limit(offset, realTake);
        List<TR> data = this.toInternalList(clazz);
        return new PageResult<TR>(total, data);
    }


    @Override
    public SqlEntityQueryExpression getSqlEntityExpression() {
        return sqlEntityExpression;
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> leftJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> rightJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Class<T2> joinClass, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, joinClass, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T2> Queryable2<T1, T2> innerJoin(Queryable<T2> joinQueryable, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on) {
        //todo 需要判断当前的表达式是否存在where group order之类的操作,是否是一个clear expression如果不是那么就需要先select all如果没有select过然后创建一个anonymous的table去join
        //简单理解就是queryable需要支持join操作 还有queryable 和queryable之间如何join

        Queryable<T2> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable2<T1, T2> queryable = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable2(t1Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public Queryable<T1> disableLogicDelete() {
        sqlEntityExpression.getSqlExpressionContext().disableLogicDelete();
        return this;
    }

    @Override
    public Queryable<T1> enableLogicDelete() {
        sqlEntityExpression.getSqlExpressionContext().enableLogicDelete();
        return this;
    }


    @Override
    public Queryable<T1> noInterceptor() {
        sqlEntityExpression.getSqlExpressionContext().noInterceptor();
        return this;
    }

    @Override
    public Queryable<T1> useInterceptor() {
        sqlEntityExpression.getSqlExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public Queryable<T1> asTracking() {
        sqlEntityExpression.getSqlExpressionContext().queryTracking(true);
        return this;
    }

    @Override
    public Queryable<T1> asNoTracking() {
        sqlEntityExpression.getSqlExpressionContext().queryTracking(false);
        return this;
    }

    @Override
    public abstract EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1();
}
