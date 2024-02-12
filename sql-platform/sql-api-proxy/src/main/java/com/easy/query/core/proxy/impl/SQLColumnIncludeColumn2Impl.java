package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumnSetExpression;

/**
 * create time 2023/12/27 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnIncludeColumn2Impl implements SQLColumnSetExpression {
    private final TableAvailable table;
    private final String selfProperty;
    private final String aliasProperty;
    private final ProxyEntity<?, ?> resultProxy;

    public SQLColumnIncludeColumn2Impl(TableAvailable table, String selfProperty, String aliasProperty, ProxyEntity<?, ?> resultProxy) {
        this.table = table;
        this.selfProperty = selfProperty;
        this.aliasProperty = aliasProperty;
        this.resultProxy = resultProxy;
    }

    @Override
    public void accept(Setter s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Selector s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(AsSelector s) {
        s.columnInclude(table, selfProperty, aliasProperty, is -> {
            TableAvailable entityTable = is.getEntityQueryExpressionBuilder().getTable(0).getEntityTable();
//            SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
//            if (selectAsExpression == null) {//全属性映射
            is.columnAll(entityTable);
//            } else {
//                selectAsExpression.accept(is);
//            }
        });
    }
}
