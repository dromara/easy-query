package com.easy.query.core.expression.parser.core.extra;

import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2025/4/16 10:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExtraSelect {

    void select(ColumnAsSelector<?,?> columnAsSelector);

}
