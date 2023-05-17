package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.SQLExpressionUtil;
import com.easy.query.core.util.SQLSegmentUtil;
import com.easy.query.core.util.StringUtil;

import java.util.function.Function;


/**
 * create time 2023/4/22 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableSQLExpressionImpl implements TableSQLExpression {

    protected final MultiTableTypeEnum multiTableType;
    private final EasyQueryRuntimeContext runtimeContext;
    private final Dialect dialect;
    private final TableAvailable entityTable;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;

    public TableSQLExpressionImpl(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EasyQueryRuntimeContext runtimeContext) {
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
        String tableName = dialect.getQuoteName(doGetTableName());
        String schema = entityTable.getSchema();
        if(StringUtil.isNotBlank(schema)){
            return dialect.getQuoteName(schema)+"."+tableName;
        }
        return tableName;
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
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        SQLExpressionUtil.expressionInvokeRoot(sqlParameterCollector);
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
    public com.easy.query.core.expression.sql.expression.TableSQLExpression cloneSQLExpression() {
        TableSQLExpressionImpl tableSQLExpression = new TableSQLExpressionImpl(entityTable, multiTableType,runtimeContext);
        if(SQLSegmentUtil.isNotEmpty(on)){
            PredicateSegment predicateSegment = on.clonePredicateSegment();
            tableSQLExpression.setOn(predicateSegment);
        }
        return tableSQLExpression;
    }
}
