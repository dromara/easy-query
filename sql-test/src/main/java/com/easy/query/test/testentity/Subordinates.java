package com.easy.query.test.testentity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.testentity.proxy.SubordinatesProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/12/3 09:17
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("subordinates")
@EntityProxy
public class Subordinates implements ProxyEntityAvailable<Subordinates , SubordinatesProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String managerId;

    @Navigate(value=RelationTypeEnum.OneToMany, targetProperty = "managerId")
    private List<Subordinates> subordinatesList;
}
