package com.easy.query.core.proxy.impl;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.proxy.BeanProxy;
import com.easy.query.core.proxy.PropertyProxy;

/**
 * create time 2023/6/30 22:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyProxyImpl implements PropertyProxy {
    private final BeanProxy beanProxy;
    private final String propertyName;
    private final Class<?> propertyType;
    private final JdbcTypeHandler jdbcTypeHandler;
    private final boolean isEncryption;
    private final EncryptionStrategy encryptionStrategy;
    private final ValueConverter<?, ?> valueConverter;

    public PropertyProxyImpl(BeanProxy beanProxy, String propertyName, Class<?> propertyType, JdbcTypeHandler jdbcTypeHandler, boolean isEncryption, EncryptionStrategy encryptionStrategy, ValueConverter<?, ?> valueConverter) {

        this.beanProxy = beanProxy;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.jdbcTypeHandler = jdbcTypeHandler;
        this.isEncryption = isEncryption;
        this.encryptionStrategy = encryptionStrategy;
        this.valueConverter = valueConverter;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Class<?> getPropertyType() {
        return propertyType;
    }

    @Override
    public JdbcTypeHandler getJdbcTypeHandler() {
        return jdbcTypeHandler;
    }

    @Override
    public boolean isEncryption() {
        return isEncryption;
    }

    @Override
    public EncryptionStrategy getEncryptionStrategy() {
        return encryptionStrategy;
    }

    @Override
    public ValueConverter<?, ?> getValueConverter() {
        return valueConverter;
    }

    @Override
    public void setValue(Object bean, Object value) {
        beanProxy.setValue(bean, propertyName, value);
    }

    @Override
    public Object getValue(Object bean) {
        return beanProxy.getValue(bean,propertyName);
    }
}
