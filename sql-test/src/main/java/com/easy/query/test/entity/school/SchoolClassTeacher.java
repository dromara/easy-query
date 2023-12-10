package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.SchoolClassTeacherProxy;
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
@EntityFileProxy
public class SchoolClassTeacher implements ProxyEntityAvailable<SchoolClassTeacher , SchoolClassTeacherProxy> {
    @Column(primaryKey = true)
    private String classId;
    @Column(primaryKey = true)
    private String teacherId;

    @Override
    public Class<SchoolClassTeacherProxy> proxyTableClass() {
        return SchoolClassTeacherProxy.class;
    }
}
