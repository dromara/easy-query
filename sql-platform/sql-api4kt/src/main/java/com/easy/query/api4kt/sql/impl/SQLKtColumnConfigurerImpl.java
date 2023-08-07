package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;

/**
 * create time 2023/8/7 14:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtColumnConfigurerImpl<T> implements SQLKtColumnConfigurer<T> {
    private final ColumnConfigurer<T> configurer;

    public SQLKtColumnConfigurerImpl(ColumnConfigurer<T> configurer){

        this.configurer = configurer;
    }

    @Override
    public ColumnConfigurer<T> getColumnConfigurer() {
        return configurer;
    }
}
