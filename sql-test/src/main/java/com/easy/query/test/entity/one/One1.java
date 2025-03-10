package com.easy.query.test.entity.one;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.one.proxy.One1Proxy;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * create time 2025/3/10 15:07
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
@Setter
@Table("one1")
@EntityProxy
@EasyAlias("one1")
@ToString
public class One1 implements ProxyEntityAvailable<One1 , One1Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String oid;
    private String oname;
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = "oid",targetProperty = "oid")
    private One2 one2;
}
