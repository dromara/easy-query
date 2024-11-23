package com.easy.query.test.vo;

import lombok.Data;

import java.util.List;

/**
 * create time 2024/11/21 20:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class SysMenuDto  {
    private Long id;
    /**
     * 菜单类型
     */
    private String type;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 路由路径
     */
    private String path;
    /**
     * 路由重定向
     */
    private String redirect;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 链接地址
     */
    private String url;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 是否隐藏（0不隐藏1隐藏）
     */
    private Boolean isHide;
    /**
     * 是否缓存（0不缓存1缓存）
     */
    private Boolean isKeepAlive;
    /**
     * 是否固定（0不固定1固定）
     */
    private Boolean isAffix;
    /**
     * 是否全屏显示（0否1是）
     */
    private Boolean isFull;
    /**
     * 是否内嵌显示（0否1是）
     */
    private Boolean isIframe;
    /**
     * 是否系统菜单（系统菜单仅超级管理员可见）
     */
    private Boolean isSystem;
    /**
     * 是否显示用户信息水印
     */
    private Boolean showUserInfo;
    /**
     * 是否全局的（所有人都具备）
     */
    private Boolean global;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态（0启用，1停用）
     */
    private Integer status;
    /**
     * 当前查询的菜单的来源
     */
    private String sourceName;
    /**
     * 该菜单的权限总的来源
     */
    private List<String> from;
    /**
     * 名称
     */
    private String name;
    /**
     * 上级ID
     */
    private Long parentId;
    /**
     * 排序
     */
    private Integer order;
    /**
     * 祖级列表
     */
    private String ancestors;
}
