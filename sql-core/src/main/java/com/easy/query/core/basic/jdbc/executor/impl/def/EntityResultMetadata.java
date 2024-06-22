package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.Map;
import java.util.Objects;

/**
 * create time 2023/6/30 21:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityResultMetadata<TR> implements ResultMetadata<TR> {
    protected final EntityMetadata entityMetadata;

    protected final DataReader dataReader;

    public EntityResultMetadata(EntityMetadata entityMetadata) {
        this(entityMetadata, null);
    }

    public EntityResultMetadata(EntityMetadata entityMetadata, DataReader dataReader) {
        this.entityMetadata = entityMetadata;
        this.dataReader = dataReader;
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public Class<TR> getResultClass() {
        return EasyObjectUtil.typeCastNullable(entityMetadata.getEntityClass());
    }

    @Override
    public DataReader getDataReader() {
        return null;
    }

    @Override
    public EntityMetadataTypeEnum getEntityMetadataType() {
        return entityMetadata.getEntityMetadataType();
    }

    @Override
    public TR newBean() {
        return EasyObjectUtil.typeCastNullable(entityMetadata.getBeanConstructorCreator().get());
    }

    @Override
    public ResultColumnMetadata getResultColumnOrNullByColumnName(int index, String columnName) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnMetadataOrNull(columnName);
        if (columnMetadata != null) {
            return new EntityResultColumnMetadata(index, entityMetadata, columnMetadata);
        }
        return null;
    }

    @Override
    public ResultColumnMetadata getResultColumnOrNullByPropertyName(int index, String propertyName) {
        if(propertyName==null){
            return null;
        }
        ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(propertyName);
        if (columnMetadata != null) {
            return new EntityResultColumnMetadata(index, entityMetadata, columnMetadata);
        }else{
            for (Map.Entry<String, ColumnMetadata> columnMetadataKv : entityMetadata.getProperty2ColumnMap().entrySet()) {
                if(propertyName.equalsIgnoreCase(columnMetadataKv.getKey())){
                    return new EntityResultColumnMetadata(index, entityMetadata, columnMetadataKv.getValue());
                }
            }
        }
        return null;
    }
}
