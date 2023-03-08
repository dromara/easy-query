package org.easy.query.core.basic.api.delete;

/**
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 * @Created by xuejiaming
 */
public interface Deletable<T,TChain> {
    /**
     * 返回受影响行数
     * @return
     */
    long executeRows();
    default void executeRows(long expectRows, String msg) {
        executeRows(expectRows, msg, null);
    }

    void executeRows(long expectRows, String msg, String code);
    String toSql();
    TChain disableLogicDelete();
}
