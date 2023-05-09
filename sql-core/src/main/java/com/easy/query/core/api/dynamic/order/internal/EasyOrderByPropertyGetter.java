package com.easy.query.core.api.dynamic.order.internal;

import java.util.Map;

/**
 * @FileName: EasyOrderByConfigure.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:21
 * @author xuejiaming
 */
public interface EasyOrderByPropertyGetter {
    Map<String, DynamicOrderByPropertyNode> getOrderProperties();
}
