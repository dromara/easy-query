package com.easy.query.core.basic.api.flat.provider;

import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2024/3/26 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapGroupSelector extends MapTable{

    ColumnGroupSelector<?> getGroupSelector(int tableIndex);
}
