package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.PredicatePrepareParseContext;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TablePredicateParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Set;

/**
 * create time 2023/4/26 11:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyPredicatePrepareParseResult implements PredicatePrepareParseResult{
    private final ExecutorContext executorContext;
    private final TablePredicateParseDescriptor tablePredicateParseDescriptor;
    private final EntityPredicateExpressionBuilder entityPredicateExpressionBuilder;
    private final EntityPredicateSQLExpression entityPredicateSQLExpression;
    private final boolean sharding;

    public EasyPredicatePrepareParseResult(PredicatePrepareParseContext predicatePrepareParseContext, TablePredicateParseDescriptor tablePredicateParseDescriptor){
        this.executorContext = predicatePrepareParseContext.getExecutorContext();
        this.tablePredicateParseDescriptor = tablePredicateParseDescriptor;

        this.entityPredicateExpressionBuilder = predicatePrepareParseContext.getEntityExpressionBuilder();
        this.entityPredicateSQLExpression = predicatePrepareParseContext.getEntityPredicateSQLExpression();
        this.sharding = EasyCollectionUtil.isNotEmpty(tablePredicateParseDescriptor.getTables());
    }

    @Override
    public TablePredicateParseDescriptor getTablePredicateParseDescriptor() {
        return tablePredicateParseDescriptor;
    }

    @Override
    public EntityPredicateSQLExpression getEntityPredicateSQLExpression() {
        return entityPredicateSQLExpression;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public TableParseDescriptor getTableParseDescriptor() {
        return tablePredicateParseDescriptor;
    }

    @Override
    public EntityPredicateExpressionBuilder getEntityExpressionBuilder() {
        return entityPredicateExpressionBuilder;
    }
}
