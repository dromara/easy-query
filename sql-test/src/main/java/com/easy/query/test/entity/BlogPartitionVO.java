package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import lombok.Data;

/**
 * create time 2024/9/29 16:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class BlogPartitionVO {
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    @ColumnIgnore
    private String content;
    /**
     * 博客链接
     */
    private String url;

    private Long rank;
}
