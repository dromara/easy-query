package com.easy.query.core.api.query.order.internal;

import java.util.Map;

/**
 * @FileName: EasyOrderByConfigure.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:21
 * @Created by xuejiaming
 */
public interface OrderByPropertyGetter {
    Map<String,OrderByPropertyNode> getOrderProperties();
}
