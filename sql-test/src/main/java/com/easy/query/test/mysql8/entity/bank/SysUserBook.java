package com.easy.query.test.mysql8.entity.bank;

import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.bank.proxy.SysUserBookProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * create time 2025/4/17 18:41
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_sys_user_book")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("user_book")
public class SysUserBook implements ProxyEntityAvailable<SysUserBook , SysUserBookProxy> {
    private String id;
    private String name;
    private String uid;
    private BigDecimal price;
}
