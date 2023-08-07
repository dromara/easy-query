package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;

/**
 * create time 2023/8/7 08:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnConfigurerImpl<T> implements SQLColumnConfigurer<T> {
    private final ColumnConfigurer<T> configurer;

    public SQLColumnConfigurerImpl(ColumnConfigurer<T> configurer){

        this.configurer = configurer;
    }
    @Override
    public ColumnConfigurer<T> getColumnConfigurer() {
        return configurer;
    }
}
