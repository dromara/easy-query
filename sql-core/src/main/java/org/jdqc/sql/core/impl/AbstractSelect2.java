package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression2;
import org.jdqc.sql.core.abstraction.lambda.SqlExpression3;
import org.jdqc.sql.core.abstraction.sql.Select2;
import org.jdqc.sql.core.abstraction.sql.Select3;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector;
import org.jdqc.sql.core.abstraction.sql.base.WherePredicate;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: AbstractSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @Created by xuejiaming
 */
public abstract  class AbstractSelect2<T1,T2,TR> extends AbstractSelect0<T1,TR, Select2<T1,T2,TR>> implements Select2<T1,T2,TR> {

    public AbstractSelect2(SelectContext selectContext) {
        super(selectContext);
    }

    @Override
    public <T3> Select3<T1, T2, T3, TR> leftJoin(Class<T3> joinClass, SqlExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        return null;
    }

    @Override
    public <T3> Select3<T1, T2, T3, TR> innerJoin(Class<T3> joinClass, SqlExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        return null;
    }

    @Override
    public Select2<T1, T2, TR> where(SqlExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression) {
        return null;
    }

    @Override
    public Select2<T1, T2, TR> select(SqlExpression2<SqlSelector<T1, TR>, SqlSelector<T2, TR>> selectExpression) {
        return null;
    }

    @Override
    public Select2<T1, T2, TR> groupBy(SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return null;
    }

    @Override
    public Select2<T1, T2, TR> orderByAsc(SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return null;
    }

    @Override
    public Select2<T1, T2, TR> orderByDesc(SqlExpression2<SqlColumnSelector<T1>, SqlColumnSelector<T2>> selectExpression) {
        return null;
    }
}
