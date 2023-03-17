package com.easy.query.core.enums;

/**
 * @FileName: SqlRangeeNUM.java
 * @Description: 文件说明
 * @Date: 2023/3/17 22:06
 * @Created by xuejiaming
 */
public enum SqlRangeEnum {
    Open(1), Closed(2), closedOpen(4),openClosed(8);
    private final int code;

    SqlRangeEnum(int code){

        this.code = code;
    }
    private static int OPEN_FIRST= Open.code | openClosed.code;
    private static int OPEN_END= Open.code | closedOpen.code;
    public static boolean openFirst(SqlRangeEnum sqlRange){
        return (OPEN_FIRST& sqlRange.code)== sqlRange.code;
    }
    public static boolean openEnd(SqlRangeEnum sqlRange){
        return (OPEN_END& sqlRange.code)== sqlRange.code;
    }
}
