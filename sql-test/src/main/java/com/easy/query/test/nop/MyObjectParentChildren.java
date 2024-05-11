package com.easy.query.test.nop;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.nop.proxy.MyObjectParentChildrenProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/5/10 12:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("MyObjectParentChildren")
@EntityProxy
public class MyObjectParentChildren implements ProxyEntityAvailable<MyObjectParentChildren , MyObjectParentChildrenProxy> {
    @Column(primaryKey = true)
    private String id;
    private String parentId;
    private String name;
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty = "childrenParentId")
    private List<MyChild> myChildren;

    @Override
    public Class<MyObjectParentChildrenProxy> proxyTableClass() {
        return MyObjectParentChildrenProxy.class;
    }
}
