package com.easy.query.core.expression.predicate;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2025/9/8 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSmartPredicateUnit implements SmartPredicateUnit {
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable fromTable;
    protected final Map<String, SmartPredicateItem> aliasMap;

    public AbstractSmartPredicateUnit(EntityQueryExpressionBuilder entityQueryExpressionBuilder, TableAvailable fromTable, Map<String, SmartPredicateItem> aliasMap) {
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.fromTable = fromTable;
        this.aliasMap = aliasMap;
    }

    protected boolean predicateParseMatch(SmartPredicateItem smartPredicateItem){
        return true;
    }

    protected SmartPredicateParseResult parsePredicate(Predicate predicate) {
        SmartPredicateItem smartPredicateItem = getTargetPropertyName(predicate);
        if (smartPredicateItem != null && predicateParseMatch(smartPredicateItem)) {
            if (predicate.getOperator() == SQLPredicateCompareEnum.EQ) {
                if (predicate instanceof ValuePredicate) {
                    ValuePredicate valuePredicate = (ValuePredicate) predicate;
                    SQLParameter parameter = valuePredicate.getParameter();
                    if (parameter instanceof ConstSQLParameter) {
                        Object value = parameter.getValue();
                        return new SmartPredicateParseResult(smartPredicateItem, f -> f.eq(smartPredicateItem.table, smartPredicateItem.property, value));
                    }
                }
            } else if (predicate.getOperator() == SQLPredicateCompareEnum.IN) {
                if (predicate instanceof ValuesPredicate) {
                    ValuesPredicate valuesPredicate = (ValuesPredicate) predicate;
                    Collection<SQLParameter> parameters = valuesPredicate.getParameters();
                    List<Object> values = new ArrayList<>();
                    for (SQLParameter parameter : parameters) {
                        if (parameter instanceof ConstSQLParameter) {
                            Object value = parameter.getValue();
                            values.add(value);
                        }
                    }
                    if (EasyCollectionUtil.isNotEmpty(values)) {
                        return new SmartPredicateParseResult(smartPredicateItem, f -> f.in(smartPredicateItem.table, smartPredicateItem.property, values));
                    }
                }
            }
        }
        return null;
    }

    protected SmartPredicateItem getTargetPropertyName(Predicate predicate) {
        if (predicate.getTable() == fromTable &&
                (predicate.getOperator() == SQLPredicateCompareEnum.EQ || predicate.getOperator() == SQLPredicateCompareEnum.IN)) {
            String aliasName = getAliasName(predicate);
            return aliasMap.get(aliasName);
        }
        return null;
    }

    private String getAliasName(Predicate predicate) {
        if (predicate.getTable() != null) {
            EntityMetadata entityMetadata = predicate.getTable().getEntityMetadata();
            ColumnMetadata columnOrNull = entityMetadata.getColumnOrNull(predicate.getPropertyName());
            if (columnOrNull != null) {
                return columnOrNull.getName();
            }
        }
        return predicate.getPropertyName();
    }

    protected boolean appendTableJoinOnPredicate(EntityTableExpressionBuilder tableExpressionBuilder, SmartPredicateParseResult predicateSQLAction) {
        TableAvailable parseTable = predicateSQLAction.smartPredicateItem.table;
        if (tableExpressionBuilder.getEntityTable() == parseTable) {
            FilterImpl onFilter = new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), tableExpressionBuilder.getOn(), false, entityQueryExpressionBuilder.getExpressionContext().getValueFilter());
            predicateSQLAction.filterAction.apply(onFilter);
            return true;
        }
        return false;
    }
}
