package com.easy.query.core.basic.jdbc.con.impl;

import com.easy.query.core.basic.jdbc.con.EasyDataSourceConnection;
import com.easy.query.core.basic.jdbc.con.EasyDataSourceConnectionFactory;
import com.easy.query.core.sharding.EasyQueryDataSource;

/**
 * create time 2023/5/12 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultEasyDataSourceConnectionFactory implements EasyDataSourceConnectionFactory {
    private final EasyQueryDataSource easyQueryDataSource;

    public DefaultEasyDataSourceConnectionFactory(EasyQueryDataSource easyQueryDataSource){

        this.easyQueryDataSource = easyQueryDataSource;
    }
    @Override
    public EasyDataSourceConnection create() {
        return new DefaultEasyDataSourceConnection(easyQueryDataSource);
    }
}
