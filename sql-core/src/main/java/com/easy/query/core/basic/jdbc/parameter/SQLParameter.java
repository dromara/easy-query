package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;

/**
 * @FileName: SqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 * @author xuejiaming
 */
public interface SQLParameter {
    EntityTableAvailable getTable();
    String getPropertyName();
    Object getValue();
}
