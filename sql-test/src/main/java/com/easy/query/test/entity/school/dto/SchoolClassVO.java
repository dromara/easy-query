package com.easy.query.test.entity.school.dto;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.dto.proxy.SchoolClassVOProxy;
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
public class SchoolClassVO implements ProxyEntityAvailable<SchoolClassVO , SchoolClassVOProxy> {
    private String id;
    private String name;
    @Navigate(RelationTypeEnum.OneToMany)
    private List<SchoolStudentVO> schoolStudents;
    @Navigate(RelationTypeEnum.ManyToMany)
    private List<SchoolTeacherVO> schoolTeachers;

    @Override
    public Class<SchoolClassVOProxy> proxyTableClass() {
        return SchoolClassVOProxy.class;
    }
}
