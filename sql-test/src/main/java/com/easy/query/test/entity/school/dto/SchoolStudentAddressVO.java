package com.easy.query.test.entity.school.dto;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/21 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
public class SchoolStudentAddressVO {
    private String id;
    private String studentId;
    private String address;
    @Navigate(value = RelationTypeEnum.ManyToOne)
    private SchoolStudentVO schoolStudent;
}
