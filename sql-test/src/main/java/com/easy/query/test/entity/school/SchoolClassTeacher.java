package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/16 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("school_class_teacher")
@Data
@ToString
@EntityProxy
public class SchoolClassTeacher {
    @Column(primaryKey = true)
    private String classId;
    @Column(primaryKey = true)
    private String teacherId;
}
