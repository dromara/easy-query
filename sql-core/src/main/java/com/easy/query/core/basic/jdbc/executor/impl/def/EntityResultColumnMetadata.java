package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/6/30 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityResultColumnMetadata implements ResultColumnMetadata {
    private final ColumnMetadata columnMetadata;

    public EntityResultColumnMetadata(ColumnMetadata columnMetadata){

        this.columnMetadata = columnMetadata;
    }
    @Override
    public String getPropertyName() {
        return columnMetadata.getPropertyName();
    }

    @Override
    public Class<?> getPropertyType() {
        return columnMetadata.getPropertyType();
    }

    @Override
    public JdbcTypeHandler getJdbcTypeHandler() {
        return columnMetadata.getJdbcTypeHandler();
    }

    @Override
    public boolean isEncryption() {
        return columnMetadata.isEncryption();
    }

    @Override
    public EncryptionStrategy getEncryptionStrategy() {
        return columnMetadata.getEncryptionStrategy();
    }

    @Override
    public ValueConverter<?, ?> getValueConverter() {
        return columnMetadata.getValueConverter();
    }

    @Override
    public void setValue(Object bean, Object value) {
        columnMetadata.getSetterCaller().call(bean,value);
    }

    @Override
    public Object getValue(Object bean) {
        return columnMetadata.getGetterCaller().apply(bean);
    }
}
