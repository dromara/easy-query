package com.easy.query.test.entity;

import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2025/1/18 20:47
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
@Table(value = "t_topic2", renameFrom = "t_topic")
public class NewTopic {
    private String abc;
}
