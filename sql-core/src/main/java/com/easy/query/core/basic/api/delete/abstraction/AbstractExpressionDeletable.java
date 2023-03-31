package com.easy.query.core.basic.api.delete.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSqlExecuteRows;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.EntityDeleteExpression;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;

import java.util.Collection;
import java.util.function.Function;

/**
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 * @author xuejiaming
 */
public abstract   class AbstractExpressionDeletable<T> extends AbstractSqlExecuteRows implements ExpressionDeletable<T> {
    protected final Class<T> clazz;
    protected final EasyEntityTableExpression table;
    protected final EntityDeleteExpression sqlEntityDeleteExpression;

    public AbstractExpressionDeletable(Class<T> clazz, EntityDeleteExpression sqlEntityDeleteExpression){
        super(sqlEntityDeleteExpression);
        this.sqlEntityDeleteExpression = sqlEntityDeleteExpression;

        this.clazz = clazz;
        EntityMetadata entityMetadata = this.sqlEntityDeleteExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = new EasyEntityTableExpression(entityMetadata,  0,null, MultiTableTypeEnum.FROM);
        this.sqlEntityDeleteExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public long executeRows() {
        String deleteSql = toSql();
        if(StringUtil.isNotBlank(deleteSql)){
            EasyQueryRuntimeContext runtimeContext = sqlEntityDeleteExpression.getRuntimeContext();
            EasyExecutor easyExecutor = runtimeContext.getEasyExecutor();
            return easyExecutor.executeRows(ExecutorContext.create(runtimeContext), deleteSql, sqlEntityDeleteExpression.getParameters());
        }
        return 0;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if(rows!=expectRows){
            throw new EasyQueryConcurrentException(msg,code);
        }
    }

    @Override
    public ExpressionDeletable<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression) {
        if(condition){
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, sqlEntityDeleteExpression, sqlEntityDeleteExpression.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<T> withVersion(boolean condition, Object versionValue) {
        if(condition){
            sqlEntityDeleteExpression.getExpressionContext().setVersion(versionValue);
        }
        return this;
    }

    @Override
    public Deletable<T, ExpressionDeletable<T>> whereById(Object id) {

        PredicateSegment where = sqlEntityDeleteExpression.getWhere();
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
                .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SqlPredicateCompareEnum.EQ, sqlEntityDeleteExpression));
        where.addPredicateSegment(andPredicateSegment);
        return this;
    }

    @Override
    public ExpressionDeletable<T> useLogicDelete(boolean enable) {
        sqlEntityDeleteExpression.setLogicDelete(enable);
        return this;
    }

    @Override
    public ExpressionDeletable<T> allowDeleteCommand(boolean allow) {
        sqlEntityDeleteExpression.getExpressionContext().deleteThrow(!allow);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asTable(Function<String, String> tableNameAs) {
        sqlEntityDeleteExpression.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }
}
