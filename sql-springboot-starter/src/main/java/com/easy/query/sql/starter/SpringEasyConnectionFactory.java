package com.easy.query.sql.starter;

import com.easy.query.core.basic.jdbc.con.DefaultEasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnectionFactory;
import com.easy.query.core.sharding.EasyQueryDataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * create time 2023/5/12 08:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class SpringEasyConnectionFactory implements EasyConnectionFactory {
    private final EasyQueryDataSource easyQueryDataSource;

    public SpringEasyConnectionFactory(EasyQueryDataSource easyQueryDataSource){

        this.easyQueryDataSource = easyQueryDataSource;
    }
    @Override
    public EasyConnection createEasyConnection(String dataSourceName, Integer isolationLevel) {
        return new DefaultEasyConnection( dataSourceName, DataSourceUtils.getConnection(easyQueryDataSource.getDataSourceNotNull(dataSourceName)),isolationLevel);
    }
}
