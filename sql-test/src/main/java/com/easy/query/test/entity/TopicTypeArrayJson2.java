package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicTypeArrayJson2Proxy;
import lombok.Data;
import lombok.ToString;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * create time 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic_type_array")
@ToString
@EntityProxy
public class TopicTypeArrayJson2 implements ProxyEntityAvailable<TopicTypeArrayJson2, TopicTypeArrayJson2Proxy> {


    @Column(primaryKey = true)
    private String id;
    private Integer[] title3;
}
