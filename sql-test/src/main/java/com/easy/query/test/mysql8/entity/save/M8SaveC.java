package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveBProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveCProxy;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveDProxy;
import lombok.Data;

/**
 * create time 2025/9/15 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_save_c")
public class M8SaveC implements ProxyEntityAvailable<M8SaveC, M8SaveCProxy> {
    @Column(primaryKey = true)
    private String id;
    private String pid;
    private String name;
    /**
     * d
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {M8SaveCProxy.Fields.id}, targetProperty = {M8SaveDProxy.Fields.pid},cascade = CascadeTypeEnum.DELETE)
    private M8SaveD m8SaveD;
}
