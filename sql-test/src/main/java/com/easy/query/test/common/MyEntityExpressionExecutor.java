//package com.easy.query.test.common;
//
//import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
//import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
//import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
//import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
//import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
//import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
//import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
//
//import java.util.List;
//
///**
// * create time 2025/1/11 14:52
// * 文件说明
// *
// * @author xuejiaming
// */
//public class MyEntityExpressionExecutor extends DefaultEntityExpressionExecutor {
//    public MyEntityExpressionExecutor(EasyPrepareParser easyPrepareParser, ExecutionContextFactory executionContextFactory) {
//        super(easyPrepareParser, executionContextFactory);
//    }
//
//    @Override
//    public <TR> List<TR> querySQL(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters) {
//        sql+=" and tenant_id=?";
//        sqlParameters.add(new EasyConstSQLParameter(null,null,"租户id"));
//        return super.querySQL(executorContext, resultMetadata, sql, sqlParameters);
//    }
//
//    @Override
//    public long executeSQLRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters) {
//        sql+=" and tenant_id=?";
//        sqlParameters.add(new EasyConstSQLParameter(null,null,"租户id"));
//        return super.executeSQLRows(executorContext, sql, sqlParameters);
//    }
//}
