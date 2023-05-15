package com.easy.query.sql.starter;

import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.DataSourceUnit;
import com.easy.query.core.basic.jdbc.con.impl.DefaultEasyConnection;
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

    public SpringEasyConnectionFactory(EasyQueryDataSource easyQueryDataSource) {

        this.easyQueryDataSource = easyQueryDataSource;
    }

    @Override
    public EasyConnection createEasyConnection(String dataSourceName, Integer isolationLevel, ConnectionStrategyEnum connectionStrategy) {
        DataSourceUnit dataSourceUnit = easyQueryDataSource.getDataSourceNotNull(dataSourceName, connectionStrategy);
        return new DefaultEasyConnection(dataSourceName,dataSourceUnit.getStrategy(), DataSourceUtils.getConnection(dataSourceUnit.getDataSource()), isolationLevel);
    }
}
