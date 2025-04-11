package com.easy.query.test.entity.m2m;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.m2m.proxy.UserAccountProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/4/10 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_user_account")
@Data
@EntityProxy
@EasyAlias("uc")
public class UserAccount implements ProxyEntityAvailable<UserAccount , UserAccountProxy> {
    @Column(primaryKey = true)
    private String id;
    private String uid;
    private String name;
    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = "uid",
            targetProperty = "uid")
    private List<UserBook> books;

}
