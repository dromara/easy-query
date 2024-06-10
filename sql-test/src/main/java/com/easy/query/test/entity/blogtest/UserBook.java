package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.blogtest.proxy.UserBookProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2024/6/10 22:21
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user_book")
@Data
@EntityProxy
@EasyAlias("u_book")
public class UserBook implements ProxyEntityAvailable<UserBook , UserBookProxy> {
    @Column(primaryKey = true)
    private String id;

    private String userId;
    private String name;
    private LocalDateTime createTime;
    private String author;
}
