package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;
import com.easy.query.core.sharding.rule.table.TableRouteRule;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/20 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableRouteManager implements TableRouteManager{
    private final Map<Class<?>, TableRouteRule> entityRouteRuleCache= new ConcurrentHashMap<>();
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

        TableRouteRule tableRouteRule = entityRouteRuleCache.get(entityClass);
        if(tableRouteRule==null){
            throw new EasyQueryInvalidOperationException(ClassUtil.getSimpleName(entityClass) +" not found table route rule");
        }
        return tableRouteRule;
    }

    @Override
    public boolean addRouteRule(TableRouteRule tableRouteRule) {
        TableRouteRule oldTableRouteRule = entityRouteRuleCache.get(tableRouteRule.entityClass());
        if(oldTableRouteRule==null){
            entityRouteRuleCache.put(tableRouteRule.entityClass(),tableRouteRule);
            return true;
        }
        return false;
    }
}
