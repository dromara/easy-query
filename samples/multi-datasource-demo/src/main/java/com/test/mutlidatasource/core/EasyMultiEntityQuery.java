package com.test.mutlidatasource.core;

import com.easy.query.api.proxy.client.EasyEntityQuery;

import java.util.function.Function;

/**
 * create time 2024/3/13 15:21
 * 如果当前没有指定数据源则返回默认数据源
 *
 * @author xuejiaming
 */
public interface EasyMultiEntityQuery extends EasyEntityQuery {
    /**
     * 设置当前上下文线程使用哪个数据源
     * @param dataSource
     */
    void setCurrent(String dataSource);

    /**
     * 返回一个存在的数据源没有就报错
     * @param dataSource
     * @return
     */
    EasyEntityQuery getByDataSource(String dataSource);

    /**
     * 执行指定数据源的方法并且返回结果
     * @param dataSource
     * @param dataSourceFunction
     * @return
     * @param <TResult>
     */
    <TResult> TResult executeScope(String dataSource, Function<EasyEntityQuery,TResult> dataSourceFunction);

    /**
     * 清楚当前上下文数据源
     */
    void clear();
}
