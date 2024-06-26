package com.easy.query.core.configuration.dialect;

/**
 * create time 2023/2/12 08:57
 * 数据库关键字处理
 *
 * @author xuejiaming
 */
public interface SQLKeyword {

    /**
     * 进行方言设置
     * @param keyword
     * @return
     */
    String getQuoteName(String keyword);
}
