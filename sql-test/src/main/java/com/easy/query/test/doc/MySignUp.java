package com.easy.query.test.doc;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.proxy.MySignUpProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2024/10/14 11:08
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_sign_up")
@EntityProxy
@FieldNameConstants
public class MySignUp implements ProxyEntityAvailable<MySignUp, MySignUpProxy> {
    @Column(primaryKey = true)
    private String id;
    private String comId;
    private String userId;
    private LocalDateTime time;
    private String content;
    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = {"comId", "userId"}, targetProperty = {"comId", "userId"})
    private MyComUser comUser;


    @Navigate(value = RelationTypeEnum.ManyToOne,directMapping = {"comUser","myCompany"})
    private MyCompany comUserCompany;
}
