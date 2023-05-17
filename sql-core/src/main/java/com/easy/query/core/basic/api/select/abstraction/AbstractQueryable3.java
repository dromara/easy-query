package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.provider.EasyQuerySQLBuilderProvider;
import com.easy.query.core.basic.api.select.provider.EasyQuerySQLBuilderProvider3;
import com.easy.query.core.basic.api.select.provider.Select3SQLProvider;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.select.Queryable3;
import com.easy.query.core.basic.api.select.Queryable4;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.SQLExpressionUtil;

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
    private final EasyQuerySQLBuilderProvider3<T1, T2, T3> sqlPredicateProvider;

    public AbstractQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;

        this.sqlPredicateProvider = new Select3SQLProvider<>(sqlEntityExpression);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        Queryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable4(t1Class, t2Class,t3Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return SQLExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> leftJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        Queryable<T4> selectAllTQueryable = SQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable4<T1, T2, T3,T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable4(t1Class,t2Class,t3Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return SQLExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        Queryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable4(t1Class, t2Class,t3Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return SQLExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> rightJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        Queryable<T4> selectAllTQueryable = SQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable4<T1, T2, T3,T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable4(t1Class,t2Class,t3Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return SQLExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        Queryable4<T1, T2, T3, T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable4(t1Class, t2Class,t3Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return SQLExpressionUtil.executeJoinOn(queryable,on);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> innerJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        Queryable<T4> selectAllTQueryable = SQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        Queryable4<T1, T2, T3,T4> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable4(t1Class,t2Class,t3Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return SQLExpressionUtil.executeJoinOn(queryable,on);
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
    protected SQLWherePredicate<?> matchWhereObjectSQLPredicate(Class<?> entityClass) {
        if(entityClass==t1Class){
            return getSQLBuilderProvider3().getSQLWherePredicate1();
        }
        if(entityClass==t2Class){
            return getSQLBuilderProvider3().getSQLWherePredicate2();
        }
        if(entityClass==t3Class){
            return getSQLBuilderProvider3().getSQLWherePredicate3();
        }
        return null;
    }

    @Override
    protected SQLColumnSelector<?> matchOrderBySQLColumnSelector(Class<?> entityClass, boolean asc) {

        SQLColumnSelector<?> sqlColumnSelector = super.matchOrderBySQLColumnSelector(entityClass, asc);
        if(sqlColumnSelector!=null){
            return sqlColumnSelector;
        }
        if(entityClass==t2Class){
            return getSQLBuilderProvider3().getSQLOrderColumnSelector2(asc);
        }
        if(entityClass==t3Class){
            return getSQLBuilderProvider3().getSQLOrderColumnSelector3(asc);
        }
        return null;
    }

    @Override
    public Queryable3<T1, T2, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition,object);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition,whereExpression);
        return this;
    }
    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression) {
        if (condition) {
            SQLWherePredicate<T1> sqlWherePredicate1 = getSQLBuilderProvider3().getSQLWherePredicate1();
            SQLWherePredicate<T2> sqlWherePredicate2 = getSQLBuilderProvider3().getSQLWherePredicate2();
            SQLWherePredicate<T3> sqlWherePredicate3 = getSQLBuilderProvider3().getSQLWherePredicate3();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2,sqlWherePredicate3);
        }
         return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression3<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>> selectExpression) {

        SQLColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLBuilderProvider3().getSQLColumnAsSelector1(entityQueryExpressionBuilder.getProjects(),resultClass);
        SQLColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLBuilderProvider3().getSQLColumnAsSelector2(entityQueryExpressionBuilder.getProjects(),resultClass);
        SQLColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLBuilderProvider3().getSQLColumnAsSelector3(entityQueryExpressionBuilder.getProjects(),resultClass);
        selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }
    private <TMember> List<TMember> selectAggregateList(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, ColumnFunction easyFunc) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        SQLColumnResultSelector<T1, TMember> sqlColumnResultSelector1 = getSQLBuilderProvider3().getSQLColumnResultSelector1(projectSQLBuilderSegment);
        SQLColumnResultSelector<T2, TMember> sqlColumnResultSelector2 = getSQLBuilderProvider3().getSQLColumnResultSelector2(projectSQLBuilderSegment);
        SQLColumnResultSelector<T3, TMember> sqlColumnResultSelector3 = getSQLBuilderProvider3().getSQLColumnResultSelector3(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3);
        if(projectSQLBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment)projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata =table.getEntityMetadata().getColumnNotNull(propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList((Class<TMember>)columnMetadata.getProperty().getPropertyType());
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember avgOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, avgFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(SQLExpression3<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>, SQLColumnResultSelector<T3, ?>> columnSelectorExpression, Integer def) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        SQLColumnResultSelector<T1, ?> sqlColumnResultSelector1 = getSQLBuilderProvider3().getSQLColumnResultSelector1(projectSQLBuilderSegment);
        SQLColumnResultSelector<T2, ?> sqlColumnResultSelector2 = getSQLBuilderProvider3().getSQLColumnResultSelector2(projectSQLBuilderSegment);
        SQLColumnResultSelector<T3, ?> sqlColumnResultSelector3 = getSQLBuilderProvider3().getSQLColumnResultSelector3(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3);
        if(projectSQLBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }

        ColumnFunction lenFunction = runtimeContext.getColumnFunctionFactory().createLenFunction();
        List<Integer> result =  cloneQueryable().select(lenFunction.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList(Integer.class);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Queryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression) {
        if (condition) {
            SQLGroupBySelector<T1> sqlGroupSelector1 = getSQLBuilderProvider3().getSQLGroupColumnSelector1();
            SQLGroupBySelector<T2> sqlGroupSelector2 = getSQLBuilderProvider3().getSQLGroupColumnSelector2();
            SQLGroupBySelector<T3> sqlGroupSelector3 = getSQLBuilderProvider3().getSQLGroupColumnSelector3();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2,sqlGroupSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression) {
        if (condition) {
            SQLColumnSelector<T1> sqlOrderColumnSelector1 = getSQLBuilderProvider3().getSQLOrderColumnSelector1(true);
            SQLColumnSelector<T2> sqlOrderColumnSelector2 = getSQLBuilderProvider3().getSQLOrderColumnSelector2(true);
            SQLColumnSelector<T3> sqlOrderColumnSelector3 = getSQLBuilderProvider3().getSQLOrderColumnSelector3(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression) {
        if (condition) {
            SQLColumnSelector<T1> sqlOrderColumnSelector1 = getSQLBuilderProvider3().getSQLOrderColumnSelector1(false);
            SQLColumnSelector<T2> sqlOrderColumnSelector2 = getSQLBuilderProvider3().getSQLOrderColumnSelector2(false);
            SQLColumnSelector<T3> sqlOrderColumnSelector3 = getSQLBuilderProvider3().getSQLOrderColumnSelector3(false);
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

    public EasyQuerySQLBuilderProvider3<T1, T2, T3> getSQLBuilderProvider3() {
        return sqlPredicateProvider;
    }

    @Override
    public EasyQuerySQLBuilderProvider<T1> getSQLBuilderProvider1() {
        return sqlPredicateProvider;
    }
}
