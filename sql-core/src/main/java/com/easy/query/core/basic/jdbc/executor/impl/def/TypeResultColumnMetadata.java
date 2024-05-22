package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2024/5/22 23:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class TypeResultColumnMetadata implements ResultColumnMetadata {
    private final Class<?> propertyType;

    public TypeResultColumnMetadata(Class<?> propertyType){

        this.propertyType = propertyType;
    }
    @Override
    public Class<?> getPropertyType() {
        return propertyType;
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getEntityClass() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JdbcProperty getJdbcProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPropertyName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JdbcTypeHandler getJdbcTypeHandler() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEncryption() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EncryptionStrategy getEncryptionStrategy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValueConverter<?, ?> getValueConverter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValue(Object bean, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getValue(Object bean) {
        throw new UnsupportedOperationException();
    }
}
