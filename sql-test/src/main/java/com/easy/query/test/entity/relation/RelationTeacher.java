package com.easy.query.test.entity.relation;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.relation.proxy.RelationTeacherProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/3/1 17:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("relation_teacher")
@EntityProxy
@Data
public class RelationTeacher implements ProxyEntityAvailable<RelationTeacher , RelationTeacherProxy> {
    @Column(primaryKey = true)
    private String id;

    private String name;

    /**
     * book type=2
     */
    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty ="userId", extraFilter = BookNavigateExtraFilterStrategy.class)
    private List<RelationBook> books;

    @Override
    public Class<RelationTeacherProxy> proxyTableClass() {
        return RelationTeacherProxy.class;
    }
}
