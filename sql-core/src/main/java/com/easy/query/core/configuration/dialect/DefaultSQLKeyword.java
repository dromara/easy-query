package com.easy.query.core.configuration.dialect;

/**
 * create time 2023/2/12 09:25
 * 没有方言
 *
 * @author xuejiaming
 */
public final class DefaultSQLKeyword implements SQLKeyword {

    @Override
    public String getQuoteName(String keyword) {
        return keyword;
    }
}
