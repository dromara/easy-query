package org.easy.query.core.basic.api.select;

import org.easy.query.core.expression.lambda.SqlExpression3;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.context.SelectContext;

/**
 * @FileName: AbstractSelect3.java
 * @Description: 文件说明
 * @Date: 2023/2/8 23:13
 * @Created by xuejiaming
 */
public abstract class AbstractSelect3<T1,T2,T3> extends AbstractSelect0<T1, Queryable3<T1,T2,T3>> implements Queryable3<T1,T2,T3> {
    private final SelectContext selectContext;

    public AbstractSelect3(Class<T1> t1Class, SelectContext selectContext) {
        super(t1Class,selectContext);
        this.selectContext = selectContext;
    }


    @Override
    protected Queryable3<T1, T2, T3> castSelf() {
        return this;
    }


    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> whereExpression) {
        return null;
    }

    @Override
    public Queryable3<T1, T2, T3> groupBy(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }
}
