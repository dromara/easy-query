package com.easy.query.core.proxy.sql.include;

import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/10/25 15:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultIncludeContext implements IncludeContext,IncludeCollectorResult{
    private List<IncludeAvailable> includes = new ArrayList<>();

    @Override
    public <TPropertyProxy extends AbstractProxyEntity<TPropertyProxy, TProperty>, TProperty> NavigateInclude<TPropertyProxy, TProperty> query(SQLQueryable<TPropertyProxy, TProperty> includeMany) {
        DefaultNavigateInclude<TPropertyProxy, TProperty> include = new DefaultNavigateInclude<>(includeMany);
        includes.add(include);
        return include;
    }
    @Override
    public <TPropertyProxy extends AbstractProxyEntity<TPropertyProxy, TProperty>, TProperty> NavigateInclude<TPropertyProxy, TProperty> query(TPropertyProxy includeOne) {
        DefaultNavigateInclude<TPropertyProxy, TProperty> include = new DefaultNavigateInclude<>(includeOne);
        includes.add(include);
        return include;
    }

    @Override
    public List<IncludeAvailable> getIncludes() {
        return includes;
    }
}
