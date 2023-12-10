package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.SchoolTeacherProxy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * create time 2023/7/16 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("school_teacher")
@Data
@ToString
@EntityFileProxy
public class SchoolTeacher implements ProxyEntityAvailable<SchoolTeacher , SchoolTeacherProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = SchoolClassTeacher.class
            , selfProperty = "id"
            , selfMappingProperty = "teacherId"
            , targetProperty = "id"
            , targetMappingProperty = "classId")
    private List<SchoolClass> schoolClasses;

    @Override
    public Class<SchoolTeacherProxy> proxyTableClass() {
        return SchoolTeacherProxy.class;
    }
}
