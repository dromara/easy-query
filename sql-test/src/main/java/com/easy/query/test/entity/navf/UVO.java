package com.easy.query.test.entity.navf;

import com.easy.query.core.annotation.NavigateFlat;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/6/12 17:20
 * {@link com.easy.query.test.entity.navf.User}
 *
 * @author xuejiaming
 */
@Data
public class UVO {
    @NavigateFlat(pathAlias = "roles.permission")
    private List<String> roles;
    @NavigateFlat(pathAlias = "roles.resources.permissionMark")
    private List<String> resources;
    @NavigateFlat(pathAlias = "roles.routes")
    private List<Route> routes;
}
