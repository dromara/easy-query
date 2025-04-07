//package com.easy.query.test.dto.autotest;
//
//import com.easy.query.core.annotation.NavigateFlat;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * create time 2024/5/15 16:59
// * 文件说明
// *
// * @author xuejiaming
// */
//@Data
//public class UserInfoDTO {
//
//    private Long id;
//    /** 登录账号 */
//    private String username;
//    /** 账户状态 {1:有效,2:过期,3:禁用} */
//    private Integer status;
//    /** 用户名 */
//    private String realName;
//    /** 电子邮件 */
//    private String email;
//    /** 手机号码 */
//    private String mobile;
//    /** 创建时间 */
//    private LocalDateTime createTime;
//    /** 最后登录时间 */
//    private LocalDateTime loginTime;
//
//    @NavigateFlat(pathAlias = "roles.permission")
//    private List<String> rolePermissions;
//
//    @NavigateFlat(pathAlias="roles.resources.permissionMark")
//    private List<String> resourcePermissionMarks;
//
//    @NavigateFlat(pathAlias="roles.routes")
//    private List<Route> routes;
//
//}
//
