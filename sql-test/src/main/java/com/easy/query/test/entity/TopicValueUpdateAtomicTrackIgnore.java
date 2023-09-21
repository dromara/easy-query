package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.basic.extension.track.update.IntegerNotNullValueUpdateAtomicTrack;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author xuejiaming
 * @FileName: Topic.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:26
 */
@Data
@Table("t_topic_value_atomic")
@ToString
public class TopicValueUpdateAtomicTrackIgnore {

    @Column(primaryKey = true)
    private String id;
    @Column(valueUpdateAtomicTrack = IntegerNotNullValueUpdateAtomicTrack.class)
    @UpdateIgnore(updateSetInTrackDiff = true)
    private Integer stars;
    private String title;
    private Integer topicType;
    private LocalDateTime createTime;
}
