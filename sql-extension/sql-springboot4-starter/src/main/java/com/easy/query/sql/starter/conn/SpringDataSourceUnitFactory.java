package com.easy.query.sql.starter.conn;

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
public class SpringDataSourceUnitFactory implements DataSourceUnitFactory {
    private final EasyQueryOption easyQueryOption;

    public SpringDataSourceUnitFactory(EasyQueryOption easyQueryOption){

        this.easyQueryOption = easyQueryOption;
    }
    @Override
    public DataSourceUnit createDataSourceUnit(String dataSourceName, DataSource dataSource, int mergePoolSize) {
        return new SpringDataSourceUnit(dataSourceName,dataSource,mergePoolSize,easyQueryOption.isWarningBusy());
    }
}
