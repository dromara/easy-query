package com.easy.query.mysql.config;

import com.easy.query.core.config.AbstractEasyQueryDialect;

/**
 * @FileName: MySQLDialect.java
 * @Description: 文件说明
 * @Date: 2023/2/12 09:08
 * @Created by xuejiaming
 */
public class MySQLDialect extends AbstractEasyQueryDialect {
    @Override
    public String getQuoteStart() {
        return "`";
    }

    @Override
    public String getQuoteEnd() {
        return "`";
    }
}
