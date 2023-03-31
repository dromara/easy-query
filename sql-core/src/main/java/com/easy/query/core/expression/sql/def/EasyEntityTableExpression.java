package com.easy.query.core.expression.sql.def;

import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.LambdaUtil;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: EasyEntityTableExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:31
 */
public class EasyEntityTableExpression implements EntityTableExpression {

    protected final EntityMetadata entityMetadata;
    protected final int index;
    protected final String alias;
    protected final MultiTableTypeEnum multiTableType;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;

    public EasyEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType) {
        this.entityMetadata = entityMetadata;

        this.index = index;
        this.alias = alias;
        this.multiTableType = multiTableType;
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public <T1> String getPropertyName(Property<T1, ?> column) {
        return LambdaUtil.getPropertyName(column);
    }

    @Override
    public String getColumnName(String propertyName) {
        return this.entityMetadata.getColumnName(propertyName);
    }

    @Override
    public String getTableName() {
        String tableName = entityMetadata.getTableName();
        if (tableName == null) {
            throw new EasyQueryException("table " + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " cant found mapping table name");
        }
        if(tableNameAs!=null){
            return tableNameAs.apply(tableName);
        }
        return tableName;
    }

    @Override
    public void setTableNameAs(Function<String, String> tableNameAs) {
        this.tableNameAs = tableNameAs;
    }

    @Override
    public SqlExpression<SqlPredicate<Object>> getLogicDeleteQueryFilterExpression() {
        if (entityMetadata.enableLogicDelete()) {
            return entityMetadata.getLogicDeleteMetadata().getLogicDeleteQueryFilterExpression();
        }
        return null;
    }

    @Override
    public SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression() {
        if (entityMetadata.enableLogicDelete()) {
            return entityMetadata.getLogicDeleteMetadata().getLogicDeletedSqlExpression();
        }
        return null;
    }

    @Override
    public Class<?> entityClass() {
        return entityMetadata.getEntityClass();
    }

    @Override
    public PredicateSegment getOn() {
        if (on == null) {
            on = new AndPredicateSegment(true);
        }
        return on;
    }

    @Override
    public boolean hasOn() {
        return on != null && on.isNotEmpty();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getSelectTableSource() {
        if (MultiTableTypeEnum.LEFT_JOIN.equals(multiTableType)) {
            return " LEFT JOIN ";
        } else if (MultiTableTypeEnum.INNER_JOIN.equals(multiTableType)) {
            return " INNER JOIN ";
        } else if (MultiTableTypeEnum.RIGHT_JOIN.equals(multiTableType)) {
            return " RIGHT JOIN ";
        }
        return " FROM ";
    }

    @Override
    public String toSql() {
        //如果当前对象没有映射到表那么直接抛错
        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append(getTableName());
        if (alias != null) {
            sql.append(" ").append(alias);
        }
        return sql.toString();
    }

}
