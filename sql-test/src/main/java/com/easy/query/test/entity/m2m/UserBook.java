package com.easy.query.test.entity.m2m;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.m2m.proxy.UserBookProxy;
import lombok.Data;

import java.math.BigDecimal;

/**
 * create time 2025/4/10 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user_book")
@Data
@EntityProxy
public class UserBook implements ProxyEntityAvailable<UserBook , UserBookProxy> {
    @Column(primaryKey = true)
    private String id;
    private String uid;
    private String name;
    private BigDecimal price;
}