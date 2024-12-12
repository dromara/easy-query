package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.SchoolStudent2Proxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2024/12/12 16:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("school_student")
@Data
@ToString
@EntityFileProxy
public class SchoolStudent2 implements ProxyEntityAvailable<SchoolStudent2 , SchoolStudent2Proxy> {

    @Column(primaryKey = true)
    private String id;
    private String classId;
    private String name;
    //    private Integer age;
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = "classId", targetProperty = "id")
    private SchoolClass schoolClass;
}
