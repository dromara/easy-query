package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic_auto1")
public class TopicAutoNative {

    @Column(primaryKey = true)
    private Integer id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}
