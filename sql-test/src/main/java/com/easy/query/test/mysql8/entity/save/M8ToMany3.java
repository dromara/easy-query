package com.easy.query.test.mysql8.entity.save;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.save.proxy.M8ToMany3Proxy;
import lombok.Data;

/**
 * create time 2025/10/17 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_many3")
@EntityProxy
public class M8ToMany3 implements ProxyEntityAvailable<M8ToMany3 , M8ToMany3Proxy> {
    @Column(primaryKey = true)
    private String g;
    private String d;
    private String h;
    private String i;
}
