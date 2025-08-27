package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.TreeDeepItem;

import java.util.List;

/**
 * create time 2023/6/30 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class DeepResultColumnMetadata implements ResultColumnMetadata {
    private final List<TreeDeepItem> treeDeepItems;
    private final JdbcTypeHandler jdbcTypeHandler;
    private final JdbcProperty jdbcProperty;

    public DeepResultColumnMetadata(List<TreeDeepItem> treeDeepItems, JdbcTypeHandler jdbcTypeHandler, JdbcProperty jdbcProperty){
        this.treeDeepItems = treeDeepItems;
        this.jdbcTypeHandler = jdbcTypeHandler;

        this.jdbcProperty =jdbcProperty;
    }

    @Override
    public Class<?> getPropertyType() {
        return jdbcProperty.getPropertyType();
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getEntityClass() {
        return null;
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
        return false;
    }

    @Override
    public EncryptionStrategy getEncryptionStrategy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValueConverter<?, ?> getValueConverter() {
        return null;
    }

    @Override
    public void setValue(Object bean, Object value) {
        treeDeepItems.add(new TreeDeepItem((long)value));
    }


    @Override
    public Object getValue(Object bean) {
        throw new UnsupportedOperationException();
    }
}
