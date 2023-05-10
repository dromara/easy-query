package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.sql.dialect.Dialect;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.SqlExpressionUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.function.Function;


/**
 * create time 2023/4/22 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableSqlExpression implements EasyTableSqlExpression {

    protected final MultiTableTypeEnum multiTableType;
    private final EasyQueryRuntimeContext runtimeContext;
    private final Dialect dialect;
    private final TableAvailable entityTable;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;

    public TableSqlExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EasyQueryRuntimeContext runtimeContext) {
        this.entityTable = entityTable;
        this.multiTableType = multiTableType;
        this.runtimeContext = runtimeContext;
        this.dialect = runtimeContext.getEasyQueryConfiguration().getDialect();
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityTable.getEntityMetadata();
    }

    @Override
    public int getIndex() {
        return entityTable.getIndex();
    }

    @Override
    public TableAvailable getEntityTable() {
        return entityTable;
    }

    @Override
    public String getAlias() {
        return entityTable.getAlias();
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
    @Override
    public String getTableName() {
        return dialect.getQuoteName(doGetTableName());
    }
    public String doGetTableName() {
        String tableName = entityTable.getTableName();
        if (tableName == null) {
            throw new EasyQueryException("table " + ClassUtil.getSimpleName(entityTable.getEntityClass()) + " cant found mapping table name");
        }
        if(tableNameAs!=null){
            return tableNameAs.apply(tableName);
        }
        return tableName;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SqlExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
        //如果当前对象没有映射到表那么直接抛错
        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append(getTableName());
        if (getAlias() != null) {
            sql.append(" ").append(getAlias());
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
    public EasyTableSqlExpression cloneSqlExpression() {
        TableSqlExpression tableSqlExpression = new TableSqlExpression(entityTable, multiTableType,runtimeContext);
        if(SqlSegmentUtil.isNotEmpty(on)){
            PredicateSegment predicateSegment = on.clonePredicateSegment();
            tableSqlExpression.setOn(predicateSegment);
        }
        return tableSqlExpression;
    }
}
