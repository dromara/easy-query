package com.easy.query.core.basic.jdbc.types;

import java.math.BigDecimal;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
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
        jdbcJavaTypes.put(Integer.valueOf(Types.LONGNVARCHAR), String.class);
        // -15 字符串
        jdbcJavaTypes.put(Integer.valueOf(Types.NCHAR), String.class);
        // -9 字符串
        jdbcJavaTypes.put(Integer.valueOf(Types.NVARCHAR), String.class);
        // -8 字符串
        jdbcJavaTypes.put(Integer.valueOf(Types.ROWID), String.class);
        // -7 布尔
        jdbcJavaTypes.put(Integer.valueOf(Types.BIT), Boolean.class);
        // -6 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.TINYINT), Integer.class);
        // -5 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.BIGINT), Long.class);
        // -4
        jdbcJavaTypes.put(Integer.valueOf(Types.LONGVARBINARY), byte[].class);
        // -3 二进制
        jdbcJavaTypes.put(Integer.valueOf(Types.VARBINARY), byte[].class);
        // -2 二进制
        jdbcJavaTypes.put(Integer.valueOf(Types.BINARY), byte[].class);
        // -1
        jdbcJavaTypes.put(Integer.valueOf(Types.LONGVARCHAR), String.class);
        // 字符串
        // jdbcJavaTypes.put(new Integer(Types.NULL), String.class); // 0
        // 1 字符串
        jdbcJavaTypes.put(Integer.valueOf(Types.CHAR), String.class);
        // 2 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.NUMERIC), BigDecimal.class);
        // 3 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.DECIMAL), BigDecimal.class);
        // 4 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.INTEGER), Integer.class);
        // 5 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.SMALLINT), Integer.class);
        // 6 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.FLOAT), BigDecimal.class);
        // 7 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.REAL), BigDecimal.class);
        // 8 数字
        jdbcJavaTypes.put(Integer.valueOf(Types.DOUBLE), BigDecimal.class);
        // 12 字符串
        jdbcJavaTypes.put(Integer.valueOf(Types.VARCHAR), String.class);
        // 16 布尔
        jdbcJavaTypes.put(Integer.valueOf(Types.BOOLEAN), Boolean.class);
        // jdbcJavaTypes.put(new Integer(Types.DATALINK), String.class); // 70
        // 91 日期
        jdbcJavaTypes.put(Integer.valueOf(Types.DATE), Date.class);
        // 92 日期
        jdbcJavaTypes.put(Integer.valueOf(Types.TIME), Time.class);
        // 93 日期
        jdbcJavaTypes.put(Integer.valueOf(Types.TIMESTAMP), Timestamp.class);
        //
        //		jdbcJavaTypes.put(Types.TIMESTAMP_WITH_TIMEZONE, Timestamp.class);
        //		jdbcJavaTypes.put(Types.TIME_WITH_TIMEZONE, Time.class);
        // 1111 其他类型？
        jdbcJavaTypes.put(Integer.valueOf(Types.OTHER), Object.class);
        // jdbcJavaTypes.put(new Integer(Types.JAVA_OBJECT), Object.class); //
        // 2000
        // jdbcJavaTypes.put(new Integer(Types.DISTINCT), String.class); // 2001
        // jdbcJavaTypes.put(new Integer(Types.STRUCT), String.class); // 2002
        // jdbcJavaTypes.put(new Integer(Types.ARRAY), String.class); // 2003
        // 2004 二进制
        jdbcJavaTypes.put(Integer.valueOf(Types.BLOB), byte[].class);
        // 2005 大文本
        jdbcJavaTypes.put(Integer.valueOf(Types.CLOB), String.class);
        // jdbcJavaTypes.put(new Integer(Types.REF), String.class); // 2006
        // 2009
        jdbcJavaTypes.put(Integer.valueOf(Types.SQLXML), SQLXML.class);
        // 2011 大文本
        jdbcJavaTypes.put(Integer.valueOf(Types.NCLOB), String.class);

        //保留java类型可能对应的sql类型
        for (Map.Entry<Integer, Class<?>> entry : jdbcJavaTypes.entrySet()) {
            javaTypeJdbcs.put(entry.getValue(), entry.getKey());
        }
    }
}
