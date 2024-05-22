package com.easy.query.test.dameng.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dameng.entity.proxy.DamengMyTopicProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/7/27 17:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("MY_TOPIC")
@EntityFileProxy
public class DamengMyTopic implements ProxyEntityAvailable<DamengMyTopic , DamengMyTopicProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;

}
