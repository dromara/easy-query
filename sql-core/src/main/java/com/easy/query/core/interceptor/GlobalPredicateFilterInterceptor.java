package com.easy.query.core.interceptor;

import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.query.SqlEntityDeleteExpression;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: GlobalQueryFilterInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:25
 * @author xuejiaming
 */
public interface GlobalPredicateFilterInterceptor extends GlobalInterceptor {

    /**
     * 配置
     * @param entityClass
     * @param sqlPredicate
     */
    void configure(Class<?> entityClass, SqlEntityExpression sqlEntityExpression, SqlPredicate<Object> sqlPredicate);
}
