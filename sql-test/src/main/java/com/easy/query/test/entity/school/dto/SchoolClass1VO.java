package com.easy.query.test.entity.school.dto;

import com.easy.query.core.annotation.EntityFileProxy;
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
@EntityFileProxy
public class SchoolClass1VO {
    private String id;
    private String name;
    private String name1;
    @Navigate(RelationTypeEnum.OneToMany)
    private List<SchoolStudentVO> schoolStudents;
}
