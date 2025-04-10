package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MyOrderDetailProxy;
import lombok.Data;

/**
 * create time 2025/4/10 17:56
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_order_detail")
@EntityProxy
@Data
public class MyOrderDetail implements ProxyEntityAvailable<MyOrderDetail , MyOrderDetailProxy> {
    private String id;
    private String orderId;
    private String productId;
    private Long orderNum;


    @Navigate(value = RelationTypeEnum.ManyToOne,selfProperty = "orderId",targetProperty = "id")
    private MyOrder order;
}
