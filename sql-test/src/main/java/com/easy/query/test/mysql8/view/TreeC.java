package com.easy.query.test.mysql8.view;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.api.EntityCteViewer;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.mysql8.TreeA;
import com.easy.query.test.mysql8.TreeB;
import com.easy.query.test.mysql8.proxy.TreeAProxy;
import com.easy.query.test.mysql8.view.proxy.TreeCProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2025/8/14 14:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_treec")
@EntityProxy
@FieldNameConstants
public class TreeC implements ProxyEntityAvailable<TreeC, TreeCProxy>, EntityCteViewer<TreeC> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String pid;

    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {TreeC.Fields.id}, targetProperty = {TreeC.Fields.pid})
    private List<TreeC> treeCList;

    @Override
    public Supplier<Query<TreeC>> viewConfigure(QueryRuntimeContext runtimeContext) {
        return () -> {

            SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
            ClientQueryable<TreeA> queryable = sqlClientApiFactory.createQueryable(TreeA.class, runtimeContext);
            return new EasyEntityQueryable<>(TreeAProxy.createTable(), queryable)
                    .leftJoin(TreeB.class, (a, b) -> a.id().eq(b.aid()))
                    .select(TreeC.class, (t1, t2) -> Select.of(
                            t1.FETCHER.allFields(),
                            t2.apid().as("pid")
                    ));
        };
    }
}
