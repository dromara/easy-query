package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import org.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import org.easy.query.core.configuration.types.EasyQueryConfiguration;

/**
 * @FileName: JQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:46
 * @Created by xuejiaming
 */
public interface EasyQueryRuntimeContext {
    EasyQueryConfiguration getEasyQueryConfiguration();
    EntityMetadataManager getEntityMetadataManager();
    EasyQueryLambdaFactory getEasyQueryLambdaFactory();
    EasyConnectionManager getConnectionManager();
    EasyExecutor getEasyExecutor();
    EasyJdbcTypeHandlerManager getEasyJdbcTypeHandlerManager();
    EasySqlApiFactory getSqlApiFactory();
    EasySqlExpressionFactory getSqlExpressionFactory();
}
