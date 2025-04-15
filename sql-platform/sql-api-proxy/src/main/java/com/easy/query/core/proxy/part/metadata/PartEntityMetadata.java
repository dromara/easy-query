package com.easy.query.core.proxy.part.metadata;

import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.ColumnOption;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Map;
import java.util.function.Supplier;

/**
 * create time 2024/8/5 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartEntityMetadata extends EntityMetadata {

    private final EntityMetadata entityMetadata;
    private final Map<String, PartColumn> partColumnMap;

    public PartEntityMetadata(Class<?> entityClass, EntityMetadata entityMetadata, Supplier<Object> beanConstructorCreator, Map<String, PartColumn> partColumnMap) {
        super(entityClass);
        this.entityMetadata = entityMetadata;
        this.partColumnMap = partColumnMap;
        entityMetadataType = EntityMetadataTypeEnum.PART;
        this.beanConstructorCreator = beanConstructorCreator;
//        beanConstructorCreator = () -> {
//            Part1<Object, Object> r = new Part1<>();
//            Object entity = entityMetadata.getBeanConstructorCreator().get();
//            r.setEntity(entity);
//            return r;
//        };
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
        PartColumn partColumn = partColumnMap.get(propertyName);
        if (partColumn != null) {
            ColumnOption columnOption = new ColumnOption(false, this, propertyName, propertyName, propertyName);
            columnOption.setGetterCaller(partColumn.getGetterCaller());
            columnOption.setSetterCaller(partColumn.getSetterCaller());
//            columnOption.setGetterCaller(obj -> {
//                return ((Part1) obj).getPartColumn1();
//            });
//            columnOption.setSetterCaller((obj, value) -> {
//                ((Part1) obj).setPartColumn1(value);
//            });
            columnOption.setJdbcTypeHandler(partColumn.getJdbcTypeHandler());
            return new PartColumnMetadata(columnOption, propertyName);
        }
        return null;
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
