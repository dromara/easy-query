package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/8/11 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ToTableContext {
    int getTableSize();
    String getAlias(TableAvailable table);
}
