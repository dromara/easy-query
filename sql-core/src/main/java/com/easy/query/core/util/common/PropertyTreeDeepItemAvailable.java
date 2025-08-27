package com.easy.query.core.util.common;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2025/8/27 12:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyTreeDeepItemAvailable implements TreeDeepItemAvailable{
    private final ColumnMetadata columnMetadata;

    public PropertyTreeDeepItemAvailable(ColumnMetadata columnMetadata){
        this.columnMetadata = columnMetadata;
    }

    @Override
    public long getDeep(Object node, int index) {
        return EasyObjectUtil.typeCastNotNull(columnMetadata.getGetterCaller().apply(node));
    }
}