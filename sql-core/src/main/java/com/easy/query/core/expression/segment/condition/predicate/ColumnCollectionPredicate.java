package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author xuejiaming
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * @Date: 2023/2/14 23:34
 */
public class ColumnCollectionPredicate implements ValuesPredicate, ShardingPredicate {
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
    public String toSQL(ToSQLContext toSQLContext) {
        if (EasyCollectionUtil.isEmpty(collection)) {
            if (SQLPredicateCompareEnum.IN == compare) {
                return "1 = 2";//FALSE  oracle等数据库不支持
            } else if (SQLPredicateCompareEnum.NOT_IN == compare) {
                return "1 = 1";//TRUE  oracle等数据库不支持
            } else {
                throw new UnsupportedOperationException();
            }
        } else {

            ColumnMetadata columnMetadata = this.table.getEntityMetadata().getColumnNotNull(propertyName);

            String sqlColumnSegment = getSQLColumnSegment(columnMetadata,toSQLContext);
            String compareSQL = compare.getSQL();
            StringBuilder sql = new StringBuilder();
            sql.append(sqlColumnSegment).append(" ").append(compareSQL).append(" (");
            Iterator<?> iterator = collection.iterator();
            Object firstVal = iterator.next();
            EasyConstSQLParameter easyConstSQLParameter = new EasyConstSQLParameter(table, propertyName, firstVal);
            sql.append(getSQLParameterSegment(easyConstSQLParameter,columnMetadata,toSQLContext));
            while (iterator.hasNext()) {
                Object val = iterator.next();
                EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
                sql.append(",").append(getSQLParameterSegment(constSQLParameter,columnMetadata,toSQLContext));
            }
            sql.append(")");
            return sql.toString();
        }
    }

    private String getSQLColumnSegment(ColumnMetadata columnMetadata,ToSQLContext toSQLContext){

        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if(columnValueSQLConverter==null){
            return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext, table, propertyName, toSQLContext);
        }

        DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
        columnValueSQLConverter.propertyColumnConvert(table,columnMetadata,sqlPropertyConverter,runtimeContext);
        return sqlPropertyConverter.toSQL(toSQLContext);
    }

    private String getSQLParameterSegment(SQLParameter sqlParameter,ColumnMetadata columnMetadata,ToSQLContext toSQLContext){
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if(columnValueSQLConverter==null){
            EasySQLUtil.addParameter(toSQLContext, sqlParameter);
            return "?";
        }else{
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
            columnValueSQLConverter.valueConvert(table,columnMetadata,sqlParameter,sqlPropertyConverter,runtimeContext);
            return sqlPropertyConverter.toSQL(toSQLContext);
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
    public Predicate cloneSQLColumnSegment() {

       return new ColumnCollectionPredicate(table,propertyName,collection,compare,runtimeContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Collection<SQLParameter> getParameters() {
        if (EasyCollectionUtil.isEmpty(collection)) {
            return Collections.emptyList();
        }
        ArrayList<SQLParameter> sqlParameters = new ArrayList<>(collection.size());
        for (Object o : collection) {
            sqlParameters.add(new EasyConstSQLParameter(table, propertyName, o));
        }
        return sqlParameters;
    }
}
