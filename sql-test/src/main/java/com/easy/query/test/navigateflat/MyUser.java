package com.easy.query.test.navigateflat;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.navigateflat.proxy.MyUserProxy;
import lombok.Data;

/**
 * create time 2025/1/2 14:00
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_user")
@Data
@EntityProxy
public class MyUser implements ProxyEntityAvailable<MyUser , MyUserProxy> {
    private String id;
    private String name;

    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = "id", targetProperty = "uid")
    private MyUserAddress userAddress;
}
