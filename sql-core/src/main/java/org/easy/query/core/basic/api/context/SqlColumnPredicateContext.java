package org.easy.query.core.basic.api.context;

import java.util.List;

/**
 * @FileName: SqlColumnPredicateContext.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:53
 * @Created by xuejiaming
 */
public interface SqlColumnPredicateContext extends SqlContext {

     List<String> getProperties();
     void addColumnProperty(String propertyName);
}
