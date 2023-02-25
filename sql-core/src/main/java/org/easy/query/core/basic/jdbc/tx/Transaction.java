package org.easy.query.core.basic.jdbc.tx;

import java.io.Closeable;
import java.sql.SQLException;

/**
 * @FileName: Transaction.java
 * @Description: 文件说明
 * @Date: 2023/2/20 22:11
 * @Created by xuejiaming
 */
public interface Transaction extends AutoCloseable {
    Integer getIsolationLevel();
    void commit();
    void rollback();
    void close();
}
