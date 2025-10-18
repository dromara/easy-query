package com.easy.query.core.basic.extension.generated;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2025/10/18 21:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class UnSupportSaveEntitySetPrimaryKeyGenerator implements SaveEntitySetPrimaryKeyGenerator{
    @Override
    public void setPrimaryKey(Object entity, ColumnMetadata columnMetadata) {
        throw new EasyQueryInvalidOperationException("un support save entity set primary key,plz set replace [SaveEntitySetPrimaryKeyGenerator] Component or add [PrimaryKeyGenerator] Component");
    }
}
