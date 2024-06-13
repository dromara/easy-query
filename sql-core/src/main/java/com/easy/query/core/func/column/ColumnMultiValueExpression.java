package com.easy.query.core.func.column;

import java.util.Collection;

/**
 * create time 2024/6/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnMultiValueExpression extends ColumnExpression{
    Collection<?> getValues();
}
