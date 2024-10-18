package com.easy.query.test.dto.autodto;

import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2024/9/2 10:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyNavigateValueSetter implements NavigateValueSetter {

    @Override
    public Object beforeSet(Class<?> entityClass,String property,Object schoolStudentAOS) {
        return ((List<SchoolClassAOProp14.SchoolStudentAO>)schoolStudentAOS).stream().sorted((a,b)->b.getId().hashCode()-a.getId().hashCode()).collect(Collectors.toList());
    }
}
