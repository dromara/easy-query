package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.JsonConverter;
import com.easy.query.test.entity.proxy.TopicTypeArrayJsonJacksonProxy;
import com.easy.query.test.entity.proxy.TopicTypeArrayJsonProxy;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * create time 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic_type_array")
@ToString
@EntityFileProxy
public class TopicTypeArrayJsonJackson implements ProxyEntityAvailable<TopicTypeArrayJsonJackson, TopicTypeArrayJsonJacksonProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    @Column(conversion = JsonConverter.class)
    private TopicTypeJsonValue title;
    @Column(conversion = JsonConverter.class)
    private List<TopicTypeJsonValue> title2;
//    private Integer[] title3;
    private Integer topicType;
    private LocalDateTime createTime;
}
