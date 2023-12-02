package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLAssertPredicate<TProperty> extends TablePropColumn {
    default SQLPredicate isNull() {
        return isNull(true);
    }

    default SQLPredicate isNull(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> f.isNull(this.getTable(), this.value()));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate isNotNull() {
        return isNotNull(true);
    }

    default SQLPredicate isNotNull(boolean condition){
        if(condition){
            return new SQLPredicateImpl(f -> f.isNotNull(this.getTable(), this.value()));
        }
        return SQLPredicate.empty;
    }
}
