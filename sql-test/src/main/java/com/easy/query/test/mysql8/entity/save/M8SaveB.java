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
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveCProxy;
import lombok.Data;

/**
 * create time 2025/9/15 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_b")
public class M8SaveB implements ProxyEntityAvailable<M8SaveB, M8SaveBProxy> {
    @Column(primaryKey = true)
    private String id;
    private String pid;
    private String name;

    /**
     * c
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {M8SaveBProxy.Fields.id}, targetProperty = {M8SaveCProxy.Fields.pid},cascade = CascadeTypeEnum.DELETE)
    private M8SaveC m8SaveC;
}
