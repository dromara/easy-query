package com.easy.query.test.mssql.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopic1Proxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/7/27 17:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "MyTopic")
@EntityProxy
public class MsSQLMyTopic1 implements ProxyEntityAvailable<MsSQLMyTopic1 , MsSQLMyTopic1Proxy> {

    @Column(primaryKey = true)
    private String Id;
    private Integer Stars;
    private String Title;
    private LocalDateTime CreateTime;
}
