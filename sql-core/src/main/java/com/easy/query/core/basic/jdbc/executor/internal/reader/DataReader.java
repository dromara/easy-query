package com.easy.query.core.basic.jdbc.executor.internal.reader;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;

/**
 * create time 2023/8/27 23:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataReader {
    <TBean> void readTo(TBean entity,StreamResultSet streamResultSet) throws SQLException;
}
