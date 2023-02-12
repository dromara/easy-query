package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.impl.SelectContext;

/**
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:21
 * @Created by xuejiaming
 */
public interface EasyQueryLambdaFactory {
    default <T1> SqlPredicate<T1> createSqlPredicate(SelectContext selectContext, SqlSegment sqlSegment){
        return createSqlPredicate(0,selectContext,sqlSegment);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnSelector(SelectContext selectContext, SqlSegment sqlSegment){
        return createSqlColumnSelector(0,selectContext,sqlSegment);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(SelectContext selectContext, SqlSegment sqlSegment,boolean asc){
        return createSqlColumnOrderSelector(0,selectContext,sqlSegment,asc);
    }
    default <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(SelectContext selectContext, SqlSegment sqlSegment){
        return createSqlColumnAsSelector(0,selectContext,sqlSegment);
    }
    <T1> SqlPredicate<T1> createSqlPredicate(int index, SelectContext selectContext, SqlSegment sqlSegment);
    <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index,SelectContext selectContext, SqlSegment sqlSegment);
    <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index,SelectContext selectContext, SqlSegment sqlSegment,boolean asc);
    <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(int index,SelectContext selectContext, SqlSegment sqlSegment);
}
