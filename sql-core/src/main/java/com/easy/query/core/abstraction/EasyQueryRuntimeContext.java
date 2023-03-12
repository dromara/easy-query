package com.easy.query.core.abstraction;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.configuration.EasyQueryConfiguration;

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
