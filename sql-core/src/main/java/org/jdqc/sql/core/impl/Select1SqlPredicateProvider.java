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
public class Select1SqlPredicateProvider<T1> {
    private final DefaultSqlPredicate<T1> sqlPredicate1;

    public Select1SqlPredicateProvider(SelectContext selectContext) {
        this.sqlPredicate1 = new DefaultSqlPredicate<>(0,selectContext, PredicateModeEnum.WHERE_PREDICATE);
    }

    public SqlPredicate<T1> getSqlPredicate1(PredicateModeEnum predicateMode) {
        sqlPredicate1.setPredicateMode(predicateMode);
        return sqlPredicate1;
    }

    public SqlPredicate<T1> getSqlWherePredicate1() {
        return getSqlPredicate1(PredicateModeEnum.WHERE_PREDICATE);
    }

    public SqlPredicate<T1> getSqlOnPredicate1() {
        return getSqlPredicate1(PredicateModeEnum.ON_PREDICATE);
    }
}
