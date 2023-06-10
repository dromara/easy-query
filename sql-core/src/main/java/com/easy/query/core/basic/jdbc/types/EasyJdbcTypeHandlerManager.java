package com.easy.query.core.basic.jdbc.types;

import com.easy.query.core.basic.jdbc.types.handler.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * @FileName: DefaultJdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:09
 * @author xuejiaming
 */
public class EasyJdbcTypeHandlerManager implements JdbcTypeHandlerManager {
    private static final BigDecimalTypeHandler bigDecimalHandler = new BigDecimalTypeHandler();
    private static final BlobTypeHandler blobTypeHandler = new BlobTypeHandler();
    private static final BooleanTypeHandler booleanDecimalHandler = new BooleanTypeHandler();
    private static final ByteArrayTypeHandler byteArrayTypeHandler = new ByteArrayTypeHandler();
    private static final ByteTypeHandler byteTypeHandler = new ByteTypeHandler();
    private static final CharArrayTypeHandler charArrayTypeHandler = new CharArrayTypeHandler();
    private static final CLobTypeHandler clobTypeHandler = new CLobTypeHandler();
//    private static final DateTypeHandler dateTypeHandler = new DateTypeHandler();
    private static final DoubleTypeHandler doubleTypeHandler = new DoubleTypeHandler();
    private static final FloatTypeHandler floatTypeHandler = new FloatTypeHandler();
    private static final IntegerTypeHandler integerTypeHandler = new IntegerTypeHandler();
    private static final LocalDateTimeTypeHandler localDateTimeHandler = new LocalDateTimeTypeHandler();
    private static final LocalTimeTypeHandler localTimeTypeHandler = new LocalTimeTypeHandler();
    private static final LocalDateTypeHandler localDateHandler = new LocalDateTypeHandler();
    private static final LongTypeHandler longTypeHandler = new LongTypeHandler();
    private static final ShortTypeHandler shortTypeHandler = new ShortTypeHandler();
    private static final SQLDateTypeHandler sqlDateTypeHandler = new SQLDateTypeHandler();
    private static final SQLXMLTypeHandler sqlXMLTypeHandler = new SQLXMLTypeHandler();
    private static final StringTypeHandler stringTypeHandler = new StringTypeHandler();
    private static final UtilDateTypeHandler utilDateTypeHandler = new UtilDateTypeHandler();
    private static final TimestampTypeHandler timestampTypeHandler = new TimestampTypeHandler();
    private static final TimeTypeHandler timeTypeHandler = new TimeTypeHandler();
    private static final JdbcTypeHandler DEFAULT_HANDLER=new ObjectTypeHandler();
    private static final HashMap<Class<?>, JdbcTypeHandler> handlers=new HashMap<>();
    static{
        handlers.put(BigDecimal.class, bigDecimalHandler);
        handlers.put(Boolean.class, booleanDecimalHandler);
        handlers.put(boolean.class, booleanDecimalHandler);
        handlers.put(byte[].class, byteArrayTypeHandler);
        handlers.put(byte.class, byteTypeHandler);
        handlers.put(Byte.class, byteTypeHandler);
        handlers.put(char[].class, charArrayTypeHandler);
//        handlers.put(java.util.Date.class, dateTypeHandler);
        handlers.put(Double.class, doubleTypeHandler);
        handlers.put(double.class, doubleTypeHandler);
        handlers.put(Float.class, floatTypeHandler);
        handlers.put(float.class, floatTypeHandler);
        handlers.put(Integer.class, integerTypeHandler);
        handlers.put(int.class, integerTypeHandler);
        handlers.put(Long.class, longTypeHandler);
        handlers.put(long.class, longTypeHandler);
        handlers.put(Short.class, shortTypeHandler);
        handlers.put(short.class, shortTypeHandler);
        handlers.put(java.sql.Date.class, sqlDateTypeHandler);
        handlers.put(java.util.Date.class, utilDateTypeHandler);
        handlers.put(SQLXML.class, sqlXMLTypeHandler);
        handlers.put(String.class, stringTypeHandler);
        handlers.put(Timestamp.class, timestampTypeHandler);
        handlers.put(Time.class, timeTypeHandler);
        handlers.put(Clob.class, clobTypeHandler);
        handlers.put(Blob.class, blobTypeHandler);
        handlers.put(LocalDateTime.class,localDateTimeHandler);
        handlers.put(LocalDate.class, localDateHandler);
        handlers.put(LocalTime.class, localTimeTypeHandler);
    }
    @Override
    public void appendHandler(Class<?> type, JdbcTypeHandler typeHandler, boolean replace) {
        if(handlers.containsKey(type)){
             if(replace){
                 handlers.put(type,typeHandler);
             }
        }else{
            handlers.put(type,typeHandler);
        }
    }

    @Override
    public JdbcTypeHandler getHandler(Class<?> type) {
        if(type==null){
            return DEFAULT_HANDLER;
        }
        return handlers.getOrDefault(type,DEFAULT_HANDLER);
    }
}
