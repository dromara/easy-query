package com.easy.query.test.nop;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.nop.proxy.MyObjectProxy;
import lombok.Data;

/**
 * create time 2024/5/10 12:41
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("MyObject")
@EntityProxy
public class MyObject implements ProxyEntityAvailable<MyObject , MyObjectProxy> {
    @Column(primaryKey = true)
    private String id;
    private String f1;
    private String f2;
    private String parentId;
    @Navigate(value = RelationTypeEnum.OneToOne,selfProperty = "parentId")
    private MyObjectParent parent;

    @Override
    public Class<MyObjectProxy> proxyTableClass() {
        return MyObjectProxy.class;
    }
}
