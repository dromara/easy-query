package com.easy.query.core.api.dynamic.sort.internal;

import java.util.Map;

/**
 * @FileName: EasyOrderByConfigure.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:21
 * @author xuejiaming
 */
public interface ObjectSortPropertyGetter {
    Map<String, ObjectSortPropertyNode> getProperties();
}
