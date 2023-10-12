package com.easy.query.core.func.concat;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/10/12 14:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ConcatColumnExpression extends ConcatExpression{
    TableAvailable getTableOrNull();
    String getProperty();
}
