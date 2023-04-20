package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/20 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableRouteManager implements TableRouteManager{
    private final EntityMetadataManager entityMetadataManager;

    public DefaultTableRouteManager(EntityMetadataManager entityMetadataManager){

        this.entityMetadataManager = entityMetadataManager;
    }
    @Override
    public Collection<TableRouteUnit> routeTo(Class<?> entityClass, DataSourceRouteResult dataSourceRouteResult, SqlParserResult sqlParserResult) {
        return null;
    }

    @Override
    public TableRouteRule getRouteRule(Class<?> entityClass) {
        return null;
    }
}
