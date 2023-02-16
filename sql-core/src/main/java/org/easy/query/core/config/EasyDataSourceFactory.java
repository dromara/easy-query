package org.easy.query.core.config;

import javax.sql.DataSource;

/**
 * @FileName: EasyDataSourceFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/16 21:59
 * @Created by xuejiaming
 */
public interface EasyDataSourceFactory {
    DataSource createDataSource();
}
