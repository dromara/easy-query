package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.metadata.EntityShardingOrder;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.Set;

/**
 * create time 2023/4/24 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryPrepareParseResult implements QueryPrepareParseResult {
    private final Set<TableAvailable> shardingTables;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final EasyQuerySqlExpression easyQuerySqlExpression;
    private final boolean sharding;
    private boolean startsWithGroupByInOrderBy;
    private final long offset;
    private final long rows;
    private final SequenceOrderPrepareParseResult sequenceOrderPrepareParseResult;

    public EasyQueryPrepareParseResult(ExecutorContext executorContext, Set<TableAvailable> shardingEntities, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        this.shardingTables = shardingEntities;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.easyQuerySqlExpression = entityQueryExpressionBuilder.toExpression();
        this.sharding = EasyCollectionUtil.isNotEmpty(shardingEntities);
        this.offset = easyQuerySqlExpression.getOffset();
        this.rows = easyQuerySqlExpression.getRows();
        this.sequenceOrderPrepareParseResult=initSequenceOrderPrepareParseResult(executorContext);
    }
    private SequenceOrderPrepareParseResult initSequenceOrderPrepareParseResult(ExecutorContext executorContext){
        //存在分片对象的情况下
        if(EasyCollectionUtil.isNotEmpty(shardingTables)){
            SqlBuilderSegment order = easyQuerySqlExpression.getOrder();
            if(SqlSegmentUtil.isNotEmpty(order)){
                SqlSegment firstOrder = EasyCollectionUtil.first(order.getSqlSegments());
                OrderByColumnSegment firstOrderColumn = (OrderByColumnSegment) firstOrder;
                TableAvailable table = firstOrderColumn.getTable();
                EntityShardingOrder entityShardingOrder = table.getEntityMetadata().getEntityShardingOrder();
                if(entityShardingOrder!=null){
                    //存在配置
                    Boolean asc = entityShardingOrder.getSequenceProperties().get(firstOrderColumn.getPropertyName());
                    if(asc!=null){
                        boolean reverse = firstOrderColumn.isAsc() != asc;
                        return new SequenceOrderPrepareParseResult(table,entityShardingOrder.getTableComparator(),reverse,entityShardingOrder.getConnectionsLimit());
                    }
                }
            }else{//默认匹配顺序
                TableAvailable table = EasyCollectionUtil.first(shardingTables);
                EntityShardingOrder entityShardingOrder = table.getEntityMetadata().getEntityShardingOrder();
                if(entityShardingOrder!=null&&entityShardingOrder.getExecuteMethodBehavior().hasBehavior(executorContext.getExecuteMethod())){
                    return new SequenceOrderPrepareParseResult(table,entityShardingOrder.getTableComparator(),entityShardingOrder.isReverse(),entityShardingOrder.getConnectionsLimit());
                }
            }
        }
        return null;
    }

    @Override
    public Set<TableAvailable> getShardingTables() {
        return shardingTables;
    }

    @Override
    public EntityQueryExpressionBuilder getEntityExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public long getRows() {
        return rows;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public boolean isStartsWithGroupByInOrderBy() {
        return startsWithGroupByInOrderBy;
    }

    @Override
    public void setStartsWithGroupByInOrderBy(boolean startsWithGroupByInOrderBy) {
        this.startsWithGroupByInOrderBy = startsWithGroupByInOrderBy;
    }


    @Override
    public EasyQuerySqlExpression getEasyEntityPredicateSqlExpression() {
        return easyQuerySqlExpression;
    }

    @Override
    public SequenceOrderPrepareParseResult getSequenceOrderPrepareParseResult() {
        return sequenceOrderPrepareParseResult;
    }
}
