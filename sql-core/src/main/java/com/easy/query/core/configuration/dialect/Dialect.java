package com.easy.query.core.configuration.dialect;

/**
 * create time 2023/2/12 08:57
 * 数据库方言
 *
 * @author xuejiaming
 */
public interface Dialect {

    /**
     * 进行方言设置
     * @param keyword
     * @return
     */
    String getQuoteName(String keyword);
}
