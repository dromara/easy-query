package com.easy.query.core.expression.parser.core.base.many;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2025/3/9 22:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ManyColumn {
    TableAvailable getTable();
    String getNavValue();
}
