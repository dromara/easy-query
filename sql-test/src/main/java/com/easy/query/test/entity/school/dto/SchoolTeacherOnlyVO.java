package com.easy.query.test.entity.school.dto;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.dto.proxy.SchoolTeacherVOProxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/21 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@ToString
public class SchoolTeacherOnlyVO {
    private String id;
    private String name;
}
