package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;

import java.util.function.Supplier;

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
     * @param resultClass
     * @return
     * @param <TR>
     */
    default <TR> TR firstNotNull(Class<TR> resultClass) {
        return firstNotNull(resultClass, null, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass
     * @param msg
     * @param <TR>
     * @return
     * @throws EasyQueryFirstNotNullException 可以通过 {@link AssertExceptionFactory#createFirstNotNullException(Query, String, String)} 自定义
     */

    default <TR> TR firstNotNull(Class<TR> resultClass, String msg) {
        return firstNotNull(resultClass, msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param resultClass 返回结果
     * @param msg
     * @param code
     * @param <TR>
     * @return
     * @throws EasyQueryFirstNotNullException 可以通过 {@link AssertExceptionFactory#createFirstNotNullException(Query,String, String)} 自定义
     */
    <TR> TR firstNotNull(Class<TR> resultClass, String msg, String code);
    <TR> TR firstNotNull(Class<TR> resultClass, Supplier<RuntimeException> throwFunc);
}
