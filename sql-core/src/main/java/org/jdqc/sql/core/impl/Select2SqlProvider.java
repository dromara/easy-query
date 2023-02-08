package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.sql.core.impl.lambda.DefaultSqlGroupSelector;
import org.jdqc.sql.core.impl.lambda.DefaultSqlPredicate;
import org.jdqc.sql.core.impl.lambda.DefaultSqlSelector;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select2SqlProvider<T1,T2,TR> extends Select1SqlProvider<T1,TR> {
    private final DefaultSqlPredicate<T2> sqlPredicate2;
    private final DefaultSqlSelector<T2,TR> sqlSelector2;
    private final DefaultSqlGroupSelector<T2> sqlGroupSelector2;

    public Select2SqlProvider(SelectContext selectContext) {
        super(selectContext);
        this.sqlPredicate2 = new DefaultSqlPredicate<>(1,selectContext, PredicateModeEnum.WHERE_PREDICATE);
        this.sqlSelector2=new DefaultSqlSelector<>(1,selectContext);
        this.sqlGroupSelector2 = new DefaultSqlGroupSelector<>(1,selectContext);
    }
    public SqlColumnSelector<T2> getSqlGroupSelector2(){
        return sqlGroupSelector2;
    }
    public SqlSelector<T2,TR> getSqlSelector2(){
        return sqlSelector2;
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
