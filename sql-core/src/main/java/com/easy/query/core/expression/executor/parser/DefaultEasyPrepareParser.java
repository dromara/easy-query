package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.executor.parser.context.EntityParseContext;
import com.easy.query.core.expression.executor.parser.context.InsertEntityParseContext;
import com.easy.query.core.expression.executor.parser.context.PredicatePrepareParseContext;
import com.easy.query.core.expression.executor.parser.context.PrepareParseContext;
import com.easy.query.core.expression.executor.parser.context.QueryPredicateParseContext;
import com.easy.query.core.expression.executor.parser.descriptor.TableEntityParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TablePredicateParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.impl.DefaultEmptyTableParseDescriptorImpl;
import com.easy.query.core.expression.executor.parser.descriptor.impl.TableEntityParseDescriptorImpl;
import com.easy.query.core.expression.executor.parser.descriptor.impl.TablePredicateParseDescriptorImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyParseUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

/**
 * create time 2023/4/9 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPrepareParser implements EasyPrepareParser {
    private TablePredicateParseDescriptor parseQueryDescriptor(PredicatePrepareParseContext predicatePrepareParseContext){
        ExpressionContext expressionContext = predicatePrepareParseContext.getEntityExpressionBuilder().getExpressionContext();
        if(expressionContext.isSharding()){
            TablePredicateParseDescriptor tablePredicateParseDescriptor = new TablePredicateParseDescriptorImpl(new HashMap<>());
            EasyParseUtil.getTablePredicateParseDescriptor(predicatePrepareParseContext.getEntityPredicateSQLExpression(), tablePredicateParseDescriptor);
            return tablePredicateParseDescriptor;
        }
        return DefaultEmptyTableParseDescriptorImpl.INSTANCE;
    }
    private TableEntityParseDescriptor parseEntityDescriptor(EntityParseContext entityPrepareParseContext){
        ExpressionContext expressionContext = entityPrepareParseContext.getEntityExpressionBuilder().getExpressionContext();
        if(expressionContext.isSharding()){
            TableAvailable entityTable = entityPrepareParseContext.getEntityExpressionBuilder().getTable(0).getEntityTable();
            return new TableEntityParseDescriptorImpl(entityTable, entityPrepareParseContext.getEntities());
        }
        return DefaultEmptyTableParseDescriptorImpl.INSTANCE;
    }
    @Override
    public PrepareParseResult parse(PrepareParseContext prepareParseContext) {
        if (prepareParseContext instanceof QueryPredicateParseContext) {
            QueryPredicateParseContext queryPredicatePrepareParseContext = (QueryPredicateParseContext) prepareParseContext;
            TablePredicateParseDescriptor tablePredicateParseDescriptor = parseQueryDescriptor(queryPredicatePrepareParseContext);
            return queryParseResult(queryPredicatePrepareParseContext, tablePredicateParseDescriptor);
        }
        if (prepareParseContext instanceof InsertEntityParseContext) {
            InsertEntityParseContext insertEntityPrepareParseContext = (InsertEntityParseContext) prepareParseContext;
            TableEntityParseDescriptor tableEntityParseDescriptor = parseEntityDescriptor(insertEntityPrepareParseContext);
            return insertParseResult(insertEntityPrepareParseContext, tableEntityParseDescriptor);
        }
        if (prepareParseContext instanceof EntityParseContext) {
            EntityParseContext entityPrepareParseContext = (EntityParseContext) prepareParseContext;
            TableEntityParseDescriptor tableEntityParseDescriptor = parseEntityDescriptor(entityPrepareParseContext);
            return entityParseResult(entityPrepareParseContext, tableEntityParseDescriptor);
        }
        if (prepareParseContext instanceof PredicatePrepareParseContext) {
            PredicatePrepareParseContext predicatePrepareParseContext = (PredicatePrepareParseContext) prepareParseContext;
            TablePredicateParseDescriptor tablePredicateParseDescriptor = parseQueryDescriptor(predicatePrepareParseContext);
            return predicatePrepareParseResult(predicatePrepareParseContext, tablePredicateParseDescriptor);
        }
        throw new NotImplementedException();
    }

    private QueryPrepareParseResult queryParseResult(QueryPredicateParseContext queryPredicatePrepareParseContext, TablePredicateParseDescriptor tablePredicateParseDescriptor) {
        return new EasyQueryPrepareParseResult(queryPredicatePrepareParseContext, tablePredicateParseDescriptor);
    }

    private InsertPrepareParseResult insertParseResult(InsertEntityParseContext insertEntityPrepareParseContext, TableEntityParseDescriptor tableEntityParseDescriptor) {
        return new EasyInsertPrepareParseResult(insertEntityPrepareParseContext, tableEntityParseDescriptor);
    }

    private EntityPrepareParseResult entityParseResult(EntityParseContext entityPrepareParseContext, TableEntityParseDescriptor tableEntityParseDescriptor) {
        return new EasyEntityPrepareParseResult(entityPrepareParseContext, tableEntityParseDescriptor);
    }

    private EasyPredicatePrepareParseResult predicatePrepareParseResult(PredicatePrepareParseContext predicatePrepareParseContext, TablePredicateParseDescriptor tablePredicateParseDescriptor) {
        return new EasyPredicatePrepareParseResult(predicatePrepareParseContext, tablePredicateParseDescriptor);
    }


}
