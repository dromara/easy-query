package com.easy.query.core.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2025/1/14 16:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyDatabaseUtil {
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

    public static Set<String> getColumns(DataSource dataSource, String sql) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // 获取元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            HashSet<String> columns = new HashSet<>(metaData.getColumnCount());
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(metaData.getColumnName(i));
            }
            return columns;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>(); // 未能获取数据库名称

    }

    public static List<Map<String,Object>> sqlQuery(DataSource dataSource, String sql, List<Object> parameters){

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 1; i <= parameters.size(); i++) {
                statement.setObject(i,parameters.get(i-1));
            }

            List<Map<String,Object>> resultData =new ArrayList<>();
            try(ResultSet resultSet = statement.executeQuery()){
                ResultSetMetaData metaData = resultSet.getMetaData();
                ArrayList<String> columns = new ArrayList<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columns.add(metaData.getColumnName(i));
                }
                while(resultSet.next()){
                    Map<String, Object> result = new HashMap<>();
                    for (int i1 = 0; i1 < columns.size(); i1++) {
                        Object object = resultSet.getObject(i1 + 1);
                        result.put(columns.get(i1), object);
                    }
                    resultData.add(result);
                }
            }

            // 获取元数据
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
