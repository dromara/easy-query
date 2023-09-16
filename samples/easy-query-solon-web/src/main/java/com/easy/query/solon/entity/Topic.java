package com.easy.query.solon.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/7/25 14:02
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("t_topic")
public class Topic {
    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private String name;
    private LocalDateTime createTime;
}