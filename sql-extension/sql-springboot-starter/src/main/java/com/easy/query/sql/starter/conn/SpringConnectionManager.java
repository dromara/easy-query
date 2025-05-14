package com.easy.query.sql.starter.conn;

import com.easy.query.core.basic.jdbc.conn.DataSourceWrapper;
import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.jdbc.conn.EasyConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.EasyDataSourceConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultConnectionManager;
import com.easy.query.core.enums.conn.ConnectionStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.sharding.EasyQueryDataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @FileName: SpringConnectionManager.java
 * @Description: 文件说明
 * create time 2023/3/10 22:01
 * @author xuejiaming
 */
public class SpringConnectionManager extends DefaultConnectionManager {

    public SpringConnectionManager(EasyQueryDataSource easyDataSource, EasyConnectionFactory easyConnectionFactory, EasyDataSourceConnectionFactory easyDataSourceConnectionFactory) {
        super(easyDataSource, easyConnectionFactory, easyDataSourceConnectionFactory);
    }

    @Override
    public boolean currentThreadInTransaction() {
        return TransactionSynchronizationManager.isActualTransactionActive() || isOpenTransaction();
    }


//    @Override
//    public EasyConnection getEasyConnection(String dataSourceName) {
//        //如果spring已经开启事务,但是当前easy-query没有开启事务,那么就先开启easy-query的事务,然后注册完成后清理当前上下文
//        if(currentThreadInTransaction()&&!easyCurrentThreadInTransaction()){
//            beginTransaction();
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//                @Override
//                public void afterCompletion(int status) {
//                    clear();
//                }
//            });
//        }
//        return super.getEasyConnection(dataSourceName);
//    }

    @Override
    public void closeEasyConnection(EasyConnection easyConnection) {
        if(easyConnection==null){
            return;
        }
        //当前没开事务,但是easy query手动开启了
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            if (super.isOpenTransaction()) {
                return;
            }
        } else {
            if (super.isOpenTransaction()) {
                throw new EasyQueryException("repeat transaction can't closed connection");
            }
        }
        DataSourceWrapper dataSourceUnit = easyDataSource.getDataSourceNotNull(easyConnection.getDataSourceName(), ConnectionStrategyEnum.ShareConnection);
        DataSourceUtils.releaseConnection(easyConnection.getConnection(), dataSourceUnit.getDataSourceUnit().getDataSource());
    }
}
