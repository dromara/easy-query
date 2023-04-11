package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.EasyShardingUtil;

import java.util.List;

/**
 * create time 2023/4/9 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyQueryExecutor implements EasyQueryExecutor{
    private final EasyPrepareParser easyPrepareParser;
    private final QueryCompilerContextFactory easyQueryCompilerContextFactory;

    public DefaultEasyQueryExecutor(EasyPrepareParser easyPrepareParser, QueryCompilerContextFactory easyQueryCompilerContextFactory){

        this.easyPrepareParser = easyPrepareParser;
        this.easyQueryCompilerContextFactory = easyQueryCompilerContextFactory;
    }
    @Override
    public <TR> List<TR> execute(EntityQueryExpression entityQueryExpression) {
        //todo 检查是否存在分片对象的查询
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(entityQueryExpression);
        QueryCompilerContext queryCompilerContext = easyQueryCompilerContextFactory.create(prepareParseResult);

        //需要分片
        if(!queryCompilerContext.isShardingQuery()){//queryCompilerContext.isSingleShardingQuery()
        }else{

        }
        boolean shardingQuery = EasyShardingUtil.isShardingQuery(entityQueryExpression);
        if(shardingQuery){

        }
        return null;
    }
}
