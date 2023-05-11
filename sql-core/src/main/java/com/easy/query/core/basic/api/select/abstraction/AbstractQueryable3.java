package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider3;
import com.easy.query.core.basic.api.select.provider.Select3SqlProvider;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.core.SqlGroupBySelector;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.select.Queryable3;
import com.easy.query.core.basic.api.select.Queryable4;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.lambda.SqlExpression3;
import com.easy.query.core.expression.lambda.SqlExpression4;
import com.easy.query.core.expression.parser.core.SqlColumnResultSelector;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.SqlExpressionUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @FileName: AbstractQueryable3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @author xuejiaming
 */
public abstract class AbstractQueryable3<T1, T2, T3> extends AbstractQueryable<T1> implements Queryable3<T1, T2, T3> {

    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    private final EasyQuerySqlBuilderProvider3<T1, T2, T3> sqlPredicateProvider;

    public AbstractQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;

        this.sqlPredicateProvider = new Select3SqlProvider<>(sqlEntityExpression);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SqlExpression4<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>, SqlWherePredicate<T4>> on) {
        Queryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable4(t1Class, t2Class,t3Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> leftJoin(Queryable<T4> joinQueryable, SqlExpression4<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>, SqlWherePredicate<T4>> on) {
        Queryable<T4> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable4<T1, T2, T3,T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable4(t1Class,t2Class,t3Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SqlExpression4<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>, SqlWherePredicate<T4>> on) {
        Queryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable4(t1Class, t2Class,t3Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> rightJoin(Queryable<T4> joinQueryable, SqlExpression4<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>, SqlWherePredicate<T4>> on) {
        Queryable<T4> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable4<T1, T2, T3,T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable4(t1Class,t2Class,t3Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SqlExpression4<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>, SqlWherePredicate<T4>> on) {
        Queryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable4(t1Class, t2Class,t3Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> innerJoin(Queryable<T4> joinQueryable, SqlExpression4<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>, SqlWherePredicate<T4>> on) {
        Queryable<T4> selectAllTQueryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable4<T1, T2, T3,T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable4(t1Class,t2Class,t3Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return SqlExpressionUtil.executeJoinOn(queryable,on);
    }


    @Override
    protected Class<?> matchQueryEntityClass(Class<?> propertyQueryEntityClass) {

        Class<?> queryEntityClass = super.matchQueryEntityClass(propertyQueryEntityClass);
        if(queryEntityClass!=null){
            return queryEntityClass;
        }
        if(Objects.equals(t2Class,propertyQueryEntityClass)){
            return t2Class;
        }
        if(Objects.equals(t3Class,propertyQueryEntityClass)){
            return t3Class;
        }
        return null;
    }

    @Override
    protected SqlWherePredicate<?> matchWhereObjectSqlPredicate(Class<?> entityClass) {
        if(entityClass==t1Class){
            return getSqlBuilderProvider3().getSqlWherePredicate1();
        }
        if(entityClass==t2Class){
            return getSqlBuilderProvider3().getSqlWherePredicate2();
        }
        if(entityClass==t3Class){
            return getSqlBuilderProvider3().getSqlWherePredicate3();
        }
        return null;
    }

    @Override
    protected SqlColumnSelector<?> matchOrderBySqlColumnSelector(Class<?> entityClass, boolean asc) {

        SqlColumnSelector<?> sqlColumnSelector = super.matchOrderBySqlColumnSelector(entityClass, asc);
        if(sqlColumnSelector!=null){
            return sqlColumnSelector;
        }
        if(entityClass==t2Class){
            return getSqlBuilderProvider3().getSqlOrderColumnSelector2(asc);
        }
        if(entityClass==t3Class){
            return getSqlBuilderProvider3().getSqlOrderColumnSelector3(asc);
        }
        return null;
    }

    @Override
    public Queryable3<T1, T2, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition,object);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SqlExpression<SqlWherePredicate<T1>> whereExpression) {
        super.where(condition,whereExpression);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SqlExpression3<SqlWherePredicate<T1>, SqlWherePredicate<T2>, SqlWherePredicate<T3>> whereExpression) {
        if (condition) {
            SqlWherePredicate<T1> sqlWherePredicate1 = getSqlBuilderProvider3().getSqlWherePredicate1();
            SqlWherePredicate<T2> sqlWherePredicate2 = getSqlBuilderProvider3().getSqlWherePredicate2();
            SqlWherePredicate<T3> sqlWherePredicate3 = getSqlBuilderProvider3().getSqlWherePredicate3();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2,sqlWherePredicate3);
        }
         return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression3<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>, SqlColumnAsSelector<T3, TR>> selectExpression) {

        SqlColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSqlBuilderProvider3().getSqlColumnAsSelector1(entityQueryExpressionBuilder.getProjects(),resultClass);
        SqlColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSqlBuilderProvider3().getSqlColumnAsSelector2(entityQueryExpressionBuilder.getProjects(),resultClass);
        SqlColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSqlBuilderProvider3().getSqlColumnAsSelector3(entityQueryExpressionBuilder.getProjects(),resultClass);
        selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3);
        return entityQueryExpressionBuilder.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }
    private <TMember> List<TMember> selectAggregateList(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, EasyFunc easyFunc) {

        ProjectSqlBuilderSegmentImpl projectSqlBuilderSegment = new ProjectSqlBuilderSegmentImpl();

        SqlColumnResultSelector<T1, TMember> sqlColumnResultSelector1 = getSqlBuilderProvider3().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, TMember> sqlColumnResultSelector2 = getSqlBuilderProvider3().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        SqlColumnResultSelector<T3, TMember> sqlColumnResultSelector3 = getSqlBuilderProvider3().getSqlColumnResultSelector3(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        SqlEntitySegment sqlSegment = (SqlEntitySegment)projectSqlBuilderSegment.getSqlSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata =table.getEntityMetadata().getColumnNotNull(propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSqlBuilderSegment.toSql(null))).toList((Class<TMember>)columnMetadata.getProperty().getPropertyType());
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MAX);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MIN);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember avgOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.AVG);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(SqlExpression3<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>, SqlColumnResultSelector<T3, ?>> columnSelectorExpression, Integer def) {

        ProjectSqlBuilderSegmentImpl projectSqlBuilderSegment = new ProjectSqlBuilderSegmentImpl();

        SqlColumnResultSelector<T1, ?> sqlColumnResultSelector1 = getSqlBuilderProvider3().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, ?> sqlColumnResultSelector2 = getSqlBuilderProvider3().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        SqlColumnResultSelector<T3, ?> sqlColumnResultSelector3 = getSqlBuilderProvider3().getSqlColumnResultSelector3(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }

        List<Integer> result =  cloneQueryable().select(EasyAggregate.LEN.getFuncColumn(projectSqlBuilderSegment.toSql(null))).toList(Integer.class);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Queryable3<T1, T2, T3> groupBy(boolean condition, SqlExpression3<SqlGroupBySelector<T1>, SqlGroupBySelector<T2>, SqlGroupBySelector<T3>> selectExpression) {
        if (condition) {
            SqlGroupBySelector<T1> sqlGroupSelector1 = getSqlBuilderProvider3().getSqlGroupColumnSelector1();
            SqlGroupBySelector<T2> sqlGroupSelector2 = getSqlBuilderProvider3().getSqlGroupColumnSelector2();
            SqlGroupBySelector<T3> sqlGroupSelector3 = getSqlBuilderProvider3().getSqlGroupColumnSelector3();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2,sqlGroupSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider3().getSqlOrderColumnSelector1(true);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider3().getSqlOrderColumnSelector2(true);
            SqlColumnSelector<T3> sqlOrderColumnSelector3 = getSqlBuilderProvider3().getSqlOrderColumnSelector3(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider3().getSqlOrderColumnSelector1(false);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider3().getSqlOrderColumnSelector2(false);
            SqlColumnSelector<T3> sqlOrderColumnSelector3 = getSqlBuilderProvider3().getSqlOrderColumnSelector3(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> noInterceptor() {
        super.noInterceptor();
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useInterceptor() {
        super.useInterceptor();
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> asNoTracking() {
        super.asNoTracking();
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode){
        super.useShardingConfigure(maxShardingQueryLimit,connectionMode);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit){
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }
    @Override
    public  Queryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode){
        super.useConnectionMode(connectionMode);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> asTable(Function<String,String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    public EasyQuerySqlBuilderProvider3<T1, T2, T3> getSqlBuilderProvider3() {
        return sqlPredicateProvider;
    }

    @Override
    public EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }
}
