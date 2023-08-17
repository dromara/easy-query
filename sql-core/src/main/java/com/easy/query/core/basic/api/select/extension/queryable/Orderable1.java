package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable1<T1> {

    default ClientQueryable<T1> orderByAsc(SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ClientQueryable<T1> orderByAsc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default ClientQueryable<T1> orderByDesc(SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ClientQueryable<T1> orderByDesc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default ClientQueryable<T1> orderBy(SQLExpression1<ColumnOrderSelector<T1>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    ClientQueryable<T1> orderBy(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression, boolean asc);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link ColumnSelector}
     */
    default ClientQueryable<T1> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link ColumnSelector}
     */
    ClientQueryable<T1> orderByObject(boolean condition, ObjectSort configuration);

}
