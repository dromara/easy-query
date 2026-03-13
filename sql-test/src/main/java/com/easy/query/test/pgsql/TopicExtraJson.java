package com.easy.query.test.pgsql;

import lombok.Data;

/**
 * create time 2026/3/13 10:16
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class TopicExtraJson implements JsonObject {
    private Boolean success;
    private Integer age;
    private String code;
    private String name;
}
