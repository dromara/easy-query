package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.UserBookEncryptProxy;
import lombok.Data;

/**
 * create time 2023/8/12 21:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("user_book_encrypt")
@EntityProxy
public class UserBookEncrypt implements ProxyEntityAvailable<UserBookEncrypt , UserBookEncryptProxy> {
    @Column(primaryKey = true)
    private String id;
    private String userId;
    private String name;
}