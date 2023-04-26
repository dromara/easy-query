package com.easy.query.core.basic.api.delete.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSqlExecuteRows;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.impl.TableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.function.Function;

/**
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 * @author xuejiaming
 */
public abstract   class AbstractExpressionDeletable<T> extends AbstractSqlExecuteRows<ExpressionDeletable<T>> implements ExpressionDeletable<T> {
    protected final Class<T> clazz;
    protected final TableExpressionBuilder table;
    protected final EntityDeleteExpressionBuilder entityDeleteExpressionBuilder;

    public AbstractExpressionDeletable(Class<T> clazz, EntityDeleteExpressionBuilder entityDeleteExpressionBuilder){
        super(entityDeleteExpressionBuilder);
        this.entityDeleteExpressionBuilder = entityDeleteExpressionBuilder;

        this.clazz = clazz;
        EntityMetadata entityMetadata = this.entityDeleteExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = new TableExpressionBuilder(entityMetadata,  0,null, MultiTableTypeEnum.FROM);
        this.entityDeleteExpressionBuilder.addSqlEntityTableExpression(table);
    }

    @Override
    public long executeRows() {
        EasyQueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
        EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
        return entityExpressionExecutor.executeRows(ExecutorContext.create(entityDeleteExpressionBuilder.getRuntimeContext(),true), entityDeleteExpressionBuilder);
    }

//    @Override
//    public void executeRows(long expectRows, String msg, String code) {
//        long rows = executeRows();
//        if(rows!=expectRows){
//            throw new EasyQueryConcurrentException(msg,code);
//        }
//    }

    @Override
    public ExpressionDeletable<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression) {
        if(condition){
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, entityDeleteExpressionBuilder, entityDeleteExpressionBuilder.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<T> withVersion(boolean condition, Object versionValue) {
        if(condition){
            entityDeleteExpressionBuilder.getExpressionContext().setVersion(versionValue);
        }
        return this;
    }

    @Override
    public Deletable<T, ExpressionDeletable<T>> whereById(Object id) {

        PredicateSegment where = entityDeleteExpressionBuilder.getWhere();
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if(keyProperties.isEmpty()){
            throw new EasyQueryException("对象:"+ ClassUtil.getSimpleName(clazz)+"未找到主键信息");
        }
        if(keyProperties.size()>1){
            throw new EasyQueryException("对象:"+ ClassUtil.getSimpleName(clazz)+"存在多个主键");
        }
        String keyProperty = keyProperties.iterator().next();
        AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
        andPredicateSegment
                .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SqlPredicateCompareEnum.EQ, entityDeleteExpressionBuilder));
        where.addPredicateSegment(andPredicateSegment);
        return this;
    }

    @Override
    public ExpressionDeletable<T> useLogicDelete(boolean enable) {
        entityDeleteExpressionBuilder.setLogicDelete(enable);
        return this;
    }

    @Override
    public ExpressionDeletable<T> allowDeleteCommand(boolean allow) {
        entityDeleteExpressionBuilder.getExpressionContext().deleteThrow(!allow);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asTable(Function<String, String> tableNameAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public String toSql() {
        return toSqlWithParam(null);
    }
    private String toSqlWithParam(SqlParameterCollector sqlParameterCollector){
        return entityDeleteExpressionBuilder.toExpression().toSql(sqlParameterCollector);
    }
}
