package com.easy.query.test.nop;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.nop.proxy.MyChildProxy;
import lombok.Data;

/**
 * create time 2024/5/10 12:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("MyChild")
@EntityProxy
public class MyChild implements ProxyEntityAvailable<MyChild , MyChildProxy> {
    @Column(primaryKey = true)
    private String id;
    private String childrenParentId;
    private String name;
}
