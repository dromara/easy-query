package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.MySchoolClassProxy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * create time 2023/7/16 11:21
 * 学校班级
 *
 * @author xuejiaming
 */
@Table("my_school_class")
@Data
@ToString
@EntityFileProxy
public class MySchoolClass implements ProxyEntityAvailable<MySchoolClass , MySchoolClassProxy> {
    @Column(primaryKey = true)//主键
    private String id;
    private String name;
    //一对多 一个班级多个学生
    @Navigate(value = RelationTypeEnum.OneToMany, targetProperty = "classId")
    //完整配置,property忽略表示对应的主键
//    @Navigate(value = RelationTypeEnum.OneToMany,selfProperty = "id",targetProperty = "classId")
    private List<MySchoolStudent> schoolStudents;

    @Override
    public Class<MySchoolClassProxy> proxyTableClass() {
        return MySchoolClassProxy.class;
    }
}
