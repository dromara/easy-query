package org.easy.query.core.impl;

import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.List;

/**
 * @FileName: SqlContext.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:54
 * @Created by xuejiaming
 */
public interface SqlContext {
     List<SqlTableInfo> getTables();
     SqlTableInfo getTable(int index);
     String getQuoteName(String value);
}
