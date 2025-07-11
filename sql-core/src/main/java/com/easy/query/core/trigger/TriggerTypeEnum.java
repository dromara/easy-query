package com.easy.query.core.trigger;

/**
 * create time 2025/7/3 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public enum TriggerTypeEnum {
    UNKNOWN(1),
    INSERT(1 << 1),
    UPDATE(1 << 2),
    DELETE(1 << 3);
    private final int code;

    TriggerTypeEnum(int code) {

        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TriggerTypeEnum enumOfOrNull(Integer val) {
        return enumOfOrDefault(val, null);
    }

    public static TriggerTypeEnum enumOfOrDefault(Integer val, TriggerTypeEnum def) {
        if (val != null) {
            switch (val) {
                case 1:
                    return UNKNOWN;
                case 1 << 1:
                    return INSERT;
                case 1 << 2:
                    return UPDATE;
                case 1 << 3:
                    return DELETE;
            }
        }
        return def;
    }
}
