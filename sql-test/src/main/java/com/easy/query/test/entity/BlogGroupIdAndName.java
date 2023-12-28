package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityFileProxy;
import lombok.Data;

/**
 * create time 2023/12/27 22:34
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityFileProxy
public class BlogGroupIdAndName {
    private String id;
    private Long idCount;
}
