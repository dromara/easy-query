package org.easy.query.core.basic.api.delete;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.context.DeleteContext;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.enums.SqlPredicateCompareEnum;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.ClassUtil;
import org.easy.query.core.util.StringUtil;

import java.util.Collection;

/**
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 * @Created by xuejiaming
 */
public abstract   class AbstractExpressionDelete<T> implements EasyExpressionDelete<T> {
    protected final Class<T> clazz;
    protected final SqlTableInfo table;
    protected final DeleteContext deleteContext;

    public AbstractExpressionDelete(Class<T> clazz, DeleteContext deleteContext){
        this.deleteContext = deleteContext;

        this.clazz = clazz;
        EntityMetadata entityMetadata = deleteContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table=new SqlTableInfo(entityMetadata, null, 0, MultiTableTypeEnum.FROM);
        deleteContext.addSqlTable(table);
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
    public EasyExpressionDelete<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression) {
        if(condition){
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, deleteContext, deleteContext.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public EasyDelete<T> deleteById(Object id) {

        PredicateSegment where = deleteContext.getWhere();
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
                .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SqlPredicateCompareEnum.EQ, deleteContext));
        where.addPredicateSegment(andPredicateSegment);
        return this;
    }
}
