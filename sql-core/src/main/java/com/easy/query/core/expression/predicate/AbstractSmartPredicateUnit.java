package com.easy.query.core.expression.predicate;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.metadata.ColumnMetadata;
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
public abstract class AbstractSmartPredicateUnit implements SmartPredicateUnit{
    protected final TableAvailable fromTable;
    private final Map<String, SmartPredicateItem> aliasMap;

    public AbstractSmartPredicateUnit(TableAvailable fromTable, Map<String, SmartPredicateItem> aliasMap) {
        this.fromTable = fromTable;
        this.aliasMap = aliasMap;
    }

    protected SQLActionExpression1<Filter> parsePredicate(Predicate predicate) {
        SmartPredicateItem smartPredicateItem = getTargetPropertyName(predicate);
        if (smartPredicateItem != null) {
            if (predicate.getOperator() == SQLPredicateCompareEnum.EQ) {
                if (predicate instanceof ValuePredicate) {
                    ValuePredicate valuePredicate = (ValuePredicate) predicate;
                    SQLParameter parameter = valuePredicate.getParameter();
                    if (parameter instanceof ConstSQLParameter) {
                        Object value = parameter.getValue();
                        return f -> f.eq(smartPredicateItem.table,smartPredicateItem.property, value);
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
                        return f -> f.in(smartPredicateItem.table,smartPredicateItem.property, values);
                    }
                }
            }
        }
        return null;
    }
    protected SmartPredicateItem getTargetPropertyName(Predicate predicate) {
        if (predicate.getTable() == fromTable &&
                (predicate.getOperator() == SQLPredicateCompareEnum.EQ || predicate.getOperator() == SQLPredicateCompareEnum.IN)) {
            return aliasMap.get(predicate.getPropertyName());
        }
        return null;
    }
}
