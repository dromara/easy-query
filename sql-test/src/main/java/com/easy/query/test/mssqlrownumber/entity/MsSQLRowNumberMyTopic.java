package com.easy.query.test.mssqlrownumber.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mssqlrownumber.entity.proxy.MsSQLRowNumberMyTopicProxy;
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
public class MsSQLRowNumberMyTopic implements ProxyEntityAvailable<MsSQLRowNumberMyTopic , MsSQLRowNumberMyTopicProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}
