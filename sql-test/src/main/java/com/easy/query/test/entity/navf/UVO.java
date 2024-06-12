package com.easy.query.test.entity.navf;

import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.enums.RelationMappingTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/6/12 17:20
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class UVO {
    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {
            User.Fields.roles,
            Role.Fields.permission
    })
    private List<String> roles;
    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {
            User.Fields.roles,
            Role.Fields.resources,
            Resource.Fields.permissionMark
    })
    private List<String> resources;
    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {
            User.Fields.roles,
            Role.Fields.routes
    })
    private List<Route> routes;
}
