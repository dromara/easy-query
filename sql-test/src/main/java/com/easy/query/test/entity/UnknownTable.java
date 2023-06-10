package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/6/10 07:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_unknown")
public class UnknownTable {
    @Column(primaryKey = true)
    private String id;
    private Integer age;
    private Integer age1;
    private Long money;
    private Long money1;
    private LocalDateTime birthday;
    private LocalDateTime birthday1;
}
