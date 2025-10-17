package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8ToMany1Proxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8ToMany2Proxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/10/17 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_many1")
@EntityProxy
public class M8ToMany1 implements ProxyEntityAvailable<M8ToMany1, M8ToMany1Proxy> {
    @Column(primaryKey = true)
    private String a;
    private String b;
    private String c;
    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {M8ToMany1Proxy.Fields.a}, targetProperty = {M8ToMany2Proxy.Fields.a},cascade = CascadeTypeEnum.DELETE)
    private List<M8ToMany2> m8ToMany2List;
}
