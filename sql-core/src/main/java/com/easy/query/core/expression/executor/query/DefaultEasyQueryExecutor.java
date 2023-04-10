package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.executor.parser.DefaultEasyPrepareParser;
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

    public DefaultEasyQueryExecutor(EasyPrepareParser easyPrepareParser){

        this.easyPrepareParser = easyPrepareParser;
    }
    @Override
    public <TR> List<TR> execute(EntityQueryExpression entityQueryExpression) {
        //todo 检查是否存在分片对象的查询
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(entityQueryExpression);
        //需要分片
        if(ArrayUtil.isNotEmpty(prepareParseResult.getShardingEntities())){

        }else{

        }
        boolean shardingQuery = EasyShardingUtil.isShardingQuery(entityQueryExpression);
        if(shardingQuery){

        }
        return null;
    }
}
