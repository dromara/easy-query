package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.sql.core.impl.lambda.DefaultSqlPredicate;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select2SqlPredicateProvider<T1,T2> extends Select1SqlPredicateProvider<T1> {
    private final DefaultSqlPredicate<T2> sqlPredicate2;

    public Select2SqlPredicateProvider(SelectContext selectContext) {
        super(selectContext);
        this.sqlPredicate2 = new DefaultSqlPredicate<>(1,selectContext, PredicateModeEnum.WHERE_PREDICATE);
    }

    public SqlPredicate<T2> getSqlPredicate2(PredicateModeEnum predicateMode) {
        sqlPredicate2.setPredicateMode(predicateMode);
        return sqlPredicate2;
    }
    public SqlPredicate<T2> getSqlWherePredicate2() {
        return getSqlPredicate2(PredicateModeEnum.WHERE_PREDICATE);
    }
    public SqlPredicate<T2> getSqlOnPredicate2() {
        return getSqlPredicate2(PredicateModeEnum.ON_PREDICATE);
    }
}
