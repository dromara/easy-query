package com.easy.query.core.enums;

/**
 * create time 2025/9/10 13:36
 * 文件说明
 *
 * @author xuejiaming
 */
public enum SaveModeEnum {
    //自动检测当前导航属性(多对多有中间表需要用户指定)
    AUTO_CHECK,
    //当前导航属性忽略不保存
    IGNORE,
    //当前导航属性是自身的聚合根
    AGGREGATE_ROOT,
    //当前导航属性是自身的值对象
    VALUE_OBJECT
}
