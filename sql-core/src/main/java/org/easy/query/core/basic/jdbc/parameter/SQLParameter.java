package org.easy.query.core.basic.jdbc.parameter;

import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: SqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 * @Created by xuejiaming
 */
public interface SQLParameter {
    SqlTableInfo getTable();
    String getPropertyName();
    Object getValue();
}
