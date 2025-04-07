//package com.easy.query.test.entity.blogtest.dto;
//
//import com.easy.query.core.annotation.NavigateFlat;
//import com.easy.query.test.entity.blogtest.SysMenu;
//import com.easy.query.test.entity.blogtest.SysRole;
//import com.easy.query.test.entity.blogtest.SysUser;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * create time 2024/5/15 22:20
// * 文件说明
// *
// * @author xuejiaming
// */
//@Data
//public class SysUserFlatDTO {
//    private String id;
//    private String name;
//    private LocalDateTime createTime;
//
//    //穿透获取用户下的roles下的menus下的id 如果穿透获取的是非基本类型那么对象只能是数据库对象而不是dto对象
//    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {
//            SysUser.Fields.roles,
//            SysRole.Fields.menus,
//            SysMenu.Fields.id
//    })
//    private List<String> menuIds;
//    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {
//            SysUser.Fields.roles,
//            SysMenu.Fields.id
//    })
//    private List<String> roleIds;
//}
