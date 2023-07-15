package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/2 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLTableOwner {
    TableAvailable getTable();
}
