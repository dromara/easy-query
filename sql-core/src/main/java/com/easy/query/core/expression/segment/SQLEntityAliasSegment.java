package com.easy.query.core.expression.segment;

/**
 * @Description: 文件说明
 * create time 2023/3/4 23:48
 * @author xuejiaming
 */
public interface SQLEntityAliasSegment extends SQLEntitySegment {
    String getAlias();
    void setAlias(String alias);
}
