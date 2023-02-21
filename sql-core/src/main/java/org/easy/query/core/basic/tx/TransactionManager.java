package org.easy.query.core.basic.tx;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

/**
 * @FileName: TransactionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/20 22:32
 * @Created by xuejiaming
 */
public interface TransactionManager {
    void begin();
    void commit();
    void rollback();

}
