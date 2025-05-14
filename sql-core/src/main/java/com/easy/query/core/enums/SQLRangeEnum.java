package com.easy.query.core.enums;

import com.easy.query.core.util.EasyBitwiseUtil;

/**
 * @Description: 文件说明
 * create time 2023/3/17 22:06
 * @author xuejiaming
 */
public enum SQLRangeEnum {
    OPEN(1), CLOSED(2), CLOSED_OPEN(4), OPEN_CLOSED(8);
    private final int code;

    SQLRangeEnum(int code){

        this.code = code;
    }
    private static final int OPEN_FIRST= OPEN.code | OPEN_CLOSED.code;
    private static final int OPEN_END= OPEN.code | CLOSED_OPEN.code;
    public static boolean openFirst(SQLRangeEnum sqlRange){
        return EasyBitwiseUtil.hasBit(OPEN_FIRST,sqlRange.code);
    }
    public static boolean openEnd(SQLRangeEnum sqlRange){
        return EasyBitwiseUtil.hasBit(OPEN_END,sqlRange.code);
    }
}
