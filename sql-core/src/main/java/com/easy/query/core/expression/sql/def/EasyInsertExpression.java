package com.easy.query.core.expression.sql.def;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.parser.impl.DefaultInsertSqlColumnSelector;
import com.easy.query.core.expression.sql.internal.AbstractEntityExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.util.ClassUtil;

/**
 * @FileName: EasySqlInsertExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:49
 * @author xuejiaming
 */
public abstract class EasyInsertExpression extends AbstractEntityExpression implements EntityInsertExpression {
    private final SqlBuilderSegment columns;

    public EasyInsertExpression(ExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.columns = new ProjectSqlBuilderSegment();
    }

    @Override
    public SqlBuilderSegment getColumns() {
        return columns;
    }


    @Override
    public String toSql() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        EntityTableExpression table = getTable(0);
        EntityMetadata entityMetadata = table.getEntityMetadata();
        if (getColumns().isEmpty()) {
            DefaultInsertSqlColumnSelector<?> columnSelector = new DefaultInsertSqlColumnSelector<>(0, this, this.getColumns());
            columnSelector.columnAll();
            getColumns().getSqlSegments().removeIf(o -> {
                if (o instanceof SqlEntitySegment) {
                    SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) o;
                    String propertyName = sqlEntitySegment.getPropertyName();
                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                    return columnMetadata.isIncrement()||columnMetadata.isInsertIgnore();
                }
                return false;
            });
        }

        int insertColumns = getColumns().getSqlSegments().size();
        if (insertColumns == 0) {
            throw new EasyQueryException("not found insert columns :"+ ClassUtil.getSimpleName(table.getEntityClass()));
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        String tableName = entityMetadata.getTableName();
        sql.append(tableName).append(" (").append(getColumns().toSql()).append(") VALUES (");
        sql.append("?");
        for (int i = 0; i < insertColumns - 1; i++) {
            sql.append(",?");
        }
        sql.append(") ");
        return sql.toString();
    }
}
