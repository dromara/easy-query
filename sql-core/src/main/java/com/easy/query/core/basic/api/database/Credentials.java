package com.easy.query.core.basic.api.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2025/9/20 13:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class Credentials {

    public final String jdbcUrl;
    public final String username;
    public final String password;
    public final String serverUrl;
    public final String databaseName;

    public Credentials(String jdbcUrl, String username, String password) {
        this(jdbcUrl, username, password, getServerBaseUrl(jdbcUrl), parseDatabaseName(jdbcUrl));
    }

    public Credentials(String jdbcUrl, String username, String password, String serverUrl, String databaseName) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.serverUrl = serverUrl;
        this.databaseName = databaseName;
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
}
