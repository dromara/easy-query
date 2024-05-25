package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2024/5/25 10:19
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class BlogEntityTitleOnly {
    private String title;
}
