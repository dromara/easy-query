package com.easy.query.test.entity.subjoin;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.subjoin.proxy.SubJoinBookProxy;
import com.easy.query.test.entity.subjoin.proxy.SubJoinUserProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/2/20 15:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("sub_join_user")
@EntityProxy
public class SubJoinUser implements ProxyEntityAvailable<SubJoinUser , SubJoinUserProxy> {

    @Column(primaryKey = true)
    private String id;

    private Integer age;

    private String name;

    private LocalDateTime createTime;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {SubJoinUserProxy.Fields.id}, targetProperty = {SubJoinBookProxy.Fields.uid})
    private List<SubJoinBook> books;
}
