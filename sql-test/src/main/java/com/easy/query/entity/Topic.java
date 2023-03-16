package com.easy.query.entity;

import com.easy.query.core.annotation.PrimaryKey;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:26
 * @Created by xuejiaming
 */
@Data
@Table("t_topic")
public class Topic {

    @PrimaryKey
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}
