package com.easy.query.test.common;

import com.easy.query.core.basic.extension.generated.SaveEntitySetPrimaryKeyGenerator;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.UUID;

/**
 * create time 2025/10/18 22:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySaveEntitySetPrimaryKeyGenerator implements SaveEntitySetPrimaryKeyGenerator {
    @Override
    public void setPrimaryKey(Object entity, ColumnMetadata columnMetadata) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        columnMetadata.getSetterCaller().call(entity, id);
    }
}
