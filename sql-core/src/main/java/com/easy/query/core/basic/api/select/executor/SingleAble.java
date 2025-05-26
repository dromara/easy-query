package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @Nullable
    T singleOrNull();


    /**
     * 无参数方法默认抛出对象添加的{@link com.easy.query.core.annotation.EasyAssertMessage}
     *
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage("未找到主题信息")
     * public class Topic{}
     * }
     * </pre></blockquote>
     *
     * @return
     */

    @NotNull
    default T singleNotNull() {
        return singleNotNull(null, null);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param msg
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据 可以通过
     * @throws EasyQuerySingleNotNullException     如果查询不到数据 可以通过 {@link AssertExceptionFactory#createSingleNotNullException(Query, String, String)} 自定义
     */
    @NotNull
    default T singleNotNull(@Nullable String msg) {
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
     * @throws EasyQuerySingleNotNullException     如果查询不到数据 可以通过 {@link AssertExceptionFactory#createSingleNotNullException(Query, String, String)} 自定义
     */
    @NotNull
    T singleNotNull(@Nullable String msg, @Nullable String code);

    /**
     * 自定义查询结果为null时如何返回
     * @param throwFunc
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    @NotNull
    T singleNotNull(@NotNull Supplier<RuntimeException> throwFunc);
}
