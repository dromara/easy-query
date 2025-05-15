package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicLargeProxy;
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
@EntityProxy //添加这个属性那么Topic对象会代理生成TopicProxy (需要idea build一下当前项目)
public class TopicLarge implements ProxyEntityAvailable<TopicLarge , TopicLargeProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    @UpdateIgnore(updateSetInTrackDiff = true)
    private String title;
    @UpdateIgnore
    private String title1;
    private LocalDateTime createTime;
}
