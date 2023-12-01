package com.easy.query.core.exception;

import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/12/1 13:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultAssertExceptionFactory implements AssertExceptionFactory {
    @Override
    public <T> RuntimeException createFirstNotNullException(Query<T> query, String msg, String code) {
        return new EasyQueryFirstOrNotNullException(msg,code);
    }

    @Override
    public <T> RuntimeException createSingleNotNullException(Query<T> query, String msg, String code) {
        return new EasyQuerySingleOrNotNullException(msg,code);
    }

    @Override
    public <T> RuntimeException createSingleMoreElementException(Query<T> query) {
        return new EasyQuerySingleMoreElementException("single query has more element in result set.");
    }
//    @Override
//    public @NotNull RuntimeException createFirstNotNullException(String msg, String code) {
//        return new EasyQueryFirstOrNotNullException(msg,code);
//    }
//
//    @Override
//    public @NotNull RuntimeException createSingleNotNullException(String msg, String code) {
//        return new EasyQuerySingleOrNotNullException(msg,code);
//    }

}
