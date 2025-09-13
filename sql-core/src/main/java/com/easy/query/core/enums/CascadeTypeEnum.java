package com.easy.query.core.enums;

/**
 * create time 2025/9/10 13:36
 * 文件说明
 *
 * @author xuejiaming
 */
public enum CascadeTypeEnum {
    //默认涉及到对象脱钩框架自动处理 默认采用set null遇到多对多且有中间表则会报错需要用户手动处理
    AUTO,
    //涉及到对象脱钩不进行操作
    NO_ACTION,
    //涉及到对象脱钩关联键设置为null
    SET_NULL,
    //涉及到对象脱钩删除目标对象
    DELETE
}
