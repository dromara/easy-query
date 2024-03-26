package com.easy.query.core.basic.api.flat.provider;

import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;

/**
 * create time 2024/3/26 16:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapSelector extends MapTable{

    ColumnSelector<?> getSelector(int tableIndex);
    ColumnAsSelector<?,?> getAsSelector(int tableIndex);
}
