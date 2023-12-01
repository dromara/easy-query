package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;

import java.util.function.Supplier;

/**
 * create time 2023/10/7 15:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SingleAble<T> extends QueryAvailable<T> {
    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     *
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    T singleOrNull();


    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param msg
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleNotNullException     如果查询不到数据
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
     * @throws EasyQuerySingleNotNullException     如果查询不到数据
     */
    T singleNotNull(String msg, String code);

    T singleNotNull(Supplier<RuntimeException> throwFunc);
}
