package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;

/**
 * create time 2025/6/12 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnReader {
    Object read(int index,StreamResultSet streamResultSet) throws SQLException;
}
