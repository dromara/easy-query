package com.easy.query.core.enums;

/**
 * create time 2025/5/3 22:49
 * 文件说明
 *
 * @author xuejiaming
 */
public enum OnDeleteActionEnum {
    /**
     * 级联删除则不会对子表进行任何操作
     */
    NO_ACTION,
    /**
     * 级联删除
     */
    CASCADE,
    /**
     * 级联删除 设置为空
     */
    SET_NULL,
//    SET_DEFAULT
}
