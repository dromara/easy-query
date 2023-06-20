package com.easy.query.core.basic.jdbc.types;


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
import com.easy.query.core.basic.jdbc.types.handler.UtilDateTypeHandler;
import com.easy.query.core.enums.PropertyHandlerTypeEnum;

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

    @Override
    public JdbcTypeHandler getHandler(PropertyHandlerTypeEnum propertyHandlerType) {
        switch (propertyHandlerType){
            case BIG_DECIMAL:return bigDecimalHandler;
            case BOOLEAN:return booleanDecimalHandler;
            case BYTE_ARRAY:return byteArrayTypeHandler;
            case BYTE:return byteTypeHandler;
            case CHAR_ARRAY:return charArrayTypeHandler;
            case DOUBLE:return doubleTypeHandler;
            case FLOAT:return floatTypeHandler;
            case INTEGER:return integerTypeHandler;
            case LONG:return longTypeHandler;
            case SHORT:return shortTypeHandler;
            case SQL_DATE:return sqlDateTypeHandler;
            case UTIL_DATE:return utilDateTypeHandler;
            case SQLXML:return sqlXMLTypeHandler;
            case STRING:return stringTypeHandler;
            case TIMESTAMP:return timestampTypeHandler;
            case TIME:return timeTypeHandler;
            case CLOB:return clobTypeHandler;
            case BLOB:return blobTypeHandler;
            case LOCAL_DATE_TIME:return localDateTimeHandler;
            case LOCAL_DATE:return localDateHandler;
            case LOCAL_TIME:return localTimeTypeHandler;
        }
        return DEFAULT_HANDLER;
    }
}
