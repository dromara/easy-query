package com.easy.query.core.basic.jdbc.executor.impl.def;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/30 21:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityResultMetadata<TR> implements ResultMetadata<TR> {
    protected final EntityMetadata entityMetadata;

    protected ResultColumnMetadata[] resultColumnMetadata;
    protected int resultColumnCount;

    public EntityResultMetadata(EntityMetadata entityMetadata) {
        this(entityMetadata, null);
    }

    public EntityResultMetadata(EntityMetadata entityMetadata, ResultColumnMetadata[] resultColumnMetadata) {
        this.entityMetadata = entityMetadata;
        this.resultColumnMetadata = resultColumnMetadata;
        if(this.resultColumnMetadata!=null){
            this.resultColumnCount=this.resultColumnMetadata.length;
        }
    }

    @Override
    public Class<TR> getResultClass() {
        return EasyObjectUtil.typeCastNullable(entityMetadata.getEntityClass());
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
    public ResultColumnMetadata getResultColumnOrNullByColumnName(int index,String columnName) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnMetadataOrNull(columnName);
        if (columnMetadata != null) {
            return new EntityResultColumnMetadata(index,columnMetadata);
        }
        return null;
    }

    @Override
    public ResultColumnMetadata getResultColumnOrNullByPropertyName(int index,String propertyName) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(propertyName);
        if (columnMetadata != null) {
            return new EntityResultColumnMetadata(index,columnMetadata);
        }
        return null;
    }

    @Override
    public void initResultColumnMetadata(ResultColumnMetadata[] resultColumnMetadata) {
        this.resultColumnMetadata = resultColumnMetadata;
    }

    @Override
    public void initResultColumnCount(int resultColumnCount) {
        this.resultColumnCount=resultColumnCount;
    }

    @Override
    public int getResultColumnCount() {
        return resultColumnCount;
    }

    @Override
    public ResultColumnMetadata getResultColumnMetadataByIndex(int columnIndexFromZero) {
        return resultColumnMetadata[columnIndexFromZero];
    }
}
