package com.easy.query.core.proxy;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

/**
 * create time 2023/6/30 22:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropertyProxy {

    String getPropertyName();
    Class<?> getPropertyType();
    JdbcTypeHandler getJdbcTypeHandler();
    boolean isEncryption();
    EncryptionStrategy getEncryptionStrategy();
    ValueConverter<?, ?> getValueConverter();
    void setValue(Object bean,Object value);
    Object getValue(Object bean);
}
