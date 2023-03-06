package org.easy.query.core.basic.api.select;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider;
import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider2;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.lambda.SqlExpression3;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnResultSelector;
import org.easy.query.core.impl.Select2SqlProvider;
import org.easy.query.core.query.SqlEntityQueryExpression;

import java.math.BigDecimal;

/**
 * @FileName: AbstractSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @Created by xuejiaming
 */
public abstract class AbstractQueryable2<T1, T2> extends AbstractQueryable<T1> implements Queryable2<T1, T2> {

    private final EasyQuerySqlBuilderProvider2<T1, T2> sqlPredicateProvider;

    public AbstractQueryable2(Class<T1> t1Class, Class<T2> t2Class, SqlEntityQueryExpression sqlEntityExpression, MultiTableTypeEnum selectTableInfoType) {
        super(t1Class, sqlEntityExpression);

        this.sqlPredicateProvider = new Select2SqlProvider<>(sqlEntityExpression);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        return null;
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> on) {
        return null;
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

    public EasyQuerySqlBuilderProvider2<T1, T2> getSqlBuilderProvider2() {
        return sqlPredicateProvider;
    }

    @Override
    public EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }

    @Override
    public Queryable2<T1, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression2<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>> selectExpression) {
        //todo
//        getSqlBuilderProvider2().getSqlColumnAsSelector2()
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }
    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression2<ColumnResultSelector<T1, TMember>, ColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return null;
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SqlExpression2<ColumnResultSelector<T1, TMember>, ColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return null;
    }

    @Override
    public <TMember> TMember maxOrDefault(SqlExpression2<ColumnResultSelector<T1, TMember>, ColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return null;
    }

    @Override
    public <TMember> TMember minOrDefault(SqlExpression2<ColumnResultSelector<T1, TMember>, ColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return null;
    }

    @Override
    public <TMember> TMember avgOrDefault(SqlExpression2<ColumnResultSelector<T1, TMember>, ColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return null;
    }
}
