package com.easy.query.api.proxy.entity.save;

/**
 * create time 2025/9/18 20:36
 * 文件说明
 *
 * @author xuejiaming
 */
public enum SaveBehaviorEnum {
    //忽略根节点任何操作
    ROOT_IGNORE(1),
    //忽略根节点的更新
    ROOT_UPDATE_IGNORE(1<<1),
    //允许对象抢夺（转移引用）（允许变更拥有者）
    ALLOW_OWNERSHIP_CHANGE(1<<2),
    //忽略NULL的值对象
    IGNORE_NULL(1<<3),
    //忽略空对象
    IGNORE_EMPTY(1<<4);
    private final int code;

    SaveBehaviorEnum(int code) {

        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
