//package com.easy.query.test.entity.blogtest.dto;
//
//import com.easy.query.core.annotation.EntityProxy;
//import com.easy.query.core.annotation.NavigateFlat;
//import com.easy.query.core.annotation.NavigateJoin;
//import lombok.Data;
//import lombok.experimental.FieldNameConstants;
//
//import java.time.LocalDateTime;
//
///**
// * create time 2024/5/15 22:04
// *
// * @author xuejiaming
// */
//@Data
//@FieldNameConstants
//@EntityProxy
//public class SysUserAutoDTO {
//    private String id;
//    private String name;
//    private LocalDateTime createTime;
//    //来自SysUserAddress.addr
//    @NavigateFlat(pathAlias = "address.addr")
//    private String myAddress1;
//    @NavigateJoin(pathAlias =  "address.addr")
//    private String myAddress2;
//}
