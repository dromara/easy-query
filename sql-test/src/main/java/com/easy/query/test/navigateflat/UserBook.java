package com.easy.query.test.navigateflat;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.navigateflat.proxy.UserBookProxy;
import lombok.Data;

/**
 * create time 2025/1/2 14:00
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("user_book")
@Data
@EntityProxy
public class UserBook implements ProxyEntityAvailable<UserBook , UserBookProxy> {
    private String id;
    private String uid;
    private String name;

    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = "uid", targetProperty = "id")
    private MyUser myUser;
}
