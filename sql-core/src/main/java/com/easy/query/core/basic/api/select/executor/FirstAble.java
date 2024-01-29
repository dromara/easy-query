package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;

import java.util.function.Supplier;

/**
 * create time 2023/10/7 15:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FirstAble<T> extends QueryAvailable<T> {
    /**
     * 当未查询到结果返回null
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     * @return
     */
    T firstOrNull();

    /**
     * 无参数方法默认抛出对象添加的{@link com.easy.query.core.annotation.EasyAssertMessage}
     *
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage("未找到主题信息")
     * public class Topic{}
     *  }
     * </pre></blockquote>
     * @return
     */
    default T firstNotNull(){
        return firstNotNull(null,null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param msg
     * @return
     * @throws EasyQueryFirstNotNullException 可以通过 {@link AssertExceptionFactory#createFirstNotNullException(Query, String, String)} 自定义
     */
    default T firstNotNull(String msg) {
        return firstNotNull(msg, null);
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param msg
     * @param code
     * @return
     * @throws EasyQueryFirstNotNullException 可以通过 {@link AssertExceptionFactory#createFirstNotNullException(Query, String, String)} 自定义
     */
    T firstNotNull(String msg, String code);
    T firstNotNull(Supplier<RuntimeException> throwFunc);
}
