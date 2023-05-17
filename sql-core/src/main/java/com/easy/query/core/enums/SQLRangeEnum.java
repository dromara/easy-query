package com.easy.query.core.enums;

/**
 * @Description: 文件说明
 * @Date: 2023/3/17 22:06
 * @author xuejiaming
 */
public enum SQLRangeEnum {
    Open(1), Closed(2), closedOpen(4),openClosed(8);
    private final int code;

    SQLRangeEnum(int code){

        this.code = code;
    }
    private static final int OPEN_FIRST= Open.code | openClosed.code;
    private static final int OPEN_END= Open.code | closedOpen.code;
    public static boolean openFirst(SQLRangeEnum sqlRange){
        return (OPEN_FIRST& sqlRange.code)== sqlRange.code;
    }
    public static boolean openEnd(SQLRangeEnum sqlRange){
        return (OPEN_END& sqlRange.code)== sqlRange.code;
    }
}
