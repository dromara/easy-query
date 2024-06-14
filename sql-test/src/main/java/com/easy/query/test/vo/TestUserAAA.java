package com.easy.query.test.vo;

import lombok.Data;

/**
 * create time 2024/6/14 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class TestUserAAA {

    private Integer lineFlag;

    /**
     * 中间件同步状态 1未同步 2已同步
     */
    private Integer dStatus;

    /**
     * 使用3区
     */
    private Integer useQ3;
}
