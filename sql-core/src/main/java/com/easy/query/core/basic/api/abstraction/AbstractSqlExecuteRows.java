package com.easy.query.core.basic.api.abstraction;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.query.SqlEntityExpression;

/**
 * @FileName: AbstractSqlExecuteRows.java
 * @Description: 文件说明
 * @Date: 2023/3/17 17:14
 * @author xuejiaming
 */
public abstract class AbstractSqlExecuteRows implements SqlExecuteExpectRows {
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
            doExecuteRows(expectRows,msg,code);
            if(!inTransaction){
                transaction.commit();
            }
        }finally {
            if(transaction!=null){
                transaction.close();
            }
        }
    }
    private void doExecuteRows(long expectRows, String msg, String code){
        long rows = executeRows();
        if(rows!=expectRows){
            throw new EasyQueryConcurrentException(msg,code);
        }
    }
}
