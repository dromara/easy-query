package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/16 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFetcher implements Fetcher {
    private SQLSelectAsExpression sqlSelectExpression = SQLSelectAsExpression.empty;

    @Override
    public Fetcher fetch(SQLSelectAsExpression... selectAsExpressions) {
        if (EasyArrayUtil.isNotEmpty(selectAsExpressions)) {
            for (SQLSelectAsExpression selectAsExpression : selectAsExpressions) {
                sqlSelectExpression = sqlSelectExpression._concat(selectAsExpression);
            }
        }
        return this;
    }

    @Override
    public Fetcher fetch(SQLSelectExpression... selectExpressions) {
        if (EasyArrayUtil.isNotEmpty(selectExpressions)) {
            for (SQLSelectExpression selectExpression : selectExpressions) {
                sqlSelectExpression = new SQLSelectAsImpl(x -> {
                    accept(x);
                    selectExpression.accept(x);
                }, x -> {
                    accept(x);
                    selectExpression.accept(x);
                },x->{
                    throw  new UnsupportedOperationException();
                });
            }
        }
        return this;
    }

    @Override
    public void accept(GroupSelector s) {
        sqlSelectExpression.accept(s);
    }

    @Override
    public void accept(AsSelector s) {
        sqlSelectExpression.accept(s);
    }

    @Override
    public void accept(Selector s) {
        sqlSelectExpression.accept(s);
    }
}
