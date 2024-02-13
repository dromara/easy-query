package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.MySchoolStudent1Proxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/16 11:28
 * 文件说明
 * com.easy.query.test.entity.school.MySchoolStudent1
 * @author xuejiaming
 */
@Table("my_school_student")
@Data
@ToString
@EntityProxy
public class MySchoolStudent1 implements ProxyEntityAvailable<MySchoolStudent1, MySchoolStudent1Proxy> {
    @Column(primaryKey = true)
    private String id;
    private String classId;
    private String name;
    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = "classId", targetProperty = "id")
    private MySchoolClass1 schoolClass;

    @Override
    public Class<MySchoolStudent1Proxy> proxyTableClass() {
        return MySchoolStudent1Proxy.class;
    }
}
