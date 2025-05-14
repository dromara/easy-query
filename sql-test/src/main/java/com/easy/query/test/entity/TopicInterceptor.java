package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicInterceptorProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/4/3 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_topic_interceptor")
@EntityProxy
public class TopicInterceptor implements ProxyEntityAvailable<TopicInterceptor , TopicInterceptorProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @UpdateIgnore
    private LocalDateTime createTime;
    @UpdateIgnore
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;
    @UpdateIgnore
    private String tenantId;
}
