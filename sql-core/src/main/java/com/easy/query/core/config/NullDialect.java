package com.easy.query.core.config;

/**
 * create time 2023/2/12 09:25
 * 没有方言
 *
 * @author xuejiaming
 */
public class NullDialect extends AbstractDialect {
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
