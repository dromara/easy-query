package com.easy.query.test.entity.school.dto;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.dto.proxy.SchoolStudentVOProxy;
import lombok.Data;

/**
 * create time 2023/7/20 11:09
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityFileProxy
public class SchoolStudentVO implements ProxyEntityAvailable<SchoolStudentVO , SchoolStudentVOProxy> {
    private String id;
    private String classId;
    private String name;
    @Navigate(RelationTypeEnum.ManyToOne)
    private SchoolClassVO schoolClass;
    @Navigate(RelationTypeEnum.OneToOne)
    private SchoolStudentAddressVO schoolStudentAddress;

    @Override
    public Class<SchoolStudentVOProxy> proxyTableClass() {
        return SchoolStudentVOProxy.class;
    }
}
