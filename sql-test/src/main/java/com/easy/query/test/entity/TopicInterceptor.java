package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
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
public class TopicInterceptor {

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
