package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/6/30 21:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ResultMetadata<TR> {
    EntityMetadata getEntityMetadata();
    Class<TR> getResultClass();
    DataReader getDataReader();

    EntityMetadataTypeEnum getEntityMetadataType();

    TR newBean();

    ResultColumnMetadata getResultColumnOrNullByColumnName(int index,String columnName);

    ResultColumnMetadata getResultColumnOrNullByPropertyName(int index,String propertyName);
}
