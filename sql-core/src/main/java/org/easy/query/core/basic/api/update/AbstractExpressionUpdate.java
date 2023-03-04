package org.easy.query.core.basic.api.update;

import org.easy.query.core.abstraction.EasyExecutor;
import org.easy.query.core.abstraction.ExecutorContext;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.parser.impl.DefaultSqlColumnSetter;
import org.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import org.easy.query.core.expression.context.UpdateContext;
import org.easy.query.core.query.EasyEntityTableExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlEntityUpdateExpression;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.StringUtil;

/**
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 * @Created by xuejiaming
 */
public abstract class AbstractExpressionUpdate<T> implements ExpressionUpdate<T> {
    protected final Class<T> clazz;
    protected final SqlEntityUpdateExpression sqlEntityUpdateExpression;

    public AbstractExpressionUpdate(Class<T> clazz, SqlEntityUpdateExpression sqlEntityUpdateExpression){

        this.clazz = clazz;
        this.sqlEntityUpdateExpression = sqlEntityUpdateExpression;

        EntityMetadata entityMetadata = this.sqlEntityUpdateExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
       SqlEntityTableExpression table = new EasyEntityTableExpression(entityMetadata,  0,null, MultiTableTypeEnum.FROM);
        this.sqlEntityUpdateExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public long executeRows() {
        String updateSql = toSql();
        System.out.println("表达式更新："+updateSql);
        if(StringUtil.isNotBlank(updateSql)){
            EasyExecutor easyExecutor = sqlEntityUpdateExpression.getRuntimeContext().getEasyExecutor();
            return easyExecutor.update(ExecutorContext.create(sqlEntityUpdateExpression.getRuntimeContext()), updateSql, sqlEntityUpdateExpression.getParameters());
        }

        return 0;
    }

    @Override
    public void executeRows(Long expectRow, String error) {

    }

    @Override
    public ExpressionUpdate<T> set(boolean condition, SqlExpression<SqlColumnSetter<T>> setExpression) {
        if(condition){
            DefaultSqlColumnSetter<T> columnSetter = new DefaultSqlColumnSetter<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns());
            setExpression.apply(columnSetter);
        }
        return this;
    }

    @Override
    public ExpressionUpdate<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression) {
        if(condition){
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }
}
