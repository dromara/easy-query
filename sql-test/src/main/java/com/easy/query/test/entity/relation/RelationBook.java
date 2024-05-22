package com.easy.query.test.entity.relation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.relation.proxy.RelationBookProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2024/3/1 17:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("relation_book")
@EntityProxy
@Data
public class RelationBook implements ProxyEntityAvailable<RelationBook , RelationBookProxy> {
    @Column(primaryKey = true)
    private String id;
    private String userId;

    private String name;
    //学生书籍和老师数据
    private Integer bookType;
    private LocalDateTime createTime;
}
