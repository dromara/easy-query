package org.easy.query.core.basic.api.delete;

/**
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 * @Created by xuejiaming
 */
public interface EasyDelete<T> {
    /**
     * 返回受影响行数
     * @return
     */
    long executeRows();
    void executeRows(Long expectRow,String error);
    String toSql();
}
