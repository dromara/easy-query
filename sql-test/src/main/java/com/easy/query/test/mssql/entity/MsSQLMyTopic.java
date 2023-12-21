package com.easy.query.test.mssql.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopicProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/7/27 17:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("MyTopic")
@EntityFileProxy
public class MsSQLMyTopic implements ProxyEntityAvailable<MsSQLMyTopic , MsSQLMyTopicProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;

    @Override
    public Class<MsSQLMyTopicProxy> proxyTableClass() {
        return MsSQLMyTopicProxy.class;
    }
}
