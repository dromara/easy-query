package com.easy.query.api.proxy.extension.tree;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/10/23 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityTreeCTEConfigurerImpl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements EntityTreeCTEConfigurer<T1Proxy, T1> {

    private final T1Proxy t1Proxy;
    private final TreeCTEConfigurer treeCTEConfigurer;

    public EntityTreeCTEConfigurerImpl(T1Proxy t1Proxy,TreeCTEConfigurer treeCTEConfigurer){
        this.t1Proxy = t1Proxy;
        this.treeCTEConfigurer = treeCTEConfigurer;
    }
    @Override
    public void setLimitDeep(int limitDeep) {
        treeCTEConfigurer.setLimitDeep(limitDeep);
    }

    @Override
    public void setUp(boolean up) {
        treeCTEConfigurer.setUp(up);
    }

    @Override
    public void setUnionAll(boolean unionAll) {
        treeCTEConfigurer.setUnionAll(unionAll);
    }

    @Override
    public void setCTETableName(String cteTableName) {
        treeCTEConfigurer.setCTETableName(cteTableName);
    }

    @Override
    public void setDeepColumnName(String deepColumnName) {
        treeCTEConfigurer.setDeepColumnName(deepColumnName);
    }

    @Override
    public void setChildFilter(SQLExpression1<T1Proxy> whereExpression) {
        treeCTEConfigurer.setChildFilter(s->{
            t1Proxy.getEntitySQLContext()._where(s.getFilter(), () -> {
                whereExpression.apply(t1Proxy.create(s.getTable(),t1Proxy.getEntitySQLContext()));
            });
        });
    }
}
