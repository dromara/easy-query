package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;

/**
 * create time 2023/12/27 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public class SQLColumnIncludeColumnImpl<TProperty, TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TSourcePropertyProxy extends ProxyEntity<TSourcePropertyProxy, TSourceProperty>, TSourceProperty extends ProxyEntityAvailable<TSourceProperty, TSourcePropertyProxy>> implements SQLColumnSetExpression {
    private final TableAvailable table;
    private final String selfProperty;
    private final String aliasProperty;
    private final TSourcePropertyProxy tSourceProxyProxy;
    private final SQLFuncExpression1<TSourcePropertyProxy, TPropertyProxy> navigateSelectExpression;

    public SQLColumnIncludeColumnImpl(TableAvailable table,  String selfProperty,String aliasProperty, TSourcePropertyProxy tSourceProxyProxy, SQLFuncExpression1<TSourcePropertyProxy, TPropertyProxy> navigateSelectExpression) {
        this.table = table;
        this.selfProperty = selfProperty;
        this.aliasProperty = aliasProperty;
        this.tSourceProxyProxy = tSourceProxyProxy;
        this.navigateSelectExpression = navigateSelectExpression;
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
            if(navigateSelectExpression!=null){
                TSourcePropertyProxy newEntityProxy = tSourceProxyProxy.create(entityTable,is.getEntityQueryExpressionBuilder(), is.getRuntimeContext());
                TPropertyProxy apply = navigateSelectExpression.apply(newEntityProxy);
                SQLSelectAsExpression selectAsExpression = apply.getEntitySQLContext().getSelectAsExpression();
                if (selectAsExpression == null) {//全属性映射
                    is.columnAll(entityTable);
                } else {
                    selectAsExpression.accept(is);
                }
            }else{
                is.columnAll(entityTable);
            }
        });
    }
}
