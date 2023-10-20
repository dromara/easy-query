package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleOrNotNullException;

/**
 * create time 2023/10/7 15:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SingleAble<T> extends QueryAvailable<T> {
    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default T singleOrNull() {
        return singleOrNull(queryClass());
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 并且select并不是表的全部而是映射到 {@code resultClass} 上
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass
     * @param <TR>
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */

    <TR> TR singleOrNull(Class<TR> resultClass);

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param msg
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleOrNotNullException 如果查询不到数据
     */
    default T singleNotNull(String msg) {
        return singleNotNull(msg, null);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param msg
     * @param code
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleOrNotNullException 如果查询不到数据
     */
    default T singleNotNull(String msg, String code) {
        return singleNotNull(queryClass(), msg, code);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass
     * @param msg
     * @param <TR>
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleOrNotNullException 如果查询不到数据
     */

    default <TR> TR singleNotNull(Class<TR> resultClass, String msg) {
        return singleNotNull(resultClass, msg, null);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass 返回结果
     * @param msg
     * @param code
     * @param <TR>
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleOrNotNullException 如果查询不到数据
     */
    <TR> TR singleNotNull(Class<TR> resultClass, String msg, String code);
}
