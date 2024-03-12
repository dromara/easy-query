package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2024/3/12 17:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class BlogPartitionEntityVO extends BlogEntity{
    private Integer num;
}
