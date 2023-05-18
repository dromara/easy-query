package com.easy.query.core.basic.jdbc.con;

import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;

/**
 * @FileName: ConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/21 08:56
 * @author xuejiaming
 */
public interface ConnectionManager {
    default Transaction beginTransaction(){
       return beginTransaction(null);
    }

    /**
     * todo 枚举
     * TRANSACTION_READ_UNCOMMITTED ......
     * @param isolationLevel
     * @return
     */
    Transaction beginTransaction(Integer isolationLevel);
    EasyConnection getEasyConnection();
    EasyConnection getEasyConnection(String dataSourceName, ConnectionStrategyEnum connectionStrategy);
    boolean currentThreadInTransaction();
    void clear();
    void closeEasyConnection(EasyConnection easyConnection);

    /**
     * 提交
     */
    void commit();

    /**
     * 回滚,异常后会自动执行
     */
    void rollback();
}
