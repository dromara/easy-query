//package org.easy.query.core.configuration.types;
//
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
///**
// * @FileName: AbstractEntityTypeConfiguration.java
// * @Description: 文件说明
// * @Date: 2023/2/27 08:36
// * @Created by xuejiaming
// */
//public abstract class AbstractEntityTypeConfiguration<T> implements EntityTypeConfiguration<T> {
//    @Override
//    public Class<T> entityType() {
//
//        Type genericSuperclass = this.getClass().getGenericSuperclass();
//        return (Class<T>)((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
//    }
//}
