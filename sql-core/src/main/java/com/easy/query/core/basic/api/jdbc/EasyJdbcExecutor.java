package com.easy.query.core.basic.api.jdbc;

import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultMetadata;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.sql.builder.EmptyExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * @FileName: DefaultEasyJDBC.java
 * @Description: 文件说明
 * @Date: 2023/3/12 22:42
 * @author xuejiaming
 */
public class EasyJdbcExecutor implements JdbcExecutor {
    private final QueryRuntimeContext runtimeContext;

    public EasyJdbcExecutor(QueryRuntimeContext runtimeContext){
        this.runtimeContext = runtimeContext;
    }


    @Override
    public <T> List<T> sqlQuery(String sql, Class<T> clazz, List<SQLParameter> sqlParameters) {
        EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
        ExecutorContext executorContext = ExecutorContext.create(new EmptyExpressionContext(runtimeContext), true, ExecuteMethodEnum.LIST);
        executorContext.setMapToBeanStrict(false);
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        return entityExpressionExecutor.querySQL(executorContext, new EntityResultMetadata<>(entityMetadata), sql, sqlParameters);
    }

    @Override
    public long sqlExecute(String sql, List<Object> parameters) {
        List<SQLParameter> sqlParameters = EasyCollectionUtil.map(parameters, o -> new EasyConstSQLParameter(null, null, o));
        EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
        return entityExpressionExecutor.executeSQLRows(ExecutorContext.create(new EmptyExpressionContext(runtimeContext),false,ExecuteMethodEnum.UNKNOWN), sql, sqlParameters);
    }
}
