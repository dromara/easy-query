package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable1<T1> {

    default KtQueryable<T1> orderByAsc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default KtQueryable<T1> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, true);
    }

    default KtQueryable<T1> orderByDesc(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable<T1> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        return orderBy(condition, selectExpression, false);
    }

    default KtQueryable<T1> orderBy(SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        return orderBy(true, selectExpression, asc);
    }

    KtQueryable<T1> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc);

    /**
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    default KtQueryable<T1> orderByObject(ObjectSort configuration) {
        return orderByObject(true, configuration);
    }

    /**
     * @param condition
     * @param configuration
     * @return
     * @throws EasyQueryOrderByInvalidOperationException 当配置{@link ObjectSort} 为{@code  DynamicModeEnum.STRICT}排序设置的属性不存在当前排序对象里面或者当前查询对象无法获取 {@link SQLKtColumnSelector}
     */
    KtQueryable<T1> orderByObject(boolean condition, ObjectSort configuration);

}
