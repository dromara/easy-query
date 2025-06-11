package com.easy.query.test.entity.tolistflat;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.tolistflat.proxy.TestABProxy;
import com.easy.query.test.entity.tolistflat.proxy.TestAProxy;
import com.easy.query.test.entity.tolistflat.proxy.TestBProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/6/11 10:42
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("test_a")
public class TestA implements ProxyEntityAvailable<TestA , TestAProxy> {
    @Column(primaryKey = true)
    private String aid;
    private String aname;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {TestAProxy.Fields.aid},
            selfMappingProperty = {TestABProxy.Fields.aid},
            mappingClass = TestAB.class,
            targetProperty = {TestBProxy.Fields.bid},
            targetMappingProperty = {TestABProxy.Fields.bid},subQueryToGroupJoin = true)
    private List<TestB> bList;
}
