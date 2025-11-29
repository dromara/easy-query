package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8AutoAProxy;
import com.easy.query.test.mysql8.entity.proxy.M8AutoBProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/11/29 11:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("m8_auto_a")
@EntityProxy
@Data
public class M8AutoA implements ProxyEntityAvailable<M8AutoA, M8AutoAProxy> {
    @Column(primaryKey = true, generatedKey = true)
    private Long id;
    private String column1;
    private String column2;
    private String column3;

    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToMany,
            selfProperty = {M8AutoAProxy.Fields.id},
            targetProperty = {M8AutoBProxy.Fields.id})
    private List<M8AutoB> m8AutoBList;
}
