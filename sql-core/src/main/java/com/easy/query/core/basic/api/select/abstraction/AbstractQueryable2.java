package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.Queryable2;
import com.easy.query.core.basic.api.select.Queryable3;
import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider2;
import com.easy.query.core.basic.api.select.provider.Select2SqlProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.lambda.SqlExpression2;
import com.easy.query.core.expression.lambda.SqlExpression3;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.SqlExpressionUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @FileName: AbstractQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @author xuejiaming
 */
public abstract class AbstractQueryable2<T1, T2> extends AbstractQueryable<T1> implements Queryable2<T1, T2> {

    private final Class<T2> t2Class;
    private final EasyQuerySqlBuilderProvider2<T1, T2> sqlPredicateProvider;

    public AbstractQueryable2(Class<T1> t1Class, Class<T2> t2Class, EntityQueryExpression sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;

        this.sqlPredicateProvider = new Select2SqlProvider<>(sqlEntityExpression);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        Queryable3<T1, T2, T3> queryable3 = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> leftJoin(Queryable<T3> joinQueryable, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        Queryable<T3> selectAllT2Queryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable3<T1, T2, T3> queryable3 = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable3(t1Class,t2Class, selectAllT2Queryable, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        Queryable3<T1, T2, T3> queryable3 = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> rightJoin(Queryable<T3> joinQueryable, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        Queryable<T3> selectAllT2Queryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable3<T1, T2, T3> queryable3 = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable3(t1Class,t2Class, selectAllT2Queryable, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        Queryable3<T1, T2, T3> queryable3 = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> innerJoin(Queryable<T3> joinQueryable, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        Queryable<T3> selectAllT2Queryable = SqlExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable3<T1, T2, T3> queryable3 = sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable3(t1Class,t2Class, selectAllT2Queryable, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpression);
        return SqlExpressionUtil.executeJoinOn(queryable3,on);
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
        return null;
    }

    @Override
    protected SqlPredicate<?> matchWhereObjectSqlPredicate(Class<?> entityClass) {
        if(entityClass==t1Class){
            return getSqlBuilderProvider2().getSqlWherePredicate1();
        }
        if(entityClass==t2Class){
            return getSqlBuilderProvider2().getSqlWherePredicate2();
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
            return getSqlBuilderProvider2().getSqlOrderColumnSelector2(asc);
        }
        return null;
    }

    @Override
    public Queryable2<T1, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition,object);
        return this;
    }
    @Override
    public Queryable2<T1, T2> where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression) {
        super.where(condition,whereExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> where(boolean condition, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> whereExpression) {
        if (condition) {
            SqlPredicate<T1> sqlWherePredicate1 = getSqlBuilderProvider2().getSqlWherePredicate1();
            SqlPredicate<T2> sqlWherePredicate2 = getSqlBuilderProvider2().getSqlWherePredicate2();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2);
        }
        return this;
    }


    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression2<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>> selectExpression) {
        SqlColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSqlBuilderProvider2().getSqlColumnAsSelector1(sqlEntityExpression.getProjects(),resultClass);
        SqlColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSqlBuilderProvider2().getSqlColumnAsSelector2(sqlEntityExpression.getProjects(),resultClass);
        selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }

    private <TMember> List<TMember> selectAggregateList(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, EasyFunc easyFunc) {

        ProjectSqlBuilderSegment projectSqlBuilderSegment = new ProjectSqlBuilderSegment();

        SqlColumnResultSelector<T1, TMember> sqlColumnResultSelector1 = getSqlBuilderProvider2().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, TMember> sqlColumnResultSelector2 = getSqlBuilderProvider2().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        SqlEntitySegment sqlSegment = (SqlEntitySegment)projectSqlBuilderSegment.getSqlSegments().get(0);

        EntityTableExpression table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata = EasyUtil.getColumnMetadata(table,propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSqlBuilderSegment.toSql())).toList((Class<TMember>)columnMetadata.getProperty().getPropertyType());
    }
    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        TMember resultMember = ArrayUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MAX);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MIN);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember extends Number> TMember avgOrDefault(SqlExpression2<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.AVG);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(SqlExpression2<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>> columnSelectorExpression, Integer def) {

        ProjectSqlBuilderSegment projectSqlBuilderSegment = new ProjectSqlBuilderSegment();

        SqlColumnResultSelector<T1, ?> sqlColumnResultSelector1 = getSqlBuilderProvider2().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, ?> sqlColumnResultSelector2 = getSqlBuilderProvider2().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }

        List<Integer> result = cloneQueryable().select(EasyAggregate.LEN.getFuncColumn(projectSqlBuilderSegment.toSql())).toList(Integer.class);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public Queryable2<T1, T2> orderByAsc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider2().getSqlOrderColumnSelector1(true);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider2().getSqlOrderColumnSelector2(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }

    @Override
    public Queryable2<T1, T2> orderByDesc(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider2().getSqlOrderColumnSelector1(false);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider2().getSqlOrderColumnSelector2(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }

    @Override
    public Queryable2<T1, T2> groupBy(boolean condition, SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlGroupSelector1 = getSqlBuilderProvider2().getSqlGroupColumnSelector1();
            SqlColumnSelector<T2> sqlGroupSelector2 = getSqlBuilderProvider2().getSqlGroupColumnSelector2();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2);
        }
        return this;
    }
    @Override
    public Queryable2<T1, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public Queryable2<T1, T2> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public Queryable2<T1, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public Queryable2<T1, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }
    @Override
    public Queryable2<T1, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public Queryable2<T1, T2> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public Queryable2<T1, T2> interceptor(String name) {
         super.interceptor(name);
        return this;
    }

    @Override
    public Queryable2<T1, T2> useInterceptor() {
        super.useInterceptor();
        return this;
    }
    @Override
    public Queryable2<T1, T2> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public Queryable2<T1, T2> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public Queryable2<T1, T2> asTable(Function<String,String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }


    public EasyQuerySqlBuilderProvider2<T1, T2> getSqlBuilderProvider2() {
        return sqlPredicateProvider;
    }

    @Override
    public EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }

}
