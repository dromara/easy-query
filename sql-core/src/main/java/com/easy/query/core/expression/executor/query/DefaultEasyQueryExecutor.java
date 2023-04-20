//package com.easy.query.core.expression.executor.query;
//
//import com.easy.query.core.basic.jdbc.executor.EasyOldExecutor;
//import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
//import com.easy.query.core.enums.EasyBehaviorEnum;
//import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
//import com.easy.query.core.expression.executor.parser.ExecutionContext;
//import com.easy.query.core.expression.executor.parser.PrepareParseResult;
//import com.easy.query.core.expression.sql.EntityQueryExpression;
//import com.easy.query.core.sharding.merge.abstraction.StreamResult;
//import com.easy.query.core.sharding.merge.executor.ShardingExecutor;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import java.util.List;
//
///**
// * create time 2023/4/9 22:10
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DefaultEasyQueryExecutor implements EasyQueryExecutor {
//    private final EasyPrepareParser easyPrepareParser;
//    private final ExecutionContextFactory easyQueryCompilerContextFactory;
//    private final EasyOldExecutor easyExecutor;
//
//    public DefaultEasyQueryExecutor(EasyPrepareParser easyPrepareParser, ExecutionContextFactory easyQueryCompilerContextFactory, EasyOldExecutor easyExecutor) {
//
//        this.easyPrepareParser = easyPrepareParser;
//        this.easyQueryCompilerContextFactory = easyQueryCompilerContextFactory;
//        this.easyExecutor = easyExecutor;
//    }
//
//    @Override
//    public <TR> List<TR> execute(EntityQueryExpression entityQueryExpression, Class<TR> resultClass) {
//        //todo 检查是否存在分片对象的查询
//        PrepareParseResult prepareParseResult = easyPrepareParser.parse(entityQueryExpression);
//        ExecutionContext executionContext = easyQueryCompilerContextFactory.create(prepareParseResult);
//
//        //需要分片
//        if (queryCompilerContext.isShardingQuery()) {//queryCompilerContext.isSingleShardingQuery()
//            MergeQueryCompilerContext mergeQueryCompilerContext = (MergeQueryCompilerContext) queryCompilerContext;
//            StreamResult streamResult = ShardingExecutor.<StreamResult>execute(null, null, mergeQueryCompilerContext.GetEntityRouteResult().getRouteUnits().stream());
//            throw new NotImplementedException();
//        } else {
//            //非分片处理
//            boolean tracking = entityQueryExpression.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
//            String sql = entityQueryExpression.toSql();
//            return easyExecutor.query(ExecutorContext.create(entityQueryExpression.getRuntimeContext(), tracking), resultClass, sql, entityQueryExpression.getParameters());
//
//        }
//    }
//}
