package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.CommentProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/8/27 13:00
 * 文件说明
 *
 * @author xuejiaming
 */

@Data
@Table("t_comment")
@EntityProxy
@EasyAlias("t_comment")
@EasyAssertMessage("未找到对应的评论信息")
public class Comment implements ProxyEntityAvailable<Comment, CommentProxy> {
    @Column(primaryKey = true, comment = "评论id")
    private String id;
    @Column(comment = "父id")
    private String parentId;
    @Column(comment = "帖子内容")
    private String content;
    @Column(comment = "用户id", nullable = false)
    private String userId;
    @Column(comment = "帖子id", nullable = false)
    private String postId;
    @Column(comment = "回复时间")
    private LocalDateTime createAt;


    /**
     * 子评论
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {CommentProxy.Fields.id}, targetProperty = {CommentProxy.Fields.parentId})
    private List<Comment> children;
}
