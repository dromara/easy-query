package com.easy.query.core.enums;

/**
 * create time 2023/3/25 09:01
 * 文件说明
 *
 * @author xuejiaming
 */
public enum EasyBehaviorEnum {
    LOGIC_DELETE(1),
    USE_INTERCEPTOR(1<<1),
    USE_TRACKING(1<<2);

    private final int code;

    EasyBehaviorEnum(int code){

        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
