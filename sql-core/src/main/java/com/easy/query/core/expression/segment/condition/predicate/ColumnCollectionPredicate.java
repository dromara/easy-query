package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.SqlUtil;
import com.easy.query.core.util.SqlExpressionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnCollectionPredicate implements ValuesPredicate,ShardingPredicate {
    private final Collection<?> collection;
    private final SqlPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;
    private final TableAvailable table;
    private final String propertyName;

    public ColumnCollectionPredicate(TableAvailable table, String propertyName, Collection<?> collection, SqlPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.collection = collection;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        if (EasyCollectionUtil.isEmpty(collection)) {
            if (SqlPredicateCompareEnum.IN.equals(compare)) {
                return "FALSE";
            } else if (SqlPredicateCompareEnum.NOT_IN.equals(compare)) {
                return "TRUE";
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
            StringBuilder sql = new StringBuilder();
            sql.append(sqlColumnSegment).append(" ").append(compare.getSql()).append(" (");
            Iterator<?> iterator = collection.iterator();
            Object firstVal = iterator.next();
            SqlUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,firstVal));
            sql.append("?");
            while (iterator.hasNext()){
                Object val = iterator.next();
                SqlUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,val));
                sql.append(",?");
            }
            sql.append(")");
            return sql.toString();
        }
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public SqlPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Collection<SQLParameter> getParameters() {
        if (EasyCollectionUtil.isEmpty(collection)){
            return Collections.emptyList();
        }
        ArrayList<SQLParameter> sqlParameters = new ArrayList<>(collection.size());
        for (Object o : collection) {
            sqlParameters.add(new EasyConstSQLParameter(table,propertyName,o));
        }
        return sqlParameters;
    }
}
