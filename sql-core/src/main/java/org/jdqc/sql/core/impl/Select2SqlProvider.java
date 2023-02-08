package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelectColumnSelector;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.sql.core.impl.lambda.DefaultSqlGroupSelector;
import org.jdqc.sql.core.impl.lambda.DefaultSqlOrderBySelector;
import org.jdqc.sql.core.impl.lambda.DefaultSqlPredicate;
import org.jdqc.sql.core.impl.lambda.DefaultSqlSelector;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select2SqlProvider<T1, T2, TR> extends Select1SqlProvider<T1, TR> {
    private final SelectContext selectContext;
    private DefaultSqlPredicate<T2> sqlPredicate2;
    private DefaultSqlSelector<T2, TR> sqlSelector2;
    private DefaultSqlGroupSelector<T2> sqlGroupSelector2;
    private DefaultSqlOrderBySelector<T2> sqlOrderBySelector2;

    public Select2SqlProvider(SelectContext selectContext) {
        super(selectContext);
        this.selectContext = selectContext;
    }

    public SqlColumnSelector<T2> getSqlOrderBySelector2() {
        if(sqlOrderBySelector2==null){
            sqlOrderBySelector2=new DefaultSqlOrderBySelector<>(1, selectContext);
        }
        return sqlOrderBySelector2;
    }

    public SqlColumnSelector<T2> getSqlGroupSelector2() {
        if(sqlGroupSelector2==null){
            sqlGroupSelector2=new DefaultSqlGroupSelector<>(1, selectContext);
        }
        return sqlGroupSelector2;
    }

    public SqlSelectColumnSelector<T2, TR> getSqlSelector2() {
        if(sqlSelector2==null){
            sqlSelector2=new DefaultSqlSelector<>(1, selectContext);
        }
        return sqlSelector2;
    }

    public SqlPredicate<T2> getSqlPredicate2(PredicateModeEnum predicateMode) {
        if(sqlPredicate2==null){
            sqlPredicate2=new DefaultSqlPredicate<>(1, selectContext, PredicateModeEnum.WHERE_PREDICATE);
        }
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
