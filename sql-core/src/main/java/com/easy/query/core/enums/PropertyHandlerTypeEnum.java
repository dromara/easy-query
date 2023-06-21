package com.easy.query.core.enums;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * create time 2023/6/20 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public enum PropertyHandlerTypeEnum {
    UNKNOWN,
    BIG_DECIMAL,
    BOOLEAN,
    BYTE_ARRAY,
    BYTE,
    CHAR_ARRAY,
    DOUBLE,
    FLOAT,
    INTEGER,
    LONG,
    SHORT,
    SQL_DATE,
    UTIL_DATE,
    SQLXML,
    STRING,
    TIMESTAMP,
    TIME,
    CLOB,
    BLOB,
    LOCAL_DATE_TIME,
    LOCAL_DATE,
    LOCAL_TIME;
    private static final HashMap<Class<?>, PropertyHandlerTypeEnum> HANDLER_MAPPING =new HashMap<>();
    static{
        HANDLER_MAPPING.put(BigDecimal.class, BIG_DECIMAL);
        HANDLER_MAPPING.put(Boolean.class, BOOLEAN);
        HANDLER_MAPPING.put(boolean.class, BOOLEAN);
        HANDLER_MAPPING.put(byte[].class, BYTE_ARRAY);
        HANDLER_MAPPING.put(byte.class, BYTE);
        HANDLER_MAPPING.put(Byte.class, BYTE);
        HANDLER_MAPPING.put(char[].class, CHAR_ARRAY);
//        handlers.put(java.util.Date.class, dateTypeHandler);
        HANDLER_MAPPING.put(Double.class, DOUBLE);
        HANDLER_MAPPING.put(double.class, DOUBLE);
        HANDLER_MAPPING.put(Float.class, FLOAT);
        HANDLER_MAPPING.put(float.class, FLOAT);
        HANDLER_MAPPING.put(Integer.class, INTEGER);
        HANDLER_MAPPING.put(int.class, INTEGER);
        HANDLER_MAPPING.put(Long.class, LONG);
        HANDLER_MAPPING.put(long.class, LONG);
        HANDLER_MAPPING.put(Short.class, SHORT);
        HANDLER_MAPPING.put(short.class, SHORT);
        HANDLER_MAPPING.put(java.sql.Date.class, SQL_DATE);
        HANDLER_MAPPING.put(java.util.Date.class, UTIL_DATE);
        HANDLER_MAPPING.put(java.sql.SQLXML.class, SQLXML);
        HANDLER_MAPPING.put(String.class, STRING);
        HANDLER_MAPPING.put(Timestamp.class, TIMESTAMP);
        HANDLER_MAPPING.put(Time.class, TIME);
        HANDLER_MAPPING.put(Clob.class, CLOB);
        HANDLER_MAPPING.put(Blob.class, BLOB);
        HANDLER_MAPPING.put(LocalDateTime.class,LOCAL_DATE_TIME);
        HANDLER_MAPPING.put(LocalDate.class, LOCAL_DATE);
        HANDLER_MAPPING.put(LocalTime.class, LOCAL_TIME);
    }
    public static PropertyHandlerTypeEnum getByProperty(Class<?> propertyType){
        if(propertyType==null){
            return UNKNOWN;
        }
        return HANDLER_MAPPING.getOrDefault(propertyType,UNKNOWN);
    }
}
