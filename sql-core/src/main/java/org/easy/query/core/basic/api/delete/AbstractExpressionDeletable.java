package org.easy.query.core.basic.api.delete;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.enums.SqlPredicateCompareEnum;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate0;
import org.easy.query.core.query.EasyEntityTableExpression;
import org.easy.query.core.query.SqlEntityDeleteExpression;
import org.easy.query.core.util.ClassUtil;
import org.easy.query.core.util.StringUtil;

import java.util.Collection;

/**
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 * @Created by xuejiaming
 */
public abstract   class AbstractExpressionDeletable<T> implements ExpressionDeletable<T> {
    protected final Class<T> clazz;
    protected final EasyEntityTableExpression table;
    protected final SqlEntityDeleteExpression sqlEntityDeleteExpression;

    public AbstractExpressionDeletable(Class<T> clazz, SqlEntityDeleteExpression sqlEntityDeleteExpression){
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
        System.out.println("表达式删除："+deleteSql);
        if(StringUtil.isBlank(deleteSql)){
//            EasyExecutor easyExecutor = updateContext.getRuntimeContext().getEasyExecutor();
//            return easyExecutor.update(ExecutorContext.create(updateContext.getRuntimeContext()), updateSql, updateContext.getParameters());
        }
        return 0;
    }

    @Override
    public void executeRows(Long expectRow, String error) {

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
    public Deletable<T, ExpressionDeletable<T>> deleteById(Object id) {

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
                .setPredicate(new ColumnValuePredicate0(table, keyProperty, id, SqlPredicateCompareEnum.EQ, sqlEntityDeleteExpression));
        where.addPredicateSegment(andPredicateSegment);
        return this;
    }

    @Override
    public ExpressionDeletable<T> disableLogicDelete() {
        sqlEntityDeleteExpression.setLogicDelete(false);
        return this;
    }
}
