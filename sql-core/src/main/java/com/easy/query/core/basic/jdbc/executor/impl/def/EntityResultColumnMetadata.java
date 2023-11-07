package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.executor.internal.props.ColumnJdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyBeanUtil;

/**
 * create time 2023/6/30 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityResultColumnMetadata implements ResultColumnMetadata {
    private final EntityMetadata entityMetadata;
    private final ColumnMetadata columnMetadata;
    private final JdbcProperty dataReader;

    public EntityResultColumnMetadata(int index, EntityMetadata entityMetadata, ColumnMetadata columnMetadata){
        this.entityMetadata = entityMetadata;

        this.columnMetadata = columnMetadata;
        this.dataReader=new ColumnJdbcProperty(index,columnMetadata);
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return this.columnMetadata;
    }

    @Override
    public Class<?> getEntityClass() {
        return columnMetadata.getEntityMetadata().getEntityClass();
    }

    @Override
    public JdbcProperty getDataReader() {
        return dataReader;
    }

    @Override
    public String getPropertyName() {
        return columnMetadata.getPropertyName();
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
        if (entityMetadata.isHasValueObject() && columnMetadata.getPropertyName().contains(".")) {
            EasyBeanUtil.setPropertyValue(bean, entityMetadata, columnMetadata, value, true);
        } else {
            EasyBeanUtil.setCurrentPropertyValue(bean, columnMetadata, value);
        }
    }


    @Override
    public Object getValue(Object bean) {
        return EasyBeanUtil.getPropertyValue(bean,entityMetadata,columnMetadata);
    }
}
