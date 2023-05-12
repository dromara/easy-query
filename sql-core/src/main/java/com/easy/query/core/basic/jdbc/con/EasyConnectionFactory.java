package com.easy.query.core.basic.jdbc.con;

/**
 * create time 2023/5/12 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyConnectionFactory {
    EasyConnection createEasyConnection(String dataSourceName, Integer isolationLevel);
}
