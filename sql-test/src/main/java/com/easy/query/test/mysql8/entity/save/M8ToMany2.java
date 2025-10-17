package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8ToMany2Proxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8ToMany3Proxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/10/17 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_many2")
@EntityProxy
public class M8ToMany2 implements ProxyEntityAvailable<M8ToMany2, M8ToMany2Proxy> {
    @Column(primaryKey = true)
    private String d;
    private String a;
    private String e;
    private String f;
    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8ToMany2Proxy.Fields.d}, targetProperty = {M8ToMany3Proxy.Fields.d},cascade = CascadeTypeEnum.DELETE)
    private List<M8ToMany3> m8ToMany3List;
}
