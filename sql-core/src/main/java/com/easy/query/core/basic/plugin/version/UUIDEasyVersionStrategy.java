package com.easy.query.core.basic.plugin.version;

import com.easy.query.core.metadata.EntityMetadata;

import java.util.UUID;

/**
 * create time 2023/3/27 13:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class UUIDEasyVersionStrategy implements EasyVersionStrategy{

    @Override
    public Object newVersionValue(EntityMetadata entityMetadata, String propertyName, Object version) {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
