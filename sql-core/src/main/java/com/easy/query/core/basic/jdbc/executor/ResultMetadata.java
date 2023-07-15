package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.enums.EntityMetadataTypeEnum;

/**
 * create time 2023/6/30 21:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ResultMetadata<TR> {
    Class<TR> getResultClass();
    EntityMetadataTypeEnum getEntityMetadataType();

    TR newBean();

    ResultColumnMetadata getResultColumnOrNullByColumnName(String columnName);
    ResultColumnMetadata getResultColumnOrNullByPropertyName(String propertyName);
}
