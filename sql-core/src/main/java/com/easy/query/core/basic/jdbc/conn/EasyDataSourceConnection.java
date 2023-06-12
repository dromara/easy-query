package com.easy.query.core.basic.jdbc.conn;

import java.util.Collection;

/**
 * create time 2023/5/11 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyDataSourceConnection {
    Collection<EasyConnection> getConnections();
    EasyConnection getEasyConnectionOrNull(String dataSource);
    void putIfAbsent(String dataSource,EasyConnection easyConnection);
    void setAutoCommit(boolean autoCommit);
    void commit();
    void rollback();
    void close();
}
