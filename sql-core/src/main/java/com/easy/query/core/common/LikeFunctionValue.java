package com.easy.query.core.common;

/**
 * create time 2026/10/4 14:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class LikeFunctionValue {
    public final boolean encrypt;
    public final String value;

    public LikeFunctionValue(boolean encrypt, String value) {
        this.encrypt = encrypt;
        this.value = value;
    }
}
