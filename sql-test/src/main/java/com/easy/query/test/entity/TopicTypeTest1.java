package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.EnumConverter;
import com.easy.query.test.entity.proxy.TopicTypeTest1Proxy;
import com.easy.query.test.enums.TopicTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic_type")
@EntityFileProxy
@ToString
public class TopicTypeTest1 implements ProxyEntityAvailable<TopicTypeTest1 , TopicTypeTest1Proxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @Column(value = "topic_type",conversion = EnumConverter.class)
    private TopicTypeEnum topicType;
    private LocalDateTime createTime;

    @Override
    public Class<TopicTypeTest1Proxy> proxyTableClass() {
        return TopicTypeTest1Proxy.class;
    }
}
