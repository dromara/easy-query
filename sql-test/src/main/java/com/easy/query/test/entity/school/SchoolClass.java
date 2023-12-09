package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.SchoolClassProxy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * create time 2023/7/16 11:21
 * 学校班级
 *
 * @author xuejiaming
 */
@Table("school_class")
@Data
@ToString
@EntityProxy
public class SchoolClass implements ProxyEntityAvailable<SchoolClass , SchoolClassProxy> {
    @Column(primaryKey = true)//主键
    private String id;
    private String name;
    //一对多 一个班级多个学生
    @Navigate(value = RelationTypeEnum.OneToMany, targetProperty = "classId")
    //完整配置,property忽略表示对应的主键
//    @Navigate(value = RelationTypeEnum.OneToMany,selfProperty = "id",targetProperty = "classId")
    private List<SchoolStudent> schoolStudents;

    //中间表多对多配置,其中mappingClass表示中间表,selfMappingProperty表示中间表的哪个字段和当前表对应,
    //targetMappingProperty表示中间表的哪个字段和目标表的属性对应
    @Navigate(value = RelationTypeEnum.ManyToMany
            , mappingClass = SchoolClassTeacher.class
            , selfMappingProperty = "classId"
            , targetMappingProperty = "teacherId")
    //完整配置其中自己的属性和目标属性忽略表示主键
//    @Navigate(value = RelationTypeEnum.ManyToMany
//            , selfProperty = "id"
//            , targetProperty = "id"
//            , mappingClass = SchoolClassTeacher.class
//            , selfMappingProperty = "classId"
//            , targetMappingProperty = "teacherId")

    private List<SchoolTeacher> schoolTeachers;

    @Override
    public Class<SchoolClassProxy> proxyTableClass() {
        return SchoolClassProxy.class;
    }
}
