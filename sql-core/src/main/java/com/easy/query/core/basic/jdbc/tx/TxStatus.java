package com.easy.query.core.basic.jdbc.tx;

/**
 * @FileName: TxStatus.java
 * @Description: 文件说明
 * @Date: 2023/2/21 20:59
 * @author xuejiaming
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
