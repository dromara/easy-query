package com.easy.query.core.proxy.part.metadata;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.ColumnOption;

/**
 * create time 2024/8/5 10:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartColumnMetadata extends ColumnMetadata {
    private final String propertyName;

    public PartColumnMetadata(ColumnOption columnOption, String propertyName) {
        super(columnOption);
        this.propertyName = propertyName;
    }
    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
