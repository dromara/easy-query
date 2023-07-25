package com.easy.query.solon.integration.conn;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.datasource.DataSourceUnitFactory;

import javax.sql.DataSource;

/**
 * create time 2023/5/27 23:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class SolonDataSourceUnitFactory implements DataSourceUnitFactory {
    private final EasyQueryOption easyQueryOption;

    public SolonDataSourceUnitFactory(EasyQueryOption easyQueryOption){

        this.easyQueryOption = easyQueryOption;
    }
    @Override
    public DataSourceUnit createDataSourceUnit(String dataSourceName, DataSource dataSource, int mergePoolSize) {
        return new SolonDataSourceUnit(dataSourceName,dataSource,mergePoolSize,easyQueryOption.isWarningBusy());
    }
}
