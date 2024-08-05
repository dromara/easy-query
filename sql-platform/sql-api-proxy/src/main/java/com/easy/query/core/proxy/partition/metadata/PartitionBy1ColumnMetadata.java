package com.easy.query.core.proxy.partition.metadata;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.ColumnOption;
import com.easy.query.core.proxy.partition.Partition1;

/**
 * create time 2024/8/5 10:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartitionBy1ColumnMetadata extends ColumnMetadata {
    public PartitionBy1ColumnMetadata(ColumnOption columnOption) {
        super(columnOption);
    }
    @Override
    public String getPropertyName() {
        return Partition1.PARTITION_COLUMN1;
    }
}
