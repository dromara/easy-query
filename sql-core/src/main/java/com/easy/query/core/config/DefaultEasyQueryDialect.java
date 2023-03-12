package com.easy.query.core.config;

/**
 * @FileName: DefaultEasyQueryDialect.java
 * @Description: 文件说明
 * @Date: 2023/2/12 09:25
 * @Created by xuejiaming
 */
public class DefaultEasyQueryDialect extends AbstractEasyQueryDialect {
    @Override
    protected String getQuoteStart() {
        return null;
    }

    @Override
    protected String getQuoteEnd() {
        return null;
    }

    @Override
    public String getQuoteName(String keyword) {
        return keyword;
    }
}
