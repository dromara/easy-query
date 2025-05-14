package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

/**
 * create time 2023/12/8 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetNativeSQLImpl implements SQLColumnSetExpression {

    private final TableAvailable table;
    private final String property;
    private final String sqlSegment;
    private final SQLActionExpression1<SQLNativeProxyExpressionContext> contextConsume;

    public SQLColumnSetNativeSQLImpl(TableAvailable table, String property, String sqlSegment, SQLActionExpression1<SQLNativeProxyExpressionContext> contextConsume){
        this.table = table;
        this.property = property;
        this.sqlSegment = sqlSegment;
        this.contextConsume = contextConsume;
    }

    @Override
    public void accept(Setter s) {
        s.sqlNativeSegment(table, property, sqlSegment, c->{
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
        });
    }

    @Override
    public void accept(Selector s) {
        s.sqlNativeSegment(sqlSegment, c->{
            c.setPropertyAlias(property);
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
        });
    }

    @Override
    public void accept(AsSelector s) {
        s.sqlNativeSegment(sqlSegment, c->{
            c.setPropertyAlias(property);
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
        });
    }

}
