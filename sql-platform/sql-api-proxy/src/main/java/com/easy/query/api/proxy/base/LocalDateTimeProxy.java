package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

import java.time.LocalDateTime;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateTimeProxy extends AbstractBasicProxyEntity<LocalDateTimeProxy, LocalDateTime> {//implements ColumnDateTimeFunctionAvailable<LocalDateTime>
    public static LocalDateTimeProxy createTable() {
        return new LocalDateTimeProxy();
    }
    private static final Class<LocalDateTime> entityClass = LocalDateTime.class;

    private LocalDateTimeProxy() {
    }
    public LocalDateTimeProxy(LocalDateTime val) {
        set(val);
    }


    public LocalDateTimeProxy(PropTypeColumn<LocalDateTime> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<LocalDateTime> getEntityClass() {
        return entityClass;
    }


//
//    @Override
//    public String getValue() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Class<?> getPropertyType() {
//        return LocalDateTime.class;
//    }
//
//    @Override
//    public <TR> void _setPropertyType(Class<TR> clazz) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void accept(OrderSelector s) {
//        SQLFunctionExpressionUtil.accept(s,null,func());
//    }
//
//    @Override
//    public Function<SQLFunc, SQLFunction> func() {
//        return new constsql;
//    }
//
//    @Override
//    public String getValue() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Class<?> getPropertyType() {
//        return LocalDateTime.class;
//    }
//
//    @Override
//    public <TR> void _setPropertyType(Class<TR> clazz) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public <TR> PropTypeColumn<TR> setPropertyType(Class<TR> clazz) {
//        throw new UnsupportedOperationException();
//    }
}