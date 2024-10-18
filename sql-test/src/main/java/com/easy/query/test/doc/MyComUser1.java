package com.easy.query.test.doc;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.OrderByPropertyModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.proxy.MyComUser1Proxy;
import com.easy.query.test.doc.proxy.MyComUserProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/10/14 11:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_com_user")
@EntityProxy
public class MyComUser1 implements ProxyEntityAvailable<MyComUser1, MyComUser1Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String comId;
    private String userId;
    private String gw;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"comId", "userId"}, targetProperty = {"comId", "userId"}, orderByProps = {
            @OrderByProperty(property = "comId"),
            @OrderByProperty(property = "time", asc = false, mode = OrderByPropertyModeEnum.NULLS_FIRST),
    })
    private List<MySignUp> mySignUps;
}
