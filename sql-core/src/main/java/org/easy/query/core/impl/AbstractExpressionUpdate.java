package org.easy.query.core.impl;

import org.easy.query.core.basic.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.basic.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.basic.api.ExpressionUpdate;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.basic.expression.parser.impl.DefaultSqlColumnSetter;
import org.easy.query.core.basic.expression.parser.impl.DefaultSqlPredicate;
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
    protected final UpdateContext updateContext;

    public AbstractExpressionUpdate(Class<T> clazz, UpdateContext updateContext){

        this.clazz = clazz;
        this.updateContext = updateContext;
        EntityMetadata entityMetadata = updateContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        updateContext.addSqlTable(new SqlTableInfo(entityMetadata, null, 0, MultiTableTypeEnum.FROM));
    }

    @Override
    public long executeRows() {
        if(!updateContext.getSetColumns().isEmpty()&&!updateContext.getWhere().isEmpty()){

            String updateSql = toSql();
            System.out.println("表达式更新："+updateSql);
            if(StringUtil.isNotBlank(updateSql)){

            }
        }

        return 0;
    }

    @Override
    public void executeRows(Long expectRow, String error) {

    }

    @Override
    public ExpressionUpdate<T> set(boolean condition, SqlExpression<SqlColumnSetter<T>> setExpression) {
        if(condition){
            DefaultSqlColumnSetter<T> columnSetter = new DefaultSqlColumnSetter<>(0, updateContext, updateContext.getSetColumns());
            setExpression.apply(columnSetter);
        }
        return this;
    }

    @Override
    public ExpressionUpdate<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression) {
        if(condition){
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, updateContext, updateContext.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }
}
