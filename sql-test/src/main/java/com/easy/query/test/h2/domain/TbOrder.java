package com.easy.query.test.h2.domain;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.h2.domain.proxy.TbOrderProxy;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2024/8/26 14:58
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_tb_order")
@EntityProxy
@Data
public class TbOrder implements ProxyEntityAvailable<TbOrder , TbOrderProxy> {
    private String id;
    private String uid;
    private String uname;
    private BigDecimal price;
    @Navigate(value = RelationTypeEnum.ManyToMany,selfProperty = "uid",targetProperty = "uid")
    private List<TbAccount> accounts;
}
