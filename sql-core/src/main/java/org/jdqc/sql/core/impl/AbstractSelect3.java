package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression3;
import org.jdqc.sql.core.abstraction.sql.Select3;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelectColumnSelector;

import java.util.List;

/**
 * @FileName: AbstractSelect3.java
 * @Description: 文件说明
 * @Date: 2023/2/8 23:13
 * @Created by xuejiaming
 */
public abstract class AbstractSelect3<T1,T2,T3,TR> extends AbstractSelect0<T1,TR, Select3<T1,T2,T3,TR>> implements Select3<T1,T2,T3,TR> {
    public AbstractSelect3(SelectContext selectContext) {
        super(selectContext);
    }


    @Override
    protected Select3<T1, T2, T3, TR> getSelf() {
        return this;
    }

    @Override
    protected Select1SqlProvider<T1, TR> getSelect1SqlPredicateProvider() {
        return null;
    }

    @Override
    public Select3<T1, T2, T3, TR> where(boolean condition, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> whereExpression) {
        return null;
    }

    @Override
    public Select3<T1, T2, T3, TR> select(boolean condition, SqlExpression3<SqlSelectColumnSelector<T1, TR>, SqlSelectColumnSelector<T2, TR>, SqlSelectColumnSelector<T3, TR>> selectExpression) {
        return null;
    }

    @Override
    public Select3<T1, T2, T3, TR> groupBy(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }

    @Override
    public Select3<T1, T2, T3, TR> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }

    @Override
    public Select3<T1, T2, T3, TR> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        return null;
    }
}
