//package com.easy.query.test.mysql8.dto;
//
//import com.easy.query.core.annotation.Navigate;
//import com.easy.query.core.enums.RelationTypeEnum;
//import lombok.Data;
//
//import java.util.List;
//
///**
// * create time 2025/12/9 15:41
// * {@link com.easy.query.test.mysql8.entity.M8User}
// *
// * @author xuejiaming
// */
//@Data
//public class M8UserDTO {
//
//    private String id;
//    private String name;
//    @Navigate(value = RelationTypeEnum.ManyToMany)
//    private List<InternalRoles> roles;
//
//
//    /**
//     * {@link com.easy.query.test.mysql8.entity.M8Role}
//     **/
//    @Data
//    public static class InternalRoles {
//
//        private String id;
//        private String name;
//    }
//}