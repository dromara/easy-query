package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.delete.EasyDelete;

/**
 * @FileName: MySQLEmptyDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 12:57
 * @Created by xuejiaming
 */
public class MySQLEmptyDelete<T1> implements EasyDelete<T1> {
    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public void executeRows(Long expectRow, String error) {

    }

    @Override
    public String toSql() {
        return null;
    }
}
