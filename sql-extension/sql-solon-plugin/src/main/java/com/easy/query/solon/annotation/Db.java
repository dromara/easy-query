package com.easy.query.solon.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据工厂注解
 *
 * 例：
 * @Db("db1") SqlSessionFactory factory;
 * @Db("db1") SqlSession session;
 * @Db("db1") Mapper mapper;
 * */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Db {
    /**
     * sqlSessionFactory bean name
     * */
    String value() default "";
}
