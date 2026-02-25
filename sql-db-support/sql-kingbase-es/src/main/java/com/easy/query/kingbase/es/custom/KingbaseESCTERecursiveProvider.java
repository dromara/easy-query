package com.easy.query.kingbase.es.custom;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.extension.cte.CTERecursiveProvider;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEOption;

/**
 * create time 2026/2/25 08:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESCTERecursiveProvider implements CTERecursiveProvider {
    @Override
    public <T> ClientQueryable<T> createCteQueryable(ClientQueryable<T> fromQueryable,ClientQueryable<T> queryable, TreeCTEOption treeCTEOption, String[] codeProperties, String[] parentCodeProperties) {
        String cteTableName = treeCTEOption.getCTETableName();
        String deepColumnName = treeCTEOption.getDeepColumnName();
        boolean up = treeCTEOption.isUp();
        SQLActionExpression1<WherePredicate<?>> childFilter = treeCTEOption.getChildFilter();
        Class<T> thisQueryClass = queryable.queryClass();
        ClientQueryable<T> cteQueryable = getCTEJoinQueryable(fromQueryable,queryable, cteTableName, codeProperties, parentCodeProperties, up)
                .where(childFilter != null, (parent, child) -> {
                    assert childFilter != null;
                    childFilter.apply(child);
                })
                .select(thisQueryClass, (parent, child) -> {
                    parent.sqlNativeSegment("{0} + 1", c -> c.columnName(deepColumnName).setAlias(deepColumnName));
                    child.columnAll();
                });

        fromQueryable.select(o -> o.sqlNativeSegment("0+0", c -> c.setAlias(deepColumnName)).columnAll());
        return cteQueryable;
    }



    private <T> ClientQueryable2<T, T> getCTEJoinQueryable(ClientQueryable<T> fromQueryable,ClientQueryable<T> queryable, String cteTableName, String[] codeProperties, String[] parentCodeProperties, boolean up) {

        Class<T> thisQueryClass = fromQueryable.queryClass();

        return queryable.asTable(cteTableName)
                .innerJoin(thisQueryClass, (t, t1) -> {
                    if (up) {
                        t1.multiEq(true, t, codeProperties, parentCodeProperties);
                    } else {
                        t1.multiEq(true, t, parentCodeProperties, codeProperties);
                    }
                });
    }
}
