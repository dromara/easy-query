package com.easy.query.core.basic.api.select.executor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;

import java.util.function.Supplier;

/**
 * create time 2023/10/7 15:04
 * 返回至多一条结果的接口
 *
 * @author xuejiaming
 */
public interface SingleResultAble<T> extends QueryAvailable<T> {

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 并且select并不是表的全部而是映射到 {@code resultClass} 上
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass 返回结果类型
     * @param <TR>        返回类型泛型
     * @return 返回类型TR的实例对象
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    @Nullable
    <TR> TR singleOrNull(@NotNull Class<TR> resultClass);

    /**
     * 无参数方法默认抛出对象添加的{@link com.easy.query.core.annotation.EasyAssertMessage}
     *
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage("未找到主题信息")
     * public class Topic{}
     *
     * </pre></blockquote>
     *
     * @param resultClass 返回结果类型
     * @param <TR>返回类型泛型
     * @return 返回类型TR的实例对象
     */
    @NotNull
    default <TR> TR singleNotNull(@NotNull Class<TR> resultClass) {
        return singleNotNull(resultClass, null, null);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass 返回结果类型
     * @param msg         错误消息
     * @param <TR>        返回类型泛型
     * @return 返回类型TR的实例对象
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleNotNullException     如果查询不到数据
     */

    @NotNull
    default <TR> TR singleNotNull(@NotNull Class<TR> resultClass, @Nullable String msg) {
        return singleNotNull(resultClass, msg, null);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass 返回结果类型
     * @param msg         错误消息
     * @param code        错误code
     * @param <TR>        返回类型泛型
     * @return 返回类型TR的实例对象
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleNotNullException     如果查询不到数据
     */
    @NotNull
    <TR> TR singleNotNull(@NotNull Class<TR> resultClass, @Nullable String msg, @Nullable String code);

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link RuntimeException}自定义异常
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass 返回结果类型
     * @param throwFunc   自定义抛错
     * @param <TR>        返回类型泛型
     * @return 返回类型TR的实例对象
     */
    @NotNull
    <TR> TR singleNotNull(@NotNull Class<TR> resultClass, @NotNull Supplier<RuntimeException> throwFunc);
}
