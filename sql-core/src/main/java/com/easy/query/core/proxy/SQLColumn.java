package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/22 13:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumn<TProperty> {
    TableAvailable getTable();
    String value();
}
