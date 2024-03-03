package com.easy.query.api.proxy.key;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * create time 2024/3/2 19:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapKeys {
    private MapKeys(){}

    public static MapKey<String> stringKey(String keyName){
        return new StringMapKey(keyName);
    }
    public static MapKey<Integer> integerKey(String keyName){
        return new IntegerMapKey(keyName);
    }
    public static MapKey<LocalDateTime> localDateTimeKey(String keyName){
        return new LocalDateTimeMapKey(keyName);
    }
    public static MapKey<BigDecimal> bigDecimalKey(String keyName){
        return new BigDecimalMapKey(keyName);
    }
    public static MapKey<Boolean> booleanKey(String keyName){
        return new BooleanMapKey(keyName);
    }
    public static MapKey<Byte[]> byteArrayKey(String keyName){
        return new ByteArrayMapKey(keyName);
    }
    public static MapKey<Byte> byteKey(String keyName){
        return new ByteMapKey(keyName);
    }
    public static MapKey<Double> doubleKey(String keyName){
        return new DoubleMapKey(keyName);
    }
    public static MapKey<Float> floatKey(String keyName){
        return new FloatMapKey(keyName);
    }
    public static <TEntity> MapKey<List<TEntity>> listKey(String keyName){
        return new ListMapKey<>(keyName);
    }
    public static MapKey<LocalDate> localDateKey(String keyName){
        return new LocalDateMapKey(keyName);
    }
    public static MapKey<LocalTime> localTimeKey(String keyName){
        return new LocalTimeMapKey(keyName);
    }
    public static MapKey<Long> longKey(String keyName){
        return new LongMapKey(keyName);
    }
    public static MapKey<Short> shortKey(String keyName){
        return new ShortMapKey(keyName);
    }
    public static MapKey<java.sql.Date> sqlDateKey(String keyName){
        return new SQLDateMapKey(keyName);
    }
    public static MapKey<Time> timeKey(String keyName){
        return new TimeMapKey(keyName);
    }
    public static MapKey<Timestamp> timestampKey(String keyName){
        return new TimestampMapKey(keyName);
    }
    public static MapKey<java.util.Date> utilDateKey(String keyName){
        return new UtilDateMapKey(keyName);
    }
}
