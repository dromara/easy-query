package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * @Date: 2023/2/14 23:34
 */
public class ColumnMultiCollectionPredicate implements ValuesPredicate, ShardingPredicate {
    private final List<List<Object>> collections;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;
    private final TableAvailable table;
    private final String[] propertyNames;

    public ColumnMultiCollectionPredicate(TableAvailable table, String[] propertyNames, List<List<Object>> collections, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.table = table;
        this.propertyNames = propertyNames;
        this.collections = collections;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        if (EasyCollectionUtil.isEmpty(collections)) {
            if (SQLPredicateCompareEnum.IN == compare) {
                return "1 = 2";//FALSE  oracle等数据库不支持
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            ArrayList<ColumnMetadata> columnMetadataList = new ArrayList<>(propertyNames.length);
            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];
                ColumnMetadata columnMetadata = this.table.getEntityMetadata().getColumnNotNull(propertyName);
//                String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,true,false);
                columnMetadataList.add(columnMetadata);
            }
            StringBuilder sql = new StringBuilder();
            sql.append("(");
            int i=0;
            for (List<Object> collection : collections) {
                if(i!=0){
                    sql.append(" ").append(SQLKeywordEnum.OR.toSQL()).append(" ");
                }
                appendProperty(columnMetadataList,sql,toSQLContext,collection);
                i++;
            }
            sql.append(")");
            return sql.toString();
        }
    }

    private void appendProperty(List<ColumnMetadata> columnMetadataList, StringBuilder sql, ToSQLContext toSQLContext, List<Object> vals) {
        sql.append("(");
        int i = 0;
        for (ColumnMetadata columnMetadata : columnMetadataList) {
            String propertyName = columnMetadata.getPropertyName();
            String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext, true, false);
            Object val = vals.get(i);
            EasyConstSQLParameter easyConstSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
            if(i!=0){
                sql.append(" ").append(SQLKeywordEnum.AND.toSQL()).append(" ");
            }
            sql.append(sqlColumnSegment).append(" ").append(SQLPredicateCompareEnum.EQ.getSQL())
                    .append(getSQLParameterSegment(easyConstSQLParameter, columnMetadata, toSQLContext));
            i++;
        }
        sql.append(")");
    }

    private String getSQLParameterSegment(SQLParameter sqlParameter, ColumnMetadata columnMetadata, ToSQLContext toSQLContext) {
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if (columnValueSQLConverter == null) {
            EasySQLUtil.addParameter(toSQLContext, sqlParameter);
            return "?";
        } else {
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, expressionContext);
            columnValueSQLConverter.valueConvert(table, columnMetadata, sqlParameter, sqlPropertyConverter, expressionContext.getRuntimeContext(), true);
            return sqlPropertyConverter.toSQL(toSQLContext);
        }
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {

        return new ColumnMultiCollectionPredicate(table, propertyNames, collections, compare, expressionContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Collection<SQLParameter> getParameters() {
        throw new UnsupportedOperationException();
    }
}
