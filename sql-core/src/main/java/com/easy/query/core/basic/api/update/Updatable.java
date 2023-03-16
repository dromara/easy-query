package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: Update.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:04
 * @Created by xuejiaming
 */
public interface Updatable<T> {
//    Insert<T> insertColumns(SqlExpression<SqlPredicate<T>> columnExpression);
//    Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression);

    SqlEntityUpdateExpression getSqlEntityUpdateExpression();
    /**
     * 返回受影响行数
     *
     * @return
     */
    long executeRows();

    /**
     * 当执行受影响行数不符合 expectRows 将会抛出 {@link EasyQueryConcurrentException}
     * @param expectRows
     * @param msg
     */
    default void executeRows(long expectRows, String msg) {
        executeRows(expectRows, msg, null);
    }

    /**
     * 异常 EasyQueryConcurrentException
     * @param expectRows
     * @param msg
     * @param code
     */
    default void executeRows(long expectRows, String msg, String code){

        EasyConnectionManager connectionManager = getSqlEntityUpdateExpression().getRuntimeContext().getConnectionManager();
        Transaction transaction=null;
        try {
            boolean inTransaction = connectionManager.currentThreadInTransaction();
            if(!inTransaction){
                transaction = connectionManager.beginTransaction();
            }
            long rows = executeRows();
            if(rows!=expectRows){
                throw new EasyQueryConcurrentException(msg,code);
            }
            if(!inTransaction){
                transaction.commit();
            }
        }finally {
            if(transaction!=null){
                transaction.close();
            }
        }
    }

    String toSql();
}
