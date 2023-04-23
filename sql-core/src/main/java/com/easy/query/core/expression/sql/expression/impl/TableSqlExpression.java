package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.function.Function;


/**
 * create time 2023/4/22 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableSqlExpression implements EasyTableSqlExpression {

    protected final EntityMetadata entityMetadata;
    protected final int index;
    protected final String alias;
    protected final MultiTableTypeEnum multiTableType;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;

    public TableSqlExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType) {
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
    public int getIndex() {
        return index;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    public Function<String, String> getTableNameAs() {
        return tableNameAs;
    }

    @Override
    public void setTableNameAs(Function<String, String> tableNameAs) {
        this.tableNameAs = tableNameAs;
    }

    @Override
    public boolean tableNameIsAs() {
        return this.tableNameAs!=null;
    }

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
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        //如果当前对象没有映射到表那么直接抛错
        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append(getTableName());
        if (alias != null) {
            sql.append(" ").append(alias);
        }
        return sql.toString();
    }


    @Override
    public PredicateSegment getOn() {
        return on;
    }
    @Override
    public void setOn(PredicateSegment predicateSegment) {
        this.on = predicateSegment;
    }

    @Override
    public EasySqlExpression cloneSqlExpression() {
        TableSqlExpression tableSqlExpression = new TableSqlExpression(entityMetadata, index, alias, multiTableType);
        if(SqlSegmentUtil.isNotEmpty(on)){
            PredicateSegment predicateSegment = on.clonePredicateSegment();
            tableSqlExpression.setOn(predicateSegment);
        }
        return tableSqlExpression;
    }
}
