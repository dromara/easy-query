package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.JsonConverter;
import com.easy.query.test.entity.proxy.TopicTypeJsonProxy;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * create time 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic_type")
@ToString
@EntityProxy
public class TopicTypeJson implements ProxyEntityAvailable<TopicTypeJson , TopicTypeJsonProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    @Column(conversion = JsonConverter.class)
    private TopicTypeJsonValue title;
    private Integer topicType;
    private LocalDateTime createTime;
}
