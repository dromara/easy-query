package com.easy.query.test;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2024/9/3 17:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class MyVO {
    private String title;
    private String content;
    private Long c;
}
