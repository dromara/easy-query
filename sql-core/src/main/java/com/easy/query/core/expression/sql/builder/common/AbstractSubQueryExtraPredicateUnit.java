package com.easy.query.core.expression.sql.builder.common;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * create time 2025/8/24 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSubQueryExtraPredicateUnit implements SubQueryExtraPredicateUnit {
    protected final TableAvailable fromTable;
    protected final String[] selfProperties;
    protected final String[] targetProperties;

    public AbstractSubQueryExtraPredicateUnit(TableAvailable fromTable, String[] selfProperties, String[] targetProperties) {
        this.fromTable = fromTable;
        this.selfProperties = selfProperties;
        this.targetProperties = targetProperties;
    }

    protected SQLActionExpression1<WherePredicate<Object>> parsePredicate(Predicate predicate) {
        String targetProperty = getTargetPropertyName(predicate);
        if (targetProperty != null) {
            if (predicate.getOperator() == SQLPredicateCompareEnum.EQ) {
                if (predicate instanceof ValuePredicate) {
                    ValuePredicate valuePredicate = (ValuePredicate) predicate;
                    SQLParameter parameter = valuePredicate.getParameter();
                    if (parameter instanceof ConstSQLParameter) {
                        Object value = parameter.getValue();
                        return w -> w.eq(targetProperty, value);
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
                        return w -> w.in(targetProperty, values);
                    }
                }
            }
        }
        return null;
    }

    protected String getTargetPropertyName(Predicate predicate) {
        if (predicate.getTable() == fromTable &&
                (predicate.getOperator() == SQLPredicateCompareEnum.EQ || predicate.getOperator() == SQLPredicateCompareEnum.IN)) {
            int predicateIndex = getPredicateIndex(predicate.getPropertyName());
            if (predicateIndex > -1) {
                return targetProperties[predicateIndex];
            }
        }
        return null;
    }

    private int getPredicateIndex(String compareProperty) {
        for (int i = 0; i < selfProperties.length; i++) {
            String selfProperty = selfProperties[i];
            if (Objects.equals(selfProperty, compareProperty)) {
                return i;
            }
        }
        return -1;
    }
}
