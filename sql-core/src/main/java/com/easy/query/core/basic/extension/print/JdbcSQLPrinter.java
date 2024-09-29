package com.easy.query.core.basic.extension.print;

/**
 * create time 2024/8/25 12:52
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcSQLPrinter {
    /**
     * 打印sql
     * @return
     */
    Boolean printSQL();

    /**
     * 打印关联子查询sql
     * @return
     */
    Boolean printNavSQL();
}
