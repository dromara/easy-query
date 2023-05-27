package com.easy.query.core.sharding;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.DataSourceWrapper;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import javax.sql.DataSource;
import java.util.Map;

/**
 * create time 2023/4/11 13:23
 * 用于管理数据源
 *
 * @author xuejiaming
 */
public interface EasyQueryDataSource {
    /**
     * 默认的数据源名称
     * @return 默认的数据源名称
     */
    String getDefaultDataSourceName();

    /**
     * 默认的数据源
     * @return 默认的数据源
     */
    DataSource getDefaultDataSource();

    /**
     *
     * 添加数据源
     * @param dataSourceName 数据源名称
     * @param dataSource 数据源
     * @param dataSourcePoolSize 数据源连接池大小
     * @return true表示添加成功,false表示添加失败已经存在对应的 {@param dataSourceName}
     */
    boolean addDataSource(String dataSourceName,DataSource dataSource,int dataSourcePoolSize);

    /**
     * 获取所有的数据源包括默认的
     * @return 数据源map
     */
    Map<String, DataSourceUnit> getAllDataSource();


    /**
     * 返回数据源名称对应的数据源没有抛出错误
     * @param dataSourceName 数据源名称
     * @return 数据源
     * @throws EasyQueryInvalidOperationException 如果数据源名称对应的数据源不存在
     */
   default DataSourceWrapper getDataSourceNotNull(String dataSourceName, ConnectionStrategyEnum connectionStrategy){
       DataSourceWrapper dataSource = getDataSourceOrNull(dataSourceName,connectionStrategy);
       if(dataSource==null){
           throw new EasyQueryInvalidOperationException("not found data source :" + dataSourceName);
       }
       return dataSource;
   }

    /**
     * 返回数据源名称对应的数据源没有返回null
     * @param dataSourceName 数据源名称
     * @return 数据源
     */
    DataSourceWrapper getDataSourceOrNull(String dataSourceName, ConnectionStrategyEnum connectionStrategy);
}
