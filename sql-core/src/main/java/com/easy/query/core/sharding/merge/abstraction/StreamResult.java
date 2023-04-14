package com.easy.query.core.sharding.merge.abstraction;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * create time 2023/4/13 11:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamResult extends AutoCloseable{
    boolean next() throws SQLException;
    Object getObject(int columnIndex) throws SQLException;
    boolean wasNull() throws SQLException;

    ResultSetMetaData getMetaData() throws SQLException;
}
