package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.test.entity.proxy.MyTopicProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/1/2 10:39
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
@Table("t_topic")
@EntityProxy
public class MyTopicTest {
    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}
