package com.easy.query.test.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/5/20 10:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class TopicSubQueryBlog {
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
    private Long blogCount;
}
