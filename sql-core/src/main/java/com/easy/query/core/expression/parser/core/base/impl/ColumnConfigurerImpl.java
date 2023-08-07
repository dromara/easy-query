package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.Configurer;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;

/**
 * create time 2023/8/7 08:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnConfigurerImpl<T1> implements ColumnConfigurer<T1> {
    private final TableAvailable table;
    private final Configurer configurer;

    public ColumnConfigurerImpl(TableAvailable table, Configurer configurer){

        this.table = table;
        this.configurer = configurer;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Configurer getConfigurer() {
        return configurer;
    }
}
