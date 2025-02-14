package com.easy.query.test;

import com.easy.query.core.basic.extension.print.JdbcSQLPrinter;

/**
 * create time 2025/2/13 10:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyJdbcSQLPrinter implements JdbcSQLPrinter {
    @Override
    public Boolean printSQL() {
        return null;
    }

    @Override
    public Boolean printNavSQL() {
        return !"isDebug".equals("env");
    }
}
