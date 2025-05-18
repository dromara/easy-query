package com.easy.query.core.basic.extension.print;

import com.easy.query.core.logging.Log;

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

//   default void print(Log log,String content){
//       log.info(content);
//   }

    /**
     * 打印关联子查询sql
     * @return
     */
    Boolean printNavSQL();
//    default void printNav(Log log,String content){
//        log.info(content);
//    }
}
