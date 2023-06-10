package com.easy.query.test.entity;

import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2023/6/10 07:56
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_nokey")
public class NoKeyEntity {
    private String id;
    private String name;
}
