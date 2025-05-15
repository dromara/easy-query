package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.base.BaseGenericEntity;
import com.easy.query.test.entity.proxy.TopicGenericKeyProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * create time 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic")
@EntityProxy
public class TopicGenericKey extends BaseGenericEntity<String> implements ProxyEntityAvailable<TopicGenericKey , TopicGenericKeyProxy> {

    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}
