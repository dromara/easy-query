package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
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
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * @Date: 2023/2/14 23:34
 */
public class ColumnRelationCollectionPredicate implements ValuesPredicate, ShardingPredicate {
    private final Supplier<List<List<Object>>> relationIdCreator;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;
    private final TableAvailable table;
    private final String[] propertyNames;

    public ColumnRelationCollectionPredicate(TableAvailable table, String[] propertyNames, Supplier<List<List<Object>>> relationIdCreator, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.table = table;
        this.propertyNames = propertyNames;
        this.relationIdCreator = relationIdCreator;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    private Collection<Object> getSingleFromNestCollection(List<List<Object>> collection) {
        ArrayList<Object> result = new ArrayList<>(collection.size());
        for (List<Object> objects : collection) {
            if (objects == null) {
                throw new EasyQueryInvalidOperationException("nest collection has null element");
            }
            if (objects.size() != 1) {
                throw new EasyQueryInvalidOperationException("nest collection element.size() != 1");
            }
            result.add(EasyCollectionUtil.first(objects));
        }
        return result;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        List<List<Object>> collections = relationIdCreator.get();
        if(propertyNames.length==1){
            Collection<Object> collection = getSingleFromNestCollection(collections);
            return new ColumnCollectionPredicate(table,propertyNames[0],collection,compare,expressionContext).toSQL(toSQLContext);
        }else{
            return new ColumnMultiCollectionPredicate(table,propertyNames,collections,compare,expressionContext).toSQL(toSQLContext);
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
       return new ColumnRelationCollectionPredicate(table,propertyNames,relationIdCreator,compare,expressionContext);
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
