package com.easy.query.logicdel;

/**
 * create time 2023/4/1 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class CurrentUserHelper {
    private static String userId;
    public static void setUserId(String userId){
        CurrentUserHelper.userId=userId;
    }
    public static String getUserId(){
        return userId;
    }
    private static String tenantId;
    public static void setTenantId(String tenantId){
        CurrentUserHelper.tenantId=tenantId;
    }
    public static String getTenantId(){
        return tenantId;
    }
}
