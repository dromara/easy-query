package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.sql.core.impl.lambda.DefaultSqlGroupSelector;
import org.jdqc.sql.core.impl.lambda.DefaultSqlSelector;
import org.jdqc.sql.core.impl.lambda.DefaultSqlPredicate;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select1SqlProvider<T1,TR> {
    private final DefaultSqlPredicate<T1> sqlPredicate1;
    private final DefaultSqlSelector<T1,TR> sqlSelector1;
    private final DefaultSqlGroupSelector<T1> sqlGroupSelector1;

    public Select1SqlProvider(SelectContext selectContext) {
        this.sqlPredicate1 = new DefaultSqlPredicate<>(0,selectContext, PredicateModeEnum.WHERE_PREDICATE);
        this.sqlSelector1 = new DefaultSqlSelector<>(0,selectContext);
        this.sqlGroupSelector1 = new DefaultSqlGroupSelector<>(0,selectContext);
    }
    public SqlColumnSelector<T1> getSqlGroupSelector1(){
        return sqlGroupSelector1;
    }
    public SqlSelector<T1,TR> getSqlSelector1(){
        return sqlSelector1;
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
