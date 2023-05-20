package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.basic.api.select.Queryable4;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 * @author xuejiaming
 */
public abstract class AbstractQueryable4<T1, T2, T3,T4> extends AbstractQueryable<T1> implements Queryable4<T1, T2, T3,T4> {
    private final Class<T2> t2Class;
    private final Class<T3> t3Class;
    private final Class<T4> t4Class;
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;
    protected SQLExpressionProvider<T3> sqlExpressionProvider3;
    protected SQLExpressionProvider<T4> sqlExpressionProvider4;


    public AbstractQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
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
        if(Objects.equals(t4Class,propertyQueryEntityClass)){
            return t4Class;
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
        if(entityClass==t3Class){
            return getSQLExpressionProvider3().getSQLWherePredicate();
        }
        if(entityClass==t4Class){
            return getSQLExpressionProvider4().getSQLWherePredicate();
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
        if(entityClass==t3Class){
            return getSQLExpressionProvider3().getSQLOrderColumnSelector(asc);
        }
        if(entityClass==t4Class){
            return getSQLExpressionProvider4().getSQLOrderColumnSelector(asc);
        }
        return null;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition,object);
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition,whereExpression);
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> where(boolean condition, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression) {
        if (condition) {
            SQLWherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getSQLWherePredicate();
            SQLWherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getSQLWherePredicate();
            SQLWherePredicate<T3> sqlWherePredicate3 = getSQLExpressionProvider3().getSQLWherePredicate();
            SQLWherePredicate<T4> sqlWherePredicate4 = getSQLExpressionProvider4().getSQLWherePredicate();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2,sqlWherePredicate3,sqlWherePredicate4);
        }
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression4<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>> selectExpression) {

        SQLColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getSQLColumnAsSelector(entityQueryExpressionBuilder.getProjects(),resultClass);
        SQLColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getSQLColumnAsSelector(entityQueryExpressionBuilder.getProjects(),resultClass);
        SQLColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getSQLColumnAsSelector(entityQueryExpressionBuilder.getProjects(),resultClass);
        SQLColumnAsSelector<T4, TR> sqlColumnAsSelector4 = getSQLExpressionProvider4().getSQLColumnAsSelector(entityQueryExpressionBuilder.getProjects(),resultClass);
        selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3,sqlColumnAsSelector4);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }
    private <TMember> List<TMember> selectAggregateList(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, ColumnFunction easyFunc) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        SQLColumnResultSelector<T1, TMember> sqlColumnResultSelector1 = getSQLExpressionProvider1().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T2, TMember> sqlColumnResultSelector2 = getSQLExpressionProvider2().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T3, TMember> sqlColumnResultSelector3 = getSQLExpressionProvider3().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T4, TMember> sqlColumnResultSelector4 = getSQLExpressionProvider4().getSQLColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3,sqlColumnResultSelector4);
        if(projectSQLBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment)projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList((Class<TMember>)columnMetadata.getProperty().getPropertyType());
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember avgOrDefault(SQLExpression4<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, avgFunction);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(SQLExpression4<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>, SQLColumnResultSelector<T3, ?>, SQLColumnResultSelector<T4, ?>> columnSelectorExpression, Integer def) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        SQLColumnResultSelector<T1, ?> sqlColumnResultSelector1 = getSQLExpressionProvider1().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T2, ?> sqlColumnResultSelector2 = getSQLExpressionProvider2().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T3, ?> sqlColumnResultSelector3 = getSQLExpressionProvider3().getSQLColumnResultSelector(projectSQLBuilderSegment);
        SQLColumnResultSelector<T4, ?> sqlColumnResultSelector4 = getSQLExpressionProvider4().getSQLColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3,sqlColumnResultSelector4);
        if(projectSQLBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        ColumnFunction lenFunction = runtimeContext.getColumnFunctionFactory().createLenFunction();
        List<Integer> result =  cloneQueryable().select(lenFunction.getFuncColumn(projectSQLBuilderSegment.toSQL(null))).toList(Integer.class);
        return EasyCollectionUtil.firstOrDefault(result,def);
    }

    @Override
    public Queryable4<T1, T2, T3,T4> groupBy(boolean condition, SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression) {
        if (condition) {
            SQLGroupBySelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getSQLGroupColumnSelector();
            SQLGroupBySelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getSQLGroupColumnSelector();
            SQLGroupBySelector<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getSQLGroupColumnSelector();
            SQLGroupBySelector<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getSQLGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2,sqlGroupSelector3,sqlGroupSelector4);
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> orderByAsc(boolean condition, SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression) {
        if (condition) {
            SQLColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getSQLOrderColumnSelector(true);
            SQLColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getSQLOrderColumnSelector(true);
            SQLColumnSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getSQLOrderColumnSelector(true);
            SQLColumnSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getSQLOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3,sqlOrderColumnSelector4);
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> orderByDesc(boolean condition, SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression) {
        if (condition) {
            SQLColumnSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getSQLOrderColumnSelector(false);
            SQLColumnSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getSQLOrderColumnSelector(false);
            SQLColumnSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getSQLOrderColumnSelector(false);
            SQLColumnSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getSQLOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3,sqlOrderColumnSelector4);
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> noInterceptor() {
        super.noInterceptor();
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> useInterceptor() {
        super.useInterceptor();
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> asNoTracking() {
        super.asNoTracking();
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode){
        super.useShardingConfigure(maxShardingQueryLimit,connectionMode);
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> useMaxShardingQueryLimit(int maxShardingQueryLimit){
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }
    @Override
    public  Queryable4<T1, T2, T3,T4> useConnectionMode(ConnectionModeEnum connectionMode){
        super.useConnectionMode(connectionMode);
        return this;
    }
    @Override
    public Queryable4<T1, T2, T3,T4> asTable(Function<String,String> tableNameAs) {
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

    @Override
    public SQLExpressionProvider<T3> getSQLExpressionProvider3(){
        if(sqlExpressionProvider3==null){
            sqlExpressionProvider3=runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(2,this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider3;
    }
    @Override
    public SQLExpressionProvider<T4> getSQLExpressionProvider4(){
        if(sqlExpressionProvider4==null){
            sqlExpressionProvider4=runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(3,this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider4;
    }
}
