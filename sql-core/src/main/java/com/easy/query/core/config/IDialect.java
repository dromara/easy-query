package com.easy.query.core.config;

/**
 * @FileName: EasyQueryDialect.java
 * @Description: 文件说明
 * @Date: 2023/2/12 08:57
 * @author xuejiaming
 */
public interface IDialect {

    String getQuoteName(String keyword);
}
