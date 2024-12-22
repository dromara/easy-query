package com.easy.query.test.vo;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2024/12/13 10:03
 * 文件说明
 *
 * @author xuejiaming
 */
@EntityProxy
@Data
public class MyVO {
    private String name;
    private Long count;
    private String max;
}
