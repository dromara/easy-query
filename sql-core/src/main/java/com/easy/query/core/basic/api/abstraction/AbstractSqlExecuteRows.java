package com.easy.query.core.basic.api.abstraction;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.query.SqlEntityExpression;

/**
 * @FileName: AbstractSqlExecuteRows.java
 * @Description: 文件说明
 * @Date: 2023/3/17 17:14
 * @Created by xuejiaming
 */
public abstract class AbstractSqlExecuteRows implements SqlExecuteRows{
    private final SqlEntityExpression sqlEntityExpression;

    public AbstractSqlExecuteRows(SqlEntityExpression sqlEntityExpression){

        this.sqlEntityExpression = sqlEntityExpression;
    }
    @Override
    public void executeRows(long expectRows, String msg, String code) {

        EasyConnectionManager connectionManager = sqlEntityExpression.getRuntimeContext().getConnectionManager();
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
}
