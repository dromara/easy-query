package org.easy.query.core.abstraction;

import org.easy.query.core.impl.SelectContext;

/**
 * @FileName: SqlExpressionParser.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:44
 * @Created by xuejiaming
 */
public interface SqlExpressionParser {
    String parse(SelectContext selectContext);
}
