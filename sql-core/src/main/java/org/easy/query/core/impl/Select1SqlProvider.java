package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider;
import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select1SqlProvider<T1> implements EasyQuerySqlBuilderProvider<T1> {

    public Select1SqlProvider(SelectContext selectContext,i){

    }
    @Override
    public SqlColumnSelector<T1> getSqlColumnSelector1(SqlSegment sqlSegment) {
        return null;
    }

    @Override
    public SqlPredicate<T1> getSqlPredicate1(SqlSegment sqlSegment) {
        return null;
    }

    @Override
    public <TR> SqlColumnAsSelector<T1, TR> getSqlColumnAsSelector1() {
        return null;
    }
}
