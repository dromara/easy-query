package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.lambda.DefaultSqlColumnAsSelector;
import org.easy.query.core.impl.lambda.DefaultSqlColumnSelector;
import org.easy.query.core.impl.lambda.DefaultSqlPredicate;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:30
 * @Created by xuejiaming
 */
public class DefaultEasyQueryLambdaFactory implements EasyQueryLambdaFactory{
    @Override
    public <T1> SqlPredicate<T1> createSqlPredicate(int index, SelectContext selectContext, SqlSegment sqlSegment) {
        return new DefaultSqlPredicate<>(index,selectContext,sqlSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, SelectContext selectContext, SqlSegment sqlSegment) {
        return new DefaultSqlColumnSelector<>(index,selectContext,sqlSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SelectContext selectContext, SqlSegment sqlSegment, boolean asc) {
        return new DefaultSqlColumnSelector<>(index,selectContext,sqlSegment);
    }

    @Override
    public <T1, TR> SqlColumnAsSelector<T1, TR> createSqlColumnAsSelector(int index, SelectContext selectContext, SqlSegment sqlSegment) {
        return new DefaultSqlColumnAsSelector<T1,TR>(index,selectContext,sqlSegment);
    }
}
