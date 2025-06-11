package com.easy.query.test.entity.tolistflat;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.tolistflat.proxy.TestBCProxy;
import com.easy.query.test.entity.tolistflat.proxy.TestBProxy;
import com.easy.query.test.entity.tolistflat.proxy.TestcProxy;
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
@Table("test_b")
public class TestB implements ProxyEntityAvailable<TestB , TestBProxy> {
    @Column(primaryKey = true)
    private String bid;
    private String bname;

    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {TestBProxy.Fields.bid},
            selfMappingProperty = {TestBCProxy.Fields.bid},
            mappingClass = TestBC.class,
            targetProperty = {TestcProxy.Fields.cid},
            targetMappingProperty = {TestBCProxy.Fields.cid},subQueryToGroupJoin = true)
    private List<Testc> cList;
}
