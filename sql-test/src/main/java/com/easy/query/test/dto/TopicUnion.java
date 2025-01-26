package com.easy.query.test.dto;

import com.easy.query.core.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/5/21 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class TopicUnion {
    private String id;
    private Integer stars;
    @Column("abc")
    private String title;
}
