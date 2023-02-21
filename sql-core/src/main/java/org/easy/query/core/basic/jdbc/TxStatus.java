package org.easy.query.core.basic.jdbc;

/**
 * @FileName: TxStatus.java
 * @Description: 文件说明
 * @Date: 2023/2/21 20:59
 * @Created by xuejiaming
 */
public class TxStatus {
    private final Integer isolationLevel;

    public TxStatus(Integer isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public Integer getIsolationLevel() {
        return isolationLevel;
    }
}
