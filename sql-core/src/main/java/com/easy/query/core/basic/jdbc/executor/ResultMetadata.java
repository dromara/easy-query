package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegment;

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

    void initResultColumnMetadata(ProjectSQLBuilderSegment projects);
    void initResultColumnMetadata(ResultColumnMetadata[] resultColumnMetadata);
    void initResultColumnCount(int resultColumnCount);
    int getResultColumnCount();

    ResultColumnMetadata getResultColumnMetadataByIndex(int columnIndexFromZero);
}
