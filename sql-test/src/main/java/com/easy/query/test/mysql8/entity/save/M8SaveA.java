package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveAProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveBProxy;
import lombok.Data;

/**
 * create time 2025/9/15 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_a")
public class M8SaveA implements ProxyEntityAvailable<M8SaveA, M8SaveAProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;

    /**
     * b
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {M8SaveAProxy.Fields.id}, targetProperty = {M8SaveBProxy.Fields.pid},cascade = CascadeTypeEnum.DELETE)
    private M8SaveB m8SaveB;
}
