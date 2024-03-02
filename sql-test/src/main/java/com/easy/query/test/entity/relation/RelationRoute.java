package com.easy.query.test.entity.relation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2024/3/2 16:04
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("relation_route")
@Data
public class RelationRoute {
    @Column(primaryKey = true)
    private String firstId;
    @Column(primaryKey = true)
    private String secondId;
    @Column(primaryKey = true)
    private Integer type;

}
