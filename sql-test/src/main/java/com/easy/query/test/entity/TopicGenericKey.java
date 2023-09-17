package com.easy.query.test.entity;

import com.easy.query.core.annotation.Table;
import com.easy.query.test.entity.base.BaseGenericEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table("t_topic")
public class TopicGenericKey extends BaseGenericEntity<String> {

    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}
