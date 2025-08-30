package com.easy.query.test.mysql8.dto;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.test.mysql8.entity.proxy.CommentProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/8/27 13:19
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class MyComment3 {
    private String id;
    private String parentId;
    private String content;
    private String userId;
    private String postId;
    private LocalDateTime createAt;
    private Long deep;


    /**
     * 子评论
     **/
    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<MyComment3> children;
}
