package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.EasyQueryFirstOrNotNullException;

/**
 * create time 2023/10/21 09:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FirstResultAble<T> extends QueryAvailable<T> {

    /**
     * 返回第一条,如果第一条没有就返回null,并且select并不是表的全部而是映射到 {@code resultClass} 上
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass
     * @param <TR>
     * @return
     */

    <TR> TR firstOrNull(Class<TR> resultClass);

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass
     * @param msg
     * @param <TR>
     * @return
     */

    default <TR> TR firstNotNull(Class<TR> resultClass, String msg) {
        return firstNotNull(resultClass, msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstOrNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass 返回结果
     * @param msg
     * @param code
     * @param <TR>
     * @return
     */
    <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code);
}
