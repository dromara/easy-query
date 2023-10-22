package com.easy.query.core.expression.parser.core.base;

/**
 * create time 2023/10/22 12:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TreeCTESelector {
    void setDeep(int deep);
    void setUp(boolean up);
    void setPathSelector(String pathSelector);
    void setPathSeparator(String pathSeparator);
}
