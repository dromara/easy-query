package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8OrderItemProxy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2025/4/12 10:14
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("t_order_item")
public class M8OrderItem implements ProxyEntityAvailable<M8OrderItem , M8OrderItemProxy> {
    private String id;
    private String orderId;
    private BigDecimal price;
    private LocalDateTime createTime;
}
