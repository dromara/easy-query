package com.easy.query.core.basic.api.internal;

import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.sql.EntityExpression;

/**
 * @FileName: AbstractSqlExecuteRows.java
 * @Description: 文件说明
 * @Date: 2023/3/17 17:14
 * @author xuejiaming
 */
public abstract class AbstractSqlExecuteRows<TChain>  implements SqlExecuteExpectRows,Interceptable<TChain>,LogicDeletable<TChain> {
    private final EntityExpression sqlEntityExpression;

    public AbstractSqlExecuteRows(EntityExpression sqlEntityExpression){

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

    @Override
    public TChain noInterceptor() {
        sqlEntityExpression.getExpressionContext().noInterceptor();
        return (TChain) this;
    }
    @Override
    public TChain useInterceptor(String name) {
        sqlEntityExpression.getExpressionContext().useInterceptor(name);
        return (TChain) this;
    }
    @Override
    public TChain noInterceptor(String name) {
        sqlEntityExpression.getExpressionContext().noInterceptor(name);
        return (TChain) this;
    }

    @Override
    public TChain useInterceptor() {
        sqlEntityExpression.getExpressionContext().useInterceptor();
        return (TChain) this;
    }

    @Override
    public TChain useLogicDelete(boolean enable) {
        if(enable){
            sqlEntityExpression.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }else{
            sqlEntityExpression.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
        return (TChain) this;
    }
}
