package com.easy.query.core.config;

/**
 * @FileName: EasyQueryDialect.java
 * @Description: 文件说明
 * @Date: 2023/2/12 08:57
 * @Created by xuejiaming
 */
public interface EasyQueryDialect {

    String getQuoteName(String keyword);
}
