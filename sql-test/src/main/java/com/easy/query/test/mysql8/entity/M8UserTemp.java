package com.easy.query.test.mysql8.entity;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.api.EntityCteViewer;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8UserProxy;
import com.easy.query.test.mysql8.entity.proxy.M8UserTempProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2025/5/31 08:40
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table
@FieldNameConstants
public class M8UserTemp implements ProxyEntityAvailable<M8UserTemp, M8UserTempProxy>, EntityCteViewer<M8UserTemp> {
    private String id;
    private String name;



    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {M8UserTemp.Fields.id},
            selfMappingProperty = {M8UserRole.Fields.userId},
            mappingClass = M8UserRole.class,
            targetProperty = {M8Role.Fields.id},
            targetMappingProperty = {M8UserRole.Fields.roleId},partitionOrder = PartitionOrderEnum.IGNORE)
    private List<M8Role> roles;

    @Override
    public Supplier<Query<M8UserTemp>> viewConfigure(QueryRuntimeContext runtimeContext) {
        return () -> {
            SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
            ClientQueryable<M8User> queryable = sqlClientApiFactory.createQueryable(M8User.class, runtimeContext);
            return new EasyEntityQueryable<>(M8UserProxy.createTable(), queryable)
                    .where(m -> {
                        m.age().isNull();
                    }).select(M8UserTemp.class);
        };
    }
}
