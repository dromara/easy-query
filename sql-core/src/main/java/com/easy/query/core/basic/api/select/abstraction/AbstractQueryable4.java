package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.abstraction.metadata.ColumnMetadata;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider;
import com.easy.query.core.basic.api.select.provider.Select4SqlProvider;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.IEasyFunc;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression4;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.basic.api.select.Queryable4;
import com.easy.query.core.basic.api.select.provider.EasyQuerySqlBuilderProvider4;

import java.math.BigDecimal;
import java.util.List;

/**
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 * @Created by xuejiaming
 */
public abstract class AbstractQueryable4<T1, T2, T3,T4> extends AbstractQueryable<T1> implements Queryable4<T1, T2, T3,T4> {
    private final Class<T2> t2Class;
    private final Class<T3> t3Class;
    private final Class<T4> t4Class;   
    private final EasyQuerySqlBuilderProvider4<T1, T2, T3,T4> sqlPredicateProvider;


    public AbstractQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, SqlEntityQueryExpression sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
        this.sqlPredicateProvider=new Select4SqlProvider<>(sqlEntityExpression);
    }


    @Override
    public Queryable4<T1, T2, T3,T4> where(boolean condition, SqlExpression4<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>, SqlPredicate<T4>> whereExpression) {
        if (condition) {
            SqlPredicate<T1> sqlWherePredicate1 = getSqlBuilderProvider4().getSqlWherePredicate1();
            SqlPredicate<T2> sqlWherePredicate2 = getSqlBuilderProvider4().getSqlWherePredicate2();
            SqlPredicate<T3> sqlWherePredicate3 = getSqlBuilderProvider4().getSqlWherePredicate3();
            SqlPredicate<T4> sqlWherePredicate4 = getSqlBuilderProvider4().getSqlWherePredicate4();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2,sqlWherePredicate3,sqlWherePredicate4);
        }
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression4<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>, SqlColumnAsSelector<T3, TR>, SqlColumnAsSelector<T4, TR>> selectExpression) {

        SqlColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSqlBuilderProvider4().getSqlColumnAsSelector1(sqlEntityExpression.getProjects());
        SqlColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSqlBuilderProvider4().getSqlColumnAsSelector2(sqlEntityExpression.getProjects());
        SqlColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSqlBuilderProvider4().getSqlColumnAsSelector3(sqlEntityExpression.getProjects());
        SqlColumnAsSelector<T4, TR> sqlColumnAsSelector4 = getSqlBuilderProvider4().getSqlColumnAsSelector4(sqlEntityExpression.getProjects());
        selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3,sqlColumnAsSelector4);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }
    private <TMember> List<TMember> selectAggregateList(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, IEasyFunc easyFunc) {

        ProjectSqlBuilderSegment projectSqlBuilderSegment = new ProjectSqlBuilderSegment();

        SqlColumnResultSelector<T1, TMember> sqlColumnResultSelector1 = getSqlBuilderProvider4().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, TMember> sqlColumnResultSelector2 = getSqlBuilderProvider4().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        SqlColumnResultSelector<T3, TMember> sqlColumnResultSelector3 = getSqlBuilderProvider4().getSqlColumnResultSelector3(projectSqlBuilderSegment);
        SqlColumnResultSelector<T4, TMember> sqlColumnResultSelector4 = getSqlBuilderProvider4().getSqlColumnResultSelector4(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3,sqlColumnResultSelector4);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        SqlEntitySegment sqlSegment = (SqlEntitySegment)projectSqlBuilderSegment.getSqlSegments().get(0);

        SqlEntityTableExpression table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata = EasyUtil.getColumnMetadata(table,propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSqlBuilderSegment.toSql())).toList((Class<TMember>)columnMetadata.getProperty().getPropertyType());
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        TMember resultMember = ArrayUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MAX);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MIN);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember avgOrDefault(SqlExpression4<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>, SqlColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.AVG);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(SqlExpression4<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>, SqlColumnResultSelector<T3, ?>, SqlColumnResultSelector<T4, ?>> columnSelectorExpression, Integer def) {

        ProjectSqlBuilderSegment projectSqlBuilderSegment = new ProjectSqlBuilderSegment();

        SqlColumnResultSelector<T1, ?> sqlColumnResultSelector1 = getSqlBuilderProvider4().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, ?> sqlColumnResultSelector2 = getSqlBuilderProvider4().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        SqlColumnResultSelector<T3, ?> sqlColumnResultSelector3 = getSqlBuilderProvider4().getSqlColumnResultSelector3(projectSqlBuilderSegment);
        SqlColumnResultSelector<T4, ?> sqlColumnResultSelector4 = getSqlBuilderProvider4().getSqlColumnResultSelector4(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3,sqlColumnResultSelector4);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }

        List<Integer> result =  cloneQueryable().select(EasyAggregate.LEN.getFuncColumn(projectSqlBuilderSegment.toSql())).toList(Integer.class);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public Queryable4<T1, T2, T3,T4> groupBy(boolean condition, SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlGroupSelector1 = getSqlBuilderProvider4().getSqlGroupColumnSelector1();
            SqlColumnSelector<T2> sqlGroupSelector2 = getSqlBuilderProvider4().getSqlGroupColumnSelector2();
            SqlColumnSelector<T3> sqlGroupSelector3 = getSqlBuilderProvider4().getSqlGroupColumnSelector3();
            SqlColumnSelector<T4> sqlGroupSelector4 = getSqlBuilderProvider4().getSqlGroupColumnSelector4();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2,sqlGroupSelector3,sqlGroupSelector4);
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> orderByAsc(boolean condition, SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider4().getSqlOrderColumnSelector1(true);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider4().getSqlOrderColumnSelector2(true);
            SqlColumnSelector<T3> sqlOrderColumnSelector3 = getSqlBuilderProvider4().getSqlOrderColumnSelector3(true);
            SqlColumnSelector<T4> sqlOrderColumnSelector4 = getSqlBuilderProvider4().getSqlOrderColumnSelector4(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3,sqlOrderColumnSelector4);
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> orderByDesc(boolean condition, SqlExpression4<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>, SqlColumnSelector<T4>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider4().getSqlOrderColumnSelector1(false);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider4().getSqlOrderColumnSelector2(false);
            SqlColumnSelector<T3> sqlOrderColumnSelector3 = getSqlBuilderProvider4().getSqlOrderColumnSelector3(false);
            SqlColumnSelector<T4> sqlOrderColumnSelector4 = getSqlBuilderProvider4().getSqlOrderColumnSelector4(false);
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
    public Queryable4<T1, T2, T3,T4> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    public EasyQuerySqlBuilderProvider4<T1, T2, T3,T4> getSqlBuilderProvider4() {
        return sqlPredicateProvider;
    }

    @Override
    public EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }
}
