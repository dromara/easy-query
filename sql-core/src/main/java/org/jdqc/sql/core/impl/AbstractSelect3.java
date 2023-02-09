package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression3;
import org.jdqc.sql.core.abstraction.sql.Select3;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;

/**
 * @FileName: AbstractSelect3.java
 * @Description: 文件说明
 * @Date: 2023/2/8 23:13
 * @Created by xuejiaming
 */
public abstract class AbstractSelect3<T1,T2,T3> extends AbstractSelect0<T1, Select3<T1,T2,T3>> implements Select3<T1,T2,T3> {
    private final MySQLSelectContext selectContext;

    public AbstractSelect3(Class<T1> t1Class, MySQLSelectContext selectContext) {
        super(t1Class,selectContext);
        this.selectContext = selectContext;
    }


    @Override
    protected Select3<T1, T2, T3> getSelf() {
        return this;
    }

    @Override
    protected Select1SqlProvider<T1> getSelect1SqlPredicateProvider() {
        return null;
    }

    @Override
    public Select3<T1, T2, T3> where(boolean condition, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> whereExpression) {
        return null;
    }

    @Override
    public Select3<T1, T2, T3> groupBy(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }

    @Override
    public Select3<T1, T2, T3> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }

    @Override
    public Select3<T1, T2, T3> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }
}
