package com.easy.query.console.demo;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/7/4 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_topic")
@EntityProxy
public class Topic {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private Integer no;
    @UpdateIgnore
    private LocalDateTime createTime;
}
