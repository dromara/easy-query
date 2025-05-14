package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @Description: 文件说明
 * create time 2023/2/28 20:47
 * @author xuejiaming
 */
public interface SQLParameter {
    TableAvailable getTableOrNull();
    String getPropertyNameOrNull();
    Object getValue();
}
