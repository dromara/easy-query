package com.easy.query.test.selectato;

import com.easy.query.core.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2024/9/4 10:11
 * @see com.easy.query.test.entity.Topic
 *
 * @author xuejiaming
 */
@Data
public class TopicAutoFalse {
    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;

    @Column(autoSelect = false)
    private String alias;
//    @Column(value = "C_USER_NAME")
}
