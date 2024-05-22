package com.easy.query.test.dto;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dto.proxy.TopicSubQueryBlogProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/5/20 10:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityFileProxy
public class TopicSubQueryBlog implements ProxyEntityAvailable<TopicSubQueryBlog , TopicSubQueryBlogProxy> {//自动生成
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
    private Long blogCount;

}
