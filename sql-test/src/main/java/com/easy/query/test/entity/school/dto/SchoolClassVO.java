package com.easy.query.test.entity.school.dto;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2023/7/20 11:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class SchoolClassVO {
    private String id;
    private String name;
    @Navigate(RelationTypeEnum.OneToMany)
    private List<SchoolStudentVO> schoolStudents;
    @Navigate(RelationTypeEnum.ManyToMany)
    private List<SchoolTeacherVO> schoolTeachers;
}
