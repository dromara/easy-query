package com.easy.query.core.datasource;

import com.easy.query.core.configuration.EasyQueryOption;

import javax.sql.DataSource;

/**
 * create time 2023/5/27 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceUnitFactory implements DataSourceUnitFactory{
    private final EasyQueryOption easyQueryOption;

    public DefaultDataSourceUnitFactory(EasyQueryOption easyQueryOption){

        this.easyQueryOption = easyQueryOption;
    }
    @Override
    public DataSourceUnit createDataSourceUnit(String dataSourceName, DataSource dataSource, int dataSourcePool) {
        return new DefaultDataSourceUnit(dataSourceName,dataSource,dataSourcePool,easyQueryOption.isWarningBusy());
    }
}
