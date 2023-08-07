package com.easy.query.core.expression.sql.builder;

import java.util.Map;

/**
 * create time 2023/8/6 10:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityColumnConfigurerExpressionBuilder extends EntityExpressionBuilder{
    Map<String, ColumnConfigurerContext> getColumnConfigurer();
}
