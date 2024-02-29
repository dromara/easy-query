package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.SchoolStudentProxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/16 11:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("school_student")
@Data
@ToString
@EntityFileProxy
public class SchoolStudent implements ProxyEntityAvailable<SchoolStudent, SchoolStudentProxy> {
    @Column(primaryKey = true)
    private String id;
    private String classId;
    private String name;
//    private Integer age;
    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = "classId", targetProperty = "id")
    private SchoolClass schoolClass;
    @Navigate(value = RelationTypeEnum.OneToOne, targetProperty = "studentId")
    private SchoolStudentAddress schoolStudentAddress;

    @Override
    public Class<SchoolStudentProxy> proxyTableClass() {
        return SchoolStudentProxy.class;
    }
}
