package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.SchoolStudentAddressProxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/16 11:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("school_student_address")
@Data
@ToString
@EntityFileProxy
public class SchoolStudentAddress implements ProxyEntityAvailable<SchoolStudentAddress , SchoolStudentAddressProxy> {
    private String id;
    private String studentId;
    private String address;
    private Integer age;
    @Navigate(value = RelationTypeEnum.ManyToOne,selfProperty = "studentId",targetProperty = "id")
    private SchoolStudent schoolStudent;
}
