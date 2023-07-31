package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.stream.JdbcStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.stream.StreamIterable;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/4/16 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityExpressionExecutor {
   default  <TR> List<TR> query(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder){
       try(JdbcStreamResultSet<TR> jdbcStreamResultSet = queryStreamResultSet(executorContext, resultMetadata, entityQueryExpressionBuilder)){
           StreamIterable<TR> streamResult = jdbcStreamResultSet.getStreamResult();
           return EasyCollectionUtil.newArrayList(streamResult);
       } catch (SQLException e) {
           throw new EasyQuerySQLCommandException(e);
       }
   }

   <TR> JdbcStreamResultSet<TR> queryStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
    <TR> List<TR> querySQL(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters);
    long executeSQLRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters);

    <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement);

    /**
     * update或者delete对象
     * @param executorContext
     * @param entityExpressionBuilder
     * @param entities
     * @return
     * @param <T>
     */
    <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities);

    /**
     * update或者delete表达式
     * @param executorContext
     * @param entityPredicateExpressionBuilder
     * @return
     * @param <T>
     */
    <T> long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder);

}
