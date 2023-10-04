package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/10/4 21:43
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("test_bean")
@Data
public class TestBean {
    @Column(primaryKey = true)
    private String id;
    private String isFull;
    private LocalDateTime isTimeLine;
    private Boolean isTop;
}
