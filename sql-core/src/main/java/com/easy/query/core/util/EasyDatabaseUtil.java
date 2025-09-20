package com.easy.query.core.util;

import com.easy.query.core.basic.api.database.Credentials;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
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
import java.util.Arrays;
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

    public static Set<String> getTableIndexes(DataSource dataSource, String tableName) {
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

    public static Set<String> getTableForeignKeys(DataSource dataSource, String tableName) {
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
    public static void checkAndCreateDatabase(DataSource dataSource, Function<String, String> checkDbSqlFunc, Function<String, String> createDbSqlFunc, Function<DataSource, Credentials> jdbcUrlByDataSourceFunction) {

        // 1. 反射获取 DataSource 的 JDBC URL、用户名、密码
        Function<DataSource, Credentials> getJdbcCredentials = ds -> {
            if (jdbcUrlByDataSourceFunction != null) {
                return jdbcUrlByDataSourceFunction.apply(ds);
            }
            return getCredentialsByReflection(ds);
//            String jdbcUrl = getJdbcUrl(ds);
//            String username = getDataSourceProperty(ds, "getUsername");
//            String password = getDataSourceProperty(ds, "getPassword");
//            return new Credentials(jdbcUrl, username, password);
        };
        Credentials credentials = getJdbcCredentials.apply(dataSource);

        // 2. 解析数据库名称和服务器基础 URL
        String dbName = parseDatabaseName(credentials.jdbcUrl);
        String serverUrl = getServerBaseUrl(credentials.jdbcUrl);

        // 3. 连接到服务器（不带库名），创建数据库
        try (Connection serverConn = DriverManager.getConnection(serverUrl, credentials.username, credentials.password)) {

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
        Class<?> clazz = dataSource.getClass();

        // 1. HikariCP
        try {
            Method method = clazz.getMethod("getJdbcUrl");
            return (String) method.invoke(dataSource);
        } catch (Exception ignored) {
        }

        // 2. Tomcat JDBC / Druid / C3P0
        try {
            Method method = clazz.getMethod("getUrl");
            return (String) method.invoke(dataSource);
        } catch (Exception ignored) {
        }

        // 3. Oracle UCP
        try {
            Method method = clazz.getMethod("getURL"); // 注意 UCP 用的是大写 URL
            return (String) method.invoke(dataSource);
        } catch (Exception ignored) {
        }

        // 4. Agroal DataSource
        try {

            String url = getJdbcUrlFromAgroalDataSource(dataSource);
            if (url != null) {
                return url;
            }
        } catch (Exception ignored) {
        }

        throw new RuntimeException("cant get JDBC URL，unknown DataSource type: " + clazz);
    }

    private static String getJdbcUrlFromAgroalDataSource(DataSource dataSource) {

        try {
            Class<?> clazz = dataSource.getClass();
            Method getConfiguration = clazz.getMethod("getConfiguration");
            Object config = getConfiguration.invoke(dataSource);

            Method getPoolConfig = config.getClass().getMethod("connectionPoolConfiguration");
            getPoolConfig.setAccessible(true);
            Object poolConfig = getPoolConfig.invoke(config);

            Method getFactoryConfig = poolConfig.getClass().getMethod("connectionFactoryConfiguration");
            getFactoryConfig.setAccessible(true); // 必须加
            Object factoryConfig = getFactoryConfig.invoke(poolConfig);

            Method jdbcUrlMethod = factoryConfig.getClass().getMethod("jdbcUrl");
            jdbcUrlMethod.setAccessible(true); // 必须加
            return (String) jdbcUrlMethod.invoke(factoryConfig);
        } catch (Exception ignored) {
        }
        return null;
    }


    public static Credentials getCredentialsByReflection(DataSource dataSource) {
        Objects.requireNonNull(dataSource, "dataSource");

        Class<?> clazz = dataSource.getClass();

        String jdbcUrl = getJdbcUrlByReflection(dataSource);
        // 常见 username / password getter 候选
        List<String> usernameGetters = Arrays.asList(
                "getUsername", "getUserName", "getUser", "getUserID", "getUserId"
        );
        List<String> passwordGetters = Arrays.asList(
                "getPassword", "getPass", "getPwd"
        );

        // 1) 直接在 DataSource 实例上尝试常见 getter
        String username = tryInvokeAnyNoArgStringMethod(clazz, dataSource, usernameGetters);
        String password = tryInvokeAnyNoArgStringMethod(clazz, dataSource, passwordGetters);
        if (username != null || password != null) {
            return new Credentials(jdbcUrl, username, password);
        }

        // 2) 针对一些实现类常见的特定方法（Hikari/HikariCP）
        try {
            // Hikari 有 getJdbcUrl, 其 config 也可能暴露 getUsername/getPassword
            if (clazz.getName().contains("Hikari")) {
                username = tryInvokeAnyNoArgStringMethod(clazz, dataSource, Arrays.asList("getUsername", "getUser"));
                password = tryInvokeAnyNoArgStringMethod(clazz, dataSource, Arrays.asList("getPassword"));
                if (username != null || password != null) {
                    return new Credentials(jdbcUrl, username, password);
                }
            }
        } catch (Throwable ignored) {
        }

        // 3) 尝试 Tomcat/DBCP/Druid/C3P0/Oracle UCP 等常见的 getter（名字通常相似）
        try {
            username = tryInvokeAnyNoArgStringMethod(clazz, dataSource, usernameGetters);
            password = tryInvokeAnyNoArgStringMethod(clazz, dataSource, passwordGetters);
            if (username != null || password != null) {
                return new Credentials(jdbcUrl, username, password);
            }
        } catch (Throwable ignored) {
        }

        // 如果还是没有b报错
        throw new EasyQueryInvalidOperationException("Cannot get credentials from data source");
    }

    // 尝试一组方法名，返回第一个成功的 String 结果；会对 Method 做 setAccessible(true)
    private static String tryInvokeAnyNoArgStringMethod(Class<?> clazz, Object instance, List<String> methodNames) {
        for (String name : methodNames) {
            try {
                Method m = findMethodInHierarchy(clazz, name);
                if (m == null) continue;
                m.setAccessible(true);
                Object val = m.invoke(instance);
                if (val != null) return val.toString();
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    // 在类及其接口/父类中寻找无参方法（优先 public，然后 declared）
    private static Method findMethodInHierarchy(Class<?> clazz, String methodName) {
        // 先直接尝试 getMethod (public)
        try {
            return clazz.getMethod(methodName);
        } catch (NoSuchMethodException ignored) {
        }

        // 再尝试 declared 方法（包括非 public）
        Class<?> c = clazz;
        while (c != null) {
            try {
                Method m = c.getDeclaredMethod(methodName);
                if (m != null) return m;
            } catch (NoSuchMethodException ignored) {
            }
            c = c.getSuperclass();
        }

        // 再尝试接口上可能定义的方法（有时实现是匿名类）
        for (Class<?> i : clazz.getInterfaces()) {
            try {
                Method m = i.getMethod(methodName);
                if (m != null) return m;
            } catch (NoSuchMethodException ignored) {
            }
        }

        return null;
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
