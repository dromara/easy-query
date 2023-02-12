package org.easy.query.core.abstraction.metadata;

/**
 * 类元信息管理
 * @FileName: ClassMetadataManager.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:07
 * @Created by xuejiaming
 */
public interface EntityMetadataManager {

    /**
     * 根据字节获取对象元信息
     *
     * @param entityClass
     * @return 如果对象不包含Table注解那么返回为为null
     */
    EntityMetadata getEntityMetadata(Class entityClass);

}
