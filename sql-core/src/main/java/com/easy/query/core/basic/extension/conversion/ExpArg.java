package com.easy.query.core.basic.extension.conversion;

/**
 * create time 2025/1/20 23:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpArg {
    public final ExpArgTypeEnum argType;
    public final String prop;
    public final Object value;

    public ExpArg(ExpArgTypeEnum argType, String prop, Object value){
        this.argType = argType;
        this.prop = prop;
        this.value = value;
    }
}
