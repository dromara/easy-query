package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLAssertPredicate<TProperty> extends TablePropColumn {
    default SQLPredicateExpression isNull() {
        return isNull(true);
    }

    default SQLPredicateExpression isNull(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> f.isNull(this.getTable(), this.getValue()));
        }
        return SQLPredicateExpression.empty;
    }
    default SQLPredicateExpression isNotNull() {
        return isNotNull(true);
    }

    default SQLPredicateExpression isNotNull(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> f.isNotNull(this.getTable(), this.getValue()));
        }
        return SQLPredicateExpression.empty;
    }
}
