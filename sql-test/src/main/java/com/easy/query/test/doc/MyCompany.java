package com.easy.query.test.doc;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.proxy.MyCompanyProxy;
import lombok.Data;

/**
 * create time 2024/10/14 11:04
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_company")
@EntityProxy
public class MyCompany implements ProxyEntityAvailable<MyCompany , MyCompanyProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
}
