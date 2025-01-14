package com.easy.query.core.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * create time 2025/1/14 16:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyDatabaseNameUtil {
    /**
     * 获取当前使用的数据库名称
     *
     * @param dataSource 数据源
     * @return 数据库名称
     */
    public static String getDatabaseName(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            // 方法 1: 使用 getCatalog()
            String databaseName = connection.getCatalog();
            if (databaseName != null) {
                return databaseName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 未能获取数据库名称
    }
}
