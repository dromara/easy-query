package com.easy.query.test.pgsql;

import com.easy.query.core.annotation.Column;
import lombok.Data;

import java.util.List;

/**
 * create time 2026/3/13 20:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class TopicJson2VO {
    private String id;
    private String name;
    private TopicExtraJson extraJson;
    private List<TopicExtraJson> extraJsonArray;
}
