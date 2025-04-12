package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8OrderProxy;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/4/12 10:14
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("t_order")
public class M8Order implements ProxyEntityAvailable<M8Order, M8OrderProxy> {
    private String id;
    private String no;
    private BigDecimal price;
    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"id"}, targetProperty = {"orderId"}, required = true)
    private List<M8OrderItem> orderItems;
}
