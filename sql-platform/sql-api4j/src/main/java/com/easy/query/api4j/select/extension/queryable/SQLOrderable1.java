package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable1<T1> {

    default Queryable<T1> orderByAsc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default Queryable<T1> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default Queryable<T1> orderByDesc(SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable<T1> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default Queryable<T1> orderBy(SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    Queryable<T1> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    default Queryable<T1> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLColumnSelector}
     */
    Queryable<T1> orderByObject(boolean condition, ObjectSort configuration);

}
