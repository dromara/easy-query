package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/6/30 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class BasicResultColumnMetadata implements ResultColumnMetadata {
    private final Class<?> propType;
    private final JdbcTypeHandler jdbcTypeHandler;
    private final JdbcProperty jdbcProperty;

    public BasicResultColumnMetadata(Class<?> propType,JdbcTypeHandler jdbcTypeHandler, JdbcProperty jdbcProperty){
        this.propType = propType;
        this.jdbcTypeHandler = jdbcTypeHandler;

        this.jdbcProperty =jdbcProperty;
    }

    @Override
    public Class<?> getPropertyType() {
        return propType;
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
        return jdbcProperty;
    }

    @Override
    public String getPropertyName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JdbcTypeHandler getJdbcTypeHandler() {
        return jdbcTypeHandler;
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
