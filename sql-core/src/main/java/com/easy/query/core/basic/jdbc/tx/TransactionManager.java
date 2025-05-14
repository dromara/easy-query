package com.easy.query.core.basic.jdbc.tx;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

/**
 * @FileName: TransactionManager.java
 * @Description: 文件说明
 * create time 2023/2/20 22:32
 * @author xuejiaming
 */
public interface TransactionManager {
    void begin();
    void commit();
    void rollback();

}
