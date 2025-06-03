package com.easy.query.test.cache;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2025/6/2 22:11
 * 文件说明
 *
 * @author xuejiaming
 */

public class JsonUtil {
    /**
     * JSON对象转换类
     */
    public static ObjectMapper jsonMapper = null; //转换器

    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    static {
        jsonMapper = new ObjectMapper(); //转换器
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略未知字段
        jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);//如果是空对象忽略序列化错误
        jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);// 忽略字段大小写
        //序列化的时候序列对象的所有属性
        jsonMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        jsonMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));

        jsonMapper.registerModule(javaTimeModule);

        // 声明一个简单Module 对象
        SimpleModule module = new SimpleModule();
//        // 给Module 添加一个序列化器
//        module.addSerializer(Enumerator.class, new JsonSerializer<Enumerator>() {
//            @Override
//            public void serialize(Enumerator value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                gen.writeNumber(value.getCode());
//            }
//        });
//        module.addDeserializer(Enum.class, new EnumeratorDeserializer());
        jsonMapper.registerModule(module);
    }


    /**
     * 对象转JSON字符串
     * @param object
     * @return
     * @throws JsonSerialException
     */
    public static String object2JsonStr(Object object)
    {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonSerialException(e);
        }
    }

    /**
     * JSONl字符串转对象
     * @param <T>
     * @param json
     * @param valueType
     * @return
     * @throws JsonSerialException
     */
    public static <T> T jsonStr2Object(String json, Class<T> valueType){
        try {
            return jsonMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            throw new JsonSerialException(e);
        }
    }
    public static <T> T jsonStr2Object(String json, JavaType javaType){
        try {
            return jsonMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new JsonSerialException(e);
        }
    }

    /**
     * JSON字符串转成对象
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     * @throws JsonSerialException
     */
    public static <T> T jsonStr2Object(String json, TypeReference<T> typeReference){
        try {
            return jsonMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new JsonSerialException(e);
        }
    }

    /**
     * JSONl字符串转对象
     * @param <T>
     * @param json
     * @param typeReference
     * @return
     * @throws JsonSerialException
     */
    public static <T> T jsonStr2List(String json, TypeReference<T> typeReference){
        try {
            return jsonMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new JsonSerialException(e);
        }
    }
}