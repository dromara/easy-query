package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

/**
 * create time 2023/6/30 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ResultColumnMetadata {
    DataReader getDataReader();
    String getPropertyName();
    JdbcTypeHandler getJdbcTypeHandler();
    boolean isEncryption();
    EncryptionStrategy getEncryptionStrategy();
    ValueConverter<?, ?> getValueConverter();
    void setValue(Object bean,Object value);
    Object getValue(Object bean);
}
