package com.easy.query.core.expression.executor.parser.context;

import java.util.List;

/**
 * create time 2023/5/18 21:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityParseContext extends PrepareParseContext{
     List<Object> getEntities();
}
