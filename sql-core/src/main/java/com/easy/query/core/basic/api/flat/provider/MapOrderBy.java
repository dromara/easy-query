package com.easy.query.core.basic.api.flat.provider;

import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2024/3/26 17:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapOrderBy extends MapTable{

    ColumnOrderSelector<?> getOrderBy(int tableIndex);
}
