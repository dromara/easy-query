package com.easy.query.core.basic.jdbc.types;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.easy.query.core.basic.jdbc.types.handler.BigDecimalTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.BlobTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.BooleanTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.ByteArrayTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.ByteTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.CLobTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.CharArrayTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.DoubleTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.FloatTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.IntegerTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.LocalDateTimeTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.LocalDateTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.LocalTimeTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.LongTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.ObjectTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.SQLDateTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.SQLXMLTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.ShortTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.StringTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.TimeTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.TimestampTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.UUIDTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.UtilDateTypeHandler;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.util.EasyClassUtil;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/2/17 22:09
 *
 * @author xuejiaming
 */
public class EasyJdbcTypeHandlerManager implements JdbcTypeHandlerManager {
    private static final BigDecimalTypeHandler bigDecimalHandler = new BigDecimalTypeHandler();
    private static final BlobTypeHandler blobTypeHandler = new BlobTypeHandler();
    private static final BooleanTypeHandler booleanTypeHandler = new BooleanTypeHandler();
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
    private static final JdbcTypeHandler uuidTypeHandler = new UUIDTypeHandler();
    private static final JdbcTypeHandler DEFAULT_HANDLER = new ObjectTypeHandler();
    private final Map<Class<?>, JdbcTypeHandler> handlers = new ConcurrentHashMap<>();
    private final Map<Class<?>, JdbcTypeHandler> handlerTypes = new ConcurrentHashMap<>();

    public EasyJdbcTypeHandlerManager() {
        handlers.put(BigDecimal.class, bigDecimalHandler);
        handlers.put(Boolean.class, booleanTypeHandler);
        handlers.put(boolean.class, booleanTypeHandler);
        handlers.put(byte[].class, byteArrayTypeHandler);
        handlers.put(byte.class, byteTypeHandler);
        handlers.put(Byte.class, byteTypeHandler);
        handlers.put(char[].class, charArrayTypeHandler);
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
        handlers.put(LocalDateTime.class, localDateTimeHandler);
        handlers.put(LocalDate.class, localDateHandler);
        handlers.put(LocalTime.class, localTimeTypeHandler);
        handlers.put(UUID.class, uuidTypeHandler);
        handlers.put(Object.class, DEFAULT_HANDLER);
    }

    @Override
    public void appendHandler(@NotNull Class<?> type, @NotNull JdbcTypeHandler typeHandler, boolean replace) {
        Objects.requireNonNull(type, "type is null.");
        Objects.requireNonNull(typeHandler, "typeHandler is null.");
        if (handlers.containsKey(type)) {
            if (replace) {
                handlers.put(type, typeHandler);
            }
        } else {
            handlers.put(type, typeHandler);
        }
    }

    @Override
    public void appendHandlerOnly(@NotNull JdbcTypeHandler typeHandler) {
        Objects.requireNonNull(typeHandler, "typeHandler is null.");
        handlerTypes.putIfAbsent(typeHandler.getClass(), typeHandler);
    }

    @NotNull
    @Override
    public JdbcTypeHandler getHandler(@Nullable Class<?> type) {
        if (type == null) {
            return DEFAULT_HANDLER;
        }
        return handlers.getOrDefault(type, DEFAULT_HANDLER);
    }

    @NotNull
    @Override
    public JdbcTypeHandler getHandlerByHandlerClass(@NotNull Class<?> handlerType) {
        JdbcTypeHandler jdbcTypeHandler = handlerTypes.get(handlerType);
        if (jdbcTypeHandler == null) {
            throw new EasyQueryInvalidOperationException("unknown type handler:" + EasyClassUtil.getSimpleName(handlerType));
        }
        return jdbcTypeHandler;
    }
}
