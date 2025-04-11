package com.easy.query.core.proxy.partition.metadata;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.ColumnOption;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.partition.Part1;
import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2024/8/5 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part1EntityMetadata extends EntityMetadata {

    private final EntityMetadata entityMetadata;
    private final JdbcTypeHandler jdbcTypeHandler;

    public Part1EntityMetadata(Class<?> entityClass, EntityMetadata entityMetadata, JdbcTypeHandler jdbcTypeHandler) {
        super(entityClass);
        this.entityMetadata = entityMetadata;
        this.jdbcTypeHandler = jdbcTypeHandler;
        entityMetadataType = EntityMetadataTypeEnum.PARTITION_BY;
        beanConstructorCreator = () -> {
            Part1<Object, Object> r = new Part1<>();
            Object entity = entityMetadata.getBeanConstructorCreator().get();
            r.setEntity(entity);
            return r;
        };
    }

    @Override
    public ColumnMetadata getColumnNotNull(String propertyName) {
        ColumnMetadata columnMetadata = getColumnOrNull(propertyName);
        if (columnMetadata == null) {
            throw new EasyQueryException(String.format("%s not found property:[%s] mapping column name", EasyClassUtil.getSimpleName(getEntityClass()), propertyName));
        }
        return columnMetadata;
    }

    @Override
    public ColumnMetadata getColumnOrNull(String propertyName) {
        ColumnMetadata partitionByColumn = getPartitionByColumn(propertyName);
        if (partitionByColumn != null) {
            return partitionByColumn;
        }
        return entityMetadata.getColumnOrNull(propertyName);
    }

    protected ColumnMetadata getPartitionByColumn(String propertyName) {

        if (isPartitionByColumn(propertyName)) {
            ColumnOption columnOption = new ColumnOption(false, this, propertyName, propertyName, propertyName);
            columnOption.setGetterCaller(obj -> {
                return ((Part1) obj).getPartColumn1();
            });
            columnOption.setSetterCaller((obj, value) -> {
                ((Part1) obj).setPartColumn1(value);
            });
            columnOption.setJdbcTypeHandler(getPartitionJdbcTypeHandler(propertyName));
            return new PartColumnMetadata(columnOption, propertyName);
        }
        return null;
    }

    protected boolean isPartitionByColumn(String propertyName) {
        return Part1.PART_COLUMN1.equals(propertyName);
    }

    protected JdbcTypeHandler getPartitionJdbcTypeHandler(String propertyName) {
        if (Part1.PART_COLUMN1.equals(propertyName)) {
            return jdbcTypeHandler;
        }
        throw new EasyQueryInvalidOperationException("unknown propertyName:[" + propertyName + "]");
    }

    public String getPropertyNameOrNull(String columnName, String def) {
        ColumnMetadata columnMetadata = getColumnMetadataOrNull(columnName);
        if (columnMetadata == null) {
            return def;
        }
        return columnMetadata.getPropertyName();
    }

    @Override
    public ColumnMetadata getColumnMetadataOrNull(String columnName) {
        ColumnMetadata partitionByColumn = getPartitionByColumn(columnName);
        if (partitionByColumn != null) {
            return partitionByColumn;
        }
        return entityMetadata.getColumnMetadataOrNull(columnName);

    }
}
