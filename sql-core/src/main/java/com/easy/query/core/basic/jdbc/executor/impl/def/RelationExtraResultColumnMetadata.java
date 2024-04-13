package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.props.ColumnJdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.RelationExtraColumn;
import com.easy.query.core.metadata.RelationExtraMetadata;

/**
 * create time 2023/6/30 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationExtraResultColumnMetadata implements ResultColumnMetadata {
    private final RelationExtraMetadata relationExtraMetadata;
    private final JdbcProperty jdbcProperty;
    private final ColumnMetadata columnMetadata;
    private final RelationExtraColumn relationExtraColumn;

    public RelationExtraResultColumnMetadata(int index, RelationExtraMetadata relationExtraMetadata, RelationExtraColumn relationExtraColumn){
        this.relationExtraMetadata = relationExtraMetadata;
        this.columnMetadata = relationExtraColumn.getColumnMetadata();

        this.jdbcProperty =new ColumnJdbcProperty(index,columnMetadata);
        this.relationExtraColumn = relationExtraColumn;
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
    public JdbcProperty getJdbcProperty() {
        return jdbcProperty;
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
        relationExtraMetadata.currentRow().put(relationExtraColumn.getPropertyName(),value);
    }


    @Override
    public Object getValue(Object bean) {
        throw new UnsupportedOperationException();
    }
}
