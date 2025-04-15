package com.easy.query.test.vo.aaaa;

import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;

/**
 * create time 2025/4/15 14:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class RealEntityExpressionExecutor extends DefaultEntityExpressionExecutor {

    private final DbInterceptorHolder dbInterceptorHolder;

    public RealEntityExpressionExecutor(DbInterceptorHolder dbInterceptorHolder, EasyPrepareParser easyPrepareParser, ExecutionContextFactory executionContextFactory) {
        super(easyPrepareParser, executionContextFactory);
        this.dbInterceptorHolder = dbInterceptorHolder;
    }
}
