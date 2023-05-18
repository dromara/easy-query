package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

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
    private final SQLPredicateCompare compare;
    private final QueryRuntimeContext runtimeContext;
    private final TableAvailable table;
    private final String propertyName;

    public ColumnCollectionPredicate(TableAvailable table, String propertyName, Collection<?> collection, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.collection = collection;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        if (EasyCollectionUtil.isEmpty(collection)) {
            if (SQLPredicateCompareEnum.IN.equals(compare)) {
                return "FALSE";
            } else if (SQLPredicateCompareEnum.NOT_IN.equals(compare)) {
                return "TRUE";
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
            StringBuilder sql = new StringBuilder();
            sql.append(sqlColumnSegment).append(" ").append(compare.getSQL()).append(" (");
            Iterator<?> iterator = collection.iterator();
            Object firstVal = iterator.next();
            EasySQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,firstVal));
            sql.append("?");
            while (iterator.hasNext()){
                Object val = iterator.next();
                EasySQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,val));
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
    public SQLEntitySegment cloneSQLEntitySegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public SQLPredicateCompare getOperator() {
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
