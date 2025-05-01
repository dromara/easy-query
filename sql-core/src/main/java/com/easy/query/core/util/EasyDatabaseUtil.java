package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2025/1/14 16:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyDatabaseUtil {
    private static final Log log = LogFactory.getLog(EasyDatabaseUtil.class);

    /**
     * 从 DataSource 中解析 JDBC URL（通过反射，兼容常见连接池）
     */
    public static String getJdbcUrl(DataSource dataSource) {
        try {
            // 尝试通过常见方法获取 URL（如 HikariCP、Tomcat JDBC 等）
            Method getUrlMethod = dataSource.getClass().getMethod("getJdbcUrl");
            return (String) getUrlMethod.invoke(dataSource);
        } catch (Exception e) {
            // 若反射失败，尝试其他方式（如 Spring 的 DriverManagerDataSource）
            try {
                Method getUrlMethod = dataSource.getClass().getMethod("getUrl");
                return (String) getUrlMethod.invoke(dataSource);
            } catch (Exception ex) {
                throw new RuntimeException("无法获取 JDBC URL", ex);
            }
        }
    }

    /**
     * 获取当前使用的数据库名称
     *
     * @param dataSource 数据源
     * @return 数据库名称
     */
    public static String getDatabaseName(DataSource dataSource, Supplier<String> def) {
        try (Connection connection = dataSource.getConnection()) {
            // 方法 1: 使用 getCatalog()
            return connection.getCatalog();
//            String databaseName = connection.getCatalog();
//            if (databaseName != null) {
//                return databaseName;
//            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (def == null) {
            return null;
        }
        return def.get(); // 未能获取数据库名称
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
            log.error("get columns error:" + e.getMessage(), e);
        }
        return new HashSet<>(); // 未能获取数据库列信息

    }
    public static Set<String> getTableIndexes(DataSource dataSource,String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getIndexInfo(null, null, tableName, false, false);
            // 获取元数据
            HashSet<String> indexes = new HashSet<>();
            while (resultSet.next()) {
                String indexName = resultSet.getString("INDEX_NAME");
                indexes.add(indexName);

            }
            return indexes;
        } catch (Exception e) {
            log.error("get indexes error:" + e.getMessage(), e);
        }
        return new HashSet<>(); // 未能获取数据库索引信息

    }
    public static Set<String> getTableForeignKeys(DataSource dataSource,String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getImportedKeys(null, null, tableName);
            // 获取元数据
            HashSet<String> foreignKeys = new HashSet<>();
            while (resultSet.next()) {
                String fkName = resultSet.getString("FK_NAME");
                foreignKeys.add(fkName);

            }
            return foreignKeys;
        } catch (Exception e) {
            log.error("get indexes error:" + e.getMessage(), e);
        }
        return new HashSet<>(); // 未能获取数据库索引信息

    }

    public static List<Map<String, Object>> sqlQuery(DataSource dataSource, String sql, List<Object> parameters) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 1; i <= parameters.size(); i++) {
                statement.setObject(i, parameters.get(i - 1));
            }

            List<Map<String, Object>> resultData = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                ArrayList<String> columns = new ArrayList<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columns.add(metaData.getColumnName(i));
                }
                while (resultSet.next()) {
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
            throw new EasyQuerySQLCommandException(e);
        }
    }


    /**
     * 检查并自动创建数据库（如果不存在）
     */
    public static void checkAndCreateDatabase(DataSource dataSource, Function<String, String> checkDbSqlFunc, Function<String, String> createDbSqlFunc) {

        // 1. 反射获取 DataSource 的 JDBC URL、用户名、密码
        String jdbcUrl = getJdbcUrlByReflection(dataSource);
        String username = getDataSourceProperty(dataSource, "getUsername");
        String password = getDataSourceProperty(dataSource, "getPassword");

        // 2. 解析数据库名称和服务器基础 URL
        String dbName = parseDatabaseName(jdbcUrl);
        String serverUrl = getServerBaseUrl(jdbcUrl);

        // 3. 连接到服务器（不带库名），创建数据库
        try (Connection serverConn = DriverManager.getConnection(serverUrl, username, password)) {

            boolean databaseExist = false;
            try (Statement stmt = serverConn.createStatement()) {
                String checkDbSQL = checkDbSqlFunc.apply(dbName);
                log.info("check db sql:" + checkDbSQL);
                ResultSet resultSet = stmt.executeQuery(checkDbSQL);
                databaseExist = resultSet.next();
            }
            if (!databaseExist) {
                try (Statement stmt = serverConn.createStatement()) {
                    String createDbSQL = createDbSqlFunc.apply(dbName);
                    log.info("create db sql:" + createDbSQL);
                    stmt.execute(createDbSQL);
                }
            }
//            String createDbSql = generateCreateDatabaseSql(dbName, jdbcUrl);
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    // --------------- 私有工具方法 ---------------

    /**
     * 反射获取 DataSource 的 JDBC URL
     */
    public static String getJdbcUrlByReflection(DataSource dataSource) {
        try {
            // 尝试常见方法名: getJdbcUrl (HikariCP), getUrl (Tomcat JDBC)
            Method getUrlMethod = dataSource.getClass().getMethod("getJdbcUrl");
            return (String) getUrlMethod.invoke(dataSource);
        } catch (Exception e1) {
            try {
                Method getUrlMethod = dataSource.getClass().getMethod("getUrl");
                return (String) getUrlMethod.invoke(dataSource);
            } catch (Exception e2) {
                throw new EasyQuerySQLCommandException("无法获取 JDBC URL", e2);
            }
        }
    }

    /**
     * 反射获取 DataSource 的用户名或密码
     */
    private static String getDataSourceProperty(DataSource dataSource, String methodName) {
        try {
            Method method = dataSource.getClass().getMethod(methodName);
            return (String) method.invoke(dataSource);
        } catch (Exception e) {
            throw new EasyQueryInvalidOperationException("cant get datasource property: " + methodName, e);
        }
    }

    /**
     * 从 JDBC URL 解析数据库名称
     */
    public static String parseDatabaseName(String jdbcUrl) {
        // 匹配通用模式：jdbc:协议://主机/数据库名
        Pattern genericPattern = Pattern.compile("jdbc:[^:]+://[^/]+/([^/?]+)");
        Matcher genericMatcher = genericPattern.matcher(jdbcUrl);
        if (genericMatcher.find()) {
            return genericMatcher.group(1);
        }

        // 匹配 SQL Server 的 database 参数
        Pattern sqlServerPattern = Pattern.compile(";database=([^;]+)", Pattern.CASE_INSENSITIVE);
        Matcher sqlServerMatcher = sqlServerPattern.matcher(jdbcUrl);
        if (sqlServerMatcher.find()) {
            return sqlServerMatcher.group(1);
        }

        // 匹配 Oracle 的 SID 或 Service Name（简化示例）
        Pattern oraclePattern = Pattern.compile("jdbc:oracle:thin:@[^:]+:[^:]+:(\\w+)");
        Matcher oracleMatcher = oraclePattern.matcher(jdbcUrl);
        if (oracleMatcher.find()) {
            return oracleMatcher.group(1);
        }

        throw new IllegalArgumentException("cant parse database name: " + jdbcUrl);
    }

    /**
     * 获取服务器基础 URL（不带库名）
     */
    public static String getServerBaseUrl(String jdbcUrl) {
        String serverBaseUrl0 = getServerBaseUrl0(jdbcUrl);
        if (jdbcUrl.contains("?")) {
            return serverBaseUrl0 + "?" + jdbcUrl.split("\\?")[1];
        }
        return serverBaseUrl0;
    }

    public static String getServerBaseUrl0(String jdbcUrl) {
        // 处理不同数据库的 URL 格式
        if (jdbcUrl.contains("jdbc:sqlserver:")) {
            return jdbcUrl.replaceAll("(?i);databaseName=[^;]+", "");
        } else {
            return jdbcUrl.replaceFirst("/[^/?]+([?].*)?$", "/");
        }
    }
}
