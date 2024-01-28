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
public interface SingleResultAble<T> extends QueryAvailable<T> {

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
     * 无参数方法默认抛出对象添加的{@link com.easy.query.core.annotation.EasyAssertMessage}
     *
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage(
     *         notNull = "未找到主题信息",
     *         singleMoreThan = "找到多条主题信息"
     * )
     * public class Topic{}
     *
     *
     * @EasyAssertMessage(
     *         //notNull = "未找到主题信息",
     *         firstNotNull = "未找到主题信息",
     *         singleNotNull = "未找到主题信息",
     *         singleMoreThan = "找到多条主题信息"
     * )
     * public class Topic{}
     *                    }
     * </pre></blockquote>
     *
     *
     * @param resultClass
     * @return
     * @param <TR>
     */
    default <TR> TR singleNotNull(Class<TR> resultClass) {
        return singleNotNull(resultClass, null, null);
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
     * @throws EasyQuerySingleNotNullException 如果查询不到数据
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
     * @throws EasyQuerySingleNotNullException 如果查询不到数据
     */
    <TR> TR singleNotNull(Class<TR> resultClass, String msg, String code);
    <TR> TR singleNotNull(Class<TR> resultClass,Supplier<RuntimeException> throwFunc);
}
