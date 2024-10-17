package com.easy.query.test.doc;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.proxy.MyUserProxy;
import lombok.Data;

/**
 * create time 2024/10/14 11:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_user")
@EntityProxy
public class MyUser implements ProxyEntityAvailable<MyUser , MyUserProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private Integer age;
}
