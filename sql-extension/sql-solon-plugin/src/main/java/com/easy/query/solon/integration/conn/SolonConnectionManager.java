package com.easy.query.solon.integration.conn;

import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.jdbc.conn.EasyConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.EasyDataSourceConnectionFactory;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultConnectionManager;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.sharding.EasyQueryDataSource;
import org.noear.solon.data.tran.TranUtils;

/**
 * create time 2023/7/24 22:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SolonConnectionManager extends DefaultConnectionManager {

    public SolonConnectionManager(EasyQueryDataSource easyDataSource, EasyConnectionFactory easyConnectionFactory, EasyDataSourceConnectionFactory easyDataSourceConnectionFactory) {
        super(easyDataSource, easyConnectionFactory, easyDataSourceConnectionFactory);
    }

    @Override
    public boolean currentThreadInTransaction() {
        return TranUtils.inTrans() || isOpenTransaction();
    }

    @Override
    public void closeEasyConnection(EasyConnection easyConnection) {
        if(easyConnection==null){
            return;
        }
        //当前没开事务,但是easy query手动开启了
        if (!TranUtils.inTrans()) {
            if (super.isOpenTransaction()) {
                return;
            }
            super.closeEasyConnection(easyConnection);
        } else {
            if (super.isOpenTransaction()) {
                throw new EasyQueryException("repeat transaction can't closed connection");
            }
        }
    }
}
