package org.easy.query.core.basic.api.insert;

import org.easy.query.core.abstraction.EasyExecutor;
import org.easy.query.core.abstraction.ExecutorContext;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.parser.impl.DefaultInsertSqlColumnSelector;
import org.easy.query.core.query.EasyEntityTableExpression;
import org.easy.query.core.query.SqlEntityInsertExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractInsert.java
 * @Description: 文件说明
 * @Date: 2023/2/20 12:30
 * @Created by xuejiaming
 */
public abstract class AbstractInsert<T> implements Insert<T> {
    protected final List<T> entities;
    protected final SqlEntityInsertExpression sqlEntityInsertExpression;

    public AbstractInsert(Class<T> clazz,  SqlEntityInsertExpression sqlEntityInsertExpression) {
        this.sqlEntityInsertExpression = sqlEntityInsertExpression;
        this.entities = new ArrayList<>();
        EntityMetadata entityMetadata = this.sqlEntityInsertExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();

        SqlEntityTableExpression table = new EasyEntityTableExpression(entityMetadata,  0,null, MultiTableTypeEnum.FROM);
        this.sqlEntityInsertExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public Insert<T> insert(T entity) {
        if (entity != null) {
            entities.add(entity);
        }
        return this;
    }

//
//    @Override
//    public Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression) {
//        return null;
//    }

    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {
            SqlExpression<SqlColumnSelector<T>> selectExpression= ColumnSelector::columnAll;
            DefaultInsertSqlColumnSelector<T> columnSelector = new DefaultInsertSqlColumnSelector<>(0, sqlEntityInsertExpression, sqlEntityInsertExpression.getColumns());
            selectExpression.apply(columnSelector);
            String insertSql = toSql();
            System.out.println("插入sql："+insertSql);
            if (!StringUtil.isBlank(insertSql)) {
                EasyExecutor easyExecutor = sqlEntityInsertExpression.getRuntimeContext().getEasyExecutor();
                return easyExecutor.insert(ExecutorContext.create(sqlEntityInsertExpression.getRuntimeContext()),insertSql,entities,sqlEntityInsertExpression.getParameters());
            }
        }

        return 0;
    }


    @Override
    public abstract String toSql();
}
