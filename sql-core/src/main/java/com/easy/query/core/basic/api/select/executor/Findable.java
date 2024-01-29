package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;

import java.util.function.Supplier;

/**
 * create time 2024/1/29 08:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Findable<T> extends QueryAvailable<T> {
    /**
     * 当未查询到结果返回null
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     * @return 可为空的结果
     * @throws com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException 如果存在多个主键
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 如果没有主键
     * @throws com.easy.query.core.exception.EasyQueryFindNotNullException 可以通过 {@link AssertExceptionFactory#createFindNotNullException(Query, String, String)} 自定义
     */
    @Nullable T findOrNull(Object id);

    /**
     * 无参数方法默认抛出对象添加的{@link com.easy.query.core.annotation.EasyAssertMessage}
     *
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage(
     *         notNull = "未找到主题信息"
     * )
     * public class Topic{}
     *
     *
     * @EasyAssertMessage(
     *         //notNull = "未找到主题信息",
     *         findNotNull = "未找到主题信息",
     *         firstNotNull = "未找到主题信息",
     *         singleNotNull = "未找到主题信息",
     *         singleMoreThan = "找到多条主题信息"
     * )
     * public class Topic{}
     *                    }
     * </pre></blockquote>
     * @return 返回一个不能为空的结果
     * @throws com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException 如果存在多个主键
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 如果没有主键
     * @throws com.easy.query.core.exception.EasyQueryFindNotNullException 可以通过 {@link AssertExceptionFactory#createFindNotNullException(Query, String, String)} 自定义
     */
    default @NotNull T findNotNull(Object id){
        return findNotNull(id,null,null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage(
     *         notNull = "未找到主题信息"
     * )
     * public class Topic{}
     *
     *
     * @EasyAssertMessage(
     *         //notNull = "未找到主题信息",
     *         findNotNull = "未找到主题信息",
     *         firstNotNull = "未找到主题信息",
     *         singleNotNull = "未找到主题信息",
     *         singleMoreThan = "找到多条主题信息"
     * )
     * public class Topic{}
     *                    }
     * </pre></blockquote>
     *
     * @param msg
     * @return 返回一个不能为空的结果
     * @throws com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException 如果存在多个主键
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 如果没有主键
     * @throws com.easy.query.core.exception.EasyQueryFindNotNullException 可以通过 {@link AssertExceptionFactory#createFindNotNullException(Query, String, String)} 自定义
     */
    default @NotNull T findNotNull(Object id,String msg) {
        return findNotNull(id,msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage(
     *         notNull = "未找到主题信息"
     * )
     * public class Topic{}
     *
     *
     * @EasyAssertMessage(
     *         //notNull = "未找到主题信息",
     *         findNotNull = "未找到主题信息",
     *         firstNotNull = "未找到主题信息",
     *         singleNotNull = "未找到主题信息",
     *         singleMoreThan = "找到多条主题信息"
     * )
     * public class Topic{}
     *                    }
     * </pre></blockquote>
     *
     * @param msg
     * @param code
     * @return 返回一个不能为空的结果
     * @throws com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException 如果存在多个主键
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 如果没有主键
     * @throws com.easy.query.core.exception.EasyQueryFindNotNullException 可以通过 {@link AssertExceptionFactory#createFindNotNullException(Query, String, String)} 自定义
     */
    @NotNull T findNotNull(Object id,String msg, String code);

    /**
     *
     * @param id 主键
     * @param throwFunc 自定义结果
     * @return 返回一个不能为空的结果
     * @throws com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException 如果存在多个主键
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException 如果没有主键
     */
    @NotNull T findNotNull(Object id,Supplier<RuntimeException> throwFunc);
}
