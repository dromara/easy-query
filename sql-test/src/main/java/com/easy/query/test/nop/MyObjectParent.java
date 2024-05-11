package com.easy.query.test.nop;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.nop.proxy.MyObjectParentProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/5/10 12:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("MyObjectParent")
@EntityProxy
public class MyObjectParent implements ProxyEntityAvailable<MyObjectParent , MyObjectParentProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Navigate(value=RelationTypeEnum.OneToMany, targetProperty = "parentId")
    private List<MyObjectParentChildren> children;

    @Override
    public Class<MyObjectParentProxy> proxyTableClass() {
        return MyObjectParentProxy.class;
    }
}
