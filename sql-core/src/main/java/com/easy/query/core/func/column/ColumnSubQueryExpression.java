package com.easy.query.core.func.column;

import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/12/21 23:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnSubQueryExpression extends ColumnExpression{
    Query<?> getQuery();
}
