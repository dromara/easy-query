package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.ExecutionContext;

/**
 * create time 2023/4/25 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExecutionCreator {
    ExecutionContext create();
}
