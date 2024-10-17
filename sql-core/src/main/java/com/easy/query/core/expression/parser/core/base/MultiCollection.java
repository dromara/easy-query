package com.easy.query.core.expression.parser.core.base;

import java.util.Collection;
import java.util.List;

/**
 * create time 2024/10/17 12:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiCollection {
    Collection<Object> singleCollection();
    Collection<List<Object>> multiCollection();

}
