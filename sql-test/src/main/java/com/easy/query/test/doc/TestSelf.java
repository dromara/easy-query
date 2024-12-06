package com.easy.query.test.doc;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.proxy.TestSelfProxy;
import lombok.Data;

/**
 * create time 2024/12/6 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("test_self")
@EntityProxy
public class TestSelf implements ProxyEntityAvailable<TestSelf , TestSelfProxy> {
    private String id;
    private String pid;
    private String uid;
    private String name;
    @Navigate(value = RelationTypeEnum.ManyToOne,selfProperty = "pid",targetProperty = "id")
    private TestSelf parent;
    @Navigate(value = RelationTypeEnum.ManyToOne,selfProperty = "uid")
    private MyUser myUser;
}
