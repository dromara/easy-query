package com.easy.query.core.basic.api.internal;

import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

/**
 * @Description: 文件说明
 * @Date: 2023/3/17 17:14
 * @author xuejiaming
 */
public abstract class AbstractSQLExecuteRows<TChain>  implements SQLExecuteExpectRows,Interceptable<TChain>,LogicDeletable<TChain> {
    private final EntityExpressionBuilder entityExpressionBuilder;

    public AbstractSQLExecuteRows(EntityExpressionBuilder entityExpressionBuilder){

        this.entityExpressionBuilder = entityExpressionBuilder;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {

        ConnectionManager connectionManager = entityExpressionBuilder.getRuntimeContext().getConnectionManager();
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

    @Override
    public TChain noInterceptor() {
        entityExpressionBuilder.getExpressionContext().noInterceptor();
        return (TChain) this;
    }
    @Override
    public TChain useInterceptor(String name) {
        entityExpressionBuilder.getExpressionContext().useInterceptor(name);
        return (TChain) this;
    }
    @Override
    public TChain noInterceptor(String name) {
        entityExpressionBuilder.getExpressionContext().noInterceptor(name);
        return (TChain) this;
    }

    @Override
    public TChain useInterceptor() {
        entityExpressionBuilder.getExpressionContext().useInterceptor();
        return (TChain) this;
    }

    @Override
    public TChain useLogicDelete(boolean enable) {
        if(enable){
            entityExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }else{
            entityExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
        return (TChain) this;
    }
}
