package org.easy.query.core.basic.api.context;

import java.util.List;

/**
 * @FileName: SqlContext.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:54
 * @Created by xuejiaming
 */
public interface SqlPredicateContext extends SqlContext{
     List<Object> getParameters();
     void addParameter(Object parameter);
}
