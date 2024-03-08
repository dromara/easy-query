package com.easy.query.test.entity.testrelation.vo;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2024/3/8 16:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class TestRouteDTO1 {

    /**
     * 主键ID
     */
    @Column(primaryKey = true)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 请求路径
     */
    private String requestPath;
}
