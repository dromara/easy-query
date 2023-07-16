package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2023/7/16 11:21
 * 学校班级
 *
 * @author xuejiaming
 */
@Table("school_class")
@Data
public class SchoolClass {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @Navigate(value = RelationTypeEnum.OneToMany, targetProperty = "classId")
    private List<SchoolStudent> schoolStudents;

    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = SchoolClassTeacher.class
            , selfProperty = "id"
            , selfMappingProperty = "classId"
            , targetProperty = "id"
            , targetMappingProperty = "teacherId")
    private List<SchoolTeacher> schoolTeachers;
}
