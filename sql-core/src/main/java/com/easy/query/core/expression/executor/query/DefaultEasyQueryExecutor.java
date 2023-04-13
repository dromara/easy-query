package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.EasyShardingUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * create time 2023/4/9 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyQueryExecutor implements EasyQueryExecutor {
    private final EasyPrepareParser easyPrepareParser;
    private final QueryCompilerContextFactory easyQueryCompilerContextFactory;
    private final EasyExecutor easyExecutor;

    public DefaultEasyQueryExecutor(EasyPrepareParser easyPrepareParser, QueryCompilerContextFactory easyQueryCompilerContextFactory, EasyExecutor easyExecutor) {

        this.easyPrepareParser = easyPrepareParser;
        this.easyQueryCompilerContextFactory = easyQueryCompilerContextFactory;
        this.easyExecutor = easyExecutor;
    }

    @Override
    public <TR> List<TR> execute(EntityQueryExpression entityQueryExpression,Class<TR> resultClass) {
        //todo 检查是否存在分片对象的查询
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(entityQueryExpression);
        QueryCompilerContext queryCompilerContext = easyQueryCompilerContextFactory.create(prepareParseResult);

        //需要分片
        if (queryCompilerContext.isShardingQuery()) {//queryCompilerContext.isSingleShardingQuery()

            throw new NotImplementedException();
        } else {
            //非分片处理
            boolean tracking = entityQueryExpression.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
            String sql = entityQueryExpression.toSql();
            return easyExecutor.query(ExecutorContext.create(entityQueryExpression.getRuntimeContext(),tracking ), resultClass, sql, entityQueryExpression.getParameters());

        }
    }
}
