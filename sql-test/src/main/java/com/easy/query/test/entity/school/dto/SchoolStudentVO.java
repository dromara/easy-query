package com.easy.query.test.entity.school.dto;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

/**
 * create time 2023/7/20 11:09
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityFileProxy
public class SchoolStudentVO {
    private String id;
    private String classId;
    private String name;
    @Navigate(RelationTypeEnum.ManyToOne)
    private SchoolClassVO schoolClass;
    @Navigate(RelationTypeEnum.OneToOne)
    private SchoolStudentAddressVO schoolStudentAddress;
}
