package com.easy.query.core.basic.jdbc.types;

import java.math.BigDecimal;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/3/28 22:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class JdbcTypes {
    /**
     * jdbc type to java jdbc type 对应的java的type
     */
    public static Map<Integer, Class<?>> jdbcJavaTypes = new HashMap<Integer, Class<?>>();
    /**
     * javaType对应的sqlType，用于一些无schema 数据库表，驱动
     */
    public static Map<Class<?>, Integer> javaTypeJdbcs = new HashMap<Class<?>, Integer>();

    static {
        // 初始化jdbcJavaTypes：
        // -16
        jdbcJavaTypes.put(Types.LONGNVARCHAR, String.class);
        // -15 字符串
        jdbcJavaTypes.put(Types.NCHAR, String.class);
        // -9 字符串
        jdbcJavaTypes.put(Types.NVARCHAR, String.class);
        // -8 字符串
        jdbcJavaTypes.put(Types.ROWID, String.class);
        // -7 布尔
        jdbcJavaTypes.put(Types.BIT, Boolean.class);
        // -6 数字
        jdbcJavaTypes.put(Types.TINYINT, Integer.class);
        // -5 数字
        jdbcJavaTypes.put(Types.BIGINT, Long.class);
        // -4
        jdbcJavaTypes.put(Types.LONGVARBINARY, byte[].class);
        // -3 二进制
        jdbcJavaTypes.put(Types.VARBINARY, byte[].class);
        // -2 二进制
        jdbcJavaTypes.put(Types.BINARY, byte[].class);
        // -1
        jdbcJavaTypes.put(Types.LONGVARCHAR, String.class);
        // 字符串
        // jdbcJavaTypes.put(new Integer(Types.NULL), String.class); // 0
        // 1 字符串
        jdbcJavaTypes.put(Types.CHAR, String.class);
        // 2 数字
        jdbcJavaTypes.put(Types.NUMERIC, BigDecimal.class);
        // 3 数字
        jdbcJavaTypes.put(Types.DECIMAL, BigDecimal.class);
        // 4 数字
        jdbcJavaTypes.put(Types.INTEGER, Integer.class);
        // 5 数字
        jdbcJavaTypes.put(Types.SMALLINT, Integer.class);
        // 6 数字
        jdbcJavaTypes.put(Types.FLOAT, BigDecimal.class);
        // 7 数字
        jdbcJavaTypes.put(Types.REAL, BigDecimal.class);
        // 8 数字
        jdbcJavaTypes.put(Types.DOUBLE, BigDecimal.class);
        // 12 字符串
        jdbcJavaTypes.put(Types.VARCHAR, String.class);
        // 16 布尔
        jdbcJavaTypes.put(Types.BOOLEAN, Boolean.class);
        // jdbcJavaTypes.put(new Integer(Types.DATALINK), String.class); // 70
        // 91 日期
        jdbcJavaTypes.put(Types.DATE, LocalDateTime.class);
        // 92 日期
        jdbcJavaTypes.put(Types.TIME, LocalTime.class);
        // 93 日期
        jdbcJavaTypes.put(Types.TIMESTAMP, Timestamp.class);
        //
        //		jdbcJavaTypes.put(Types.TIMESTAMP_WITH_TIMEZONE, Timestamp.class);
        //		jdbcJavaTypes.put(Types.TIME_WITH_TIMEZONE, Time.class);
        // 1111 其他类型？
        jdbcJavaTypes.put(Types.OTHER, Object.class);
        // jdbcJavaTypes.put(new Integer(Types.JAVA_OBJECT), Object.class); //
        // 2000
        // jdbcJavaTypes.put(new Integer(Types.DISTINCT), String.class); // 2001
        // jdbcJavaTypes.put(new Integer(Types.STRUCT), String.class); // 2002
        // jdbcJavaTypes.put(new Integer(Types.ARRAY), String.class); // 2003
        // 2004 二进制
        jdbcJavaTypes.put(Types.BLOB, byte[].class);
        // 2005 大文本
        jdbcJavaTypes.put(Types.CLOB, String.class);
        // jdbcJavaTypes.put(new Integer(Types.REF), String.class); // 2006
        // 2009
        jdbcJavaTypes.put(Types.SQLXML, SQLXML.class);
        // 2011 大文本
        jdbcJavaTypes.put(Types.NCLOB, String.class);

        //保留java类型可能对应的sql类型
        for (Map.Entry<Integer, Class<?>> entry : jdbcJavaTypes.entrySet()) {
            javaTypeJdbcs.put(entry.getValue(), entry.getKey());
        }
    }
}
