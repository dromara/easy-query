package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicTypeTest2Proxy;
import com.easy.query.test.enums.TopicTypeEnum;
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
public class TopicTypeTest2 implements ProxyEntityAvailable<TopicTypeTest2 , TopicTypeTest2Proxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
//    @Column(value = "topic_type",conversion = EnumValueConverter.class)
    private TopicTypeEnum topicType;
    private LocalDateTime createTime;
}
