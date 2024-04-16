package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2024/4/8 13:22
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
public class SchoolClassAggregatePropVO {
    @Column(primaryKey = true)//主键
    private String id;
    private String name;

    private Long studentSize;

}
