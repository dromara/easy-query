package com.easy.query.api.proxy.entity.save;

/**
 * create time 2025/9/11 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public enum OwnershipPolicyEnum {
    EnforceSingleOwner,   // 禁止对象抢夺（强制唯一拥有者）
    AllowOwnershipChange,    // 允许对象抢夺（转移引用）（允许变更拥有者）
}
