package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.Queryable2;
import com.easy.query.core.basic.api.select.Queryable3;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

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

    protected final Class<T2> t2Class;
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;

    public AbstractQueryable2(Class<T1> t1Class, Class<T2> t2Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        Queryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> leftJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        Queryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        Queryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable3(t1Class,t2Class, selectAllT2Queryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        Queryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> rightJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        Queryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        Queryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable3(t1Class,t2Class, selectAllT2Queryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        Queryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3,on);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> innerJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        Queryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        Queryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable3(t1Class,t2Class, selectAllT2Queryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3,on);
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
    protected SQLWherePredicate<?> matchWhereObjectSQLPredicate(Class<?> entityClass) {
        if(entityClass==t1Class){
            return getSQLExpressionProvider1().getSQLWherePredicate();
        }
        if(entityClass==t2Class){
            return getSQLExpressionProvider2().getSQLWherePredicate();
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
            return getSQLExpressionProvider2().getSQLOrderColumnSelector(asc);
        }
        return null;
    }

    @Override
    public Queryable2<T1, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition,object);
        return this;
    }
    @Override
    public Queryable2<T1, T2> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition,whereExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> where(boolean condition, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> whereExpression) {
        if (condition) {
            SQLWherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getSQLWherePredicate();
            SQLWherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getSQLWherePredicate();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2);
        }
        return this;
    }


    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>> selectExpression) {
        SQLColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getSQLColumnAsSelector(entityQueryExpressionBuilder.getProjects(),resultClass);
        SQLColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getSQLColumnAsSelector(entityQueryExpressionBuilder.getProjects(),resultClass);
        selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    private <TMember> List<TMember> selectAggregateList(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, ColumnFunction easyFunc) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        SQLColumnResultSelector<T1, TMember> sqlColumnResultSelector1 = getSQLExpressionProvider1().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T2, TMember> sqlColumnResultSelector2 = getSQLExpressionProvider2().getSQLColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2);
        if(projectSQLBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment)projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata =table.getEntityMetadata().getColumnNotNull(propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList((Class<TMember>)columnMetadata.getPropertyType());
    }
    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember extends Number> TMember avgOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, avgFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(SQLExpression2<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>> columnSelectorExpression, Integer def) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        SQLColumnResultSelector<T1, ?> sqlColumnResultSelector1 = getSQLExpressionProvider1().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T2, ?> sqlColumnResultSelector2 = getSQLExpressionProvider2().getSQLColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2);
        if(projectSQLBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        ColumnFunction lenFunction = runtimeContext.getColumnFunctionFactory().createLenFunction();
        List<Integer> result = cloneQueryable().select(lenFunction.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList(Integer.class);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition,selectExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLColumnSelector<T1>, SQLColumnSelector<T2>> selectExpression) {
        if (condition) {
            SQLColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getSQLOrderColumnSelector(true);
            SQLColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getSQLOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }

    @Override
    public Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition,selectExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLColumnSelector<T1>, SQLColumnSelector<T2>> selectExpression) {
        if (condition) {
            SQLColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getSQLOrderColumnSelector(false);
            SQLColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getSQLOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }

    @Override
    public Queryable2<T1,T2> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression){
        super.groupBy(condition,selectExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>> selectExpression) {
        if (condition) {
            SQLGroupBySelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getSQLGroupColumnSelector();
            SQLGroupBySelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getSQLGroupColumnSelector();
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
    public Queryable2<T1, T2> useInterceptor(String name) {
         super.useInterceptor(name);
        return this;
    }

    @Override
    public Queryable2<T1, T2> noInterceptor(String name) {
         super.noInterceptor(name);
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
    public Queryable2<T1, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public Queryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode){
        super.useShardingConfigure(maxShardingQueryLimit,connectionMode);
        return this;
    }
    @Override
    public Queryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit){
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }
    @Override
    public  Queryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode){
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public Queryable2<T1, T2> asTable(Function<String,String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public SQLExpressionProvider<T2> getSQLExpressionProvider2(){
        if(sqlExpressionProvider2==null){
            sqlExpressionProvider2=runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(1,this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider2;
    }

}
