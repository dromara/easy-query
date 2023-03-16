package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.query.SqlEntityDeleteExpression;

/**
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 * @Created by xuejiaming
 */
public interface Deletable<T,TChain> {
    SqlEntityDeleteExpression getSqlEntityDeleteExpression();
    /**
     * 返回受影响行数
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
     * 当执行受影响行数不符合 expectRows 将会抛出 {@link EasyQueryConcurrentException}
     * @param expectRows
     * @param msg
     * @param code
     */

   default void executeRows(long expectRows, String msg, String code){

       EasyConnectionManager connectionManager = getSqlEntityDeleteExpression().getRuntimeContext().getConnectionManager();
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
    TChain disableLogicDelete();
}
