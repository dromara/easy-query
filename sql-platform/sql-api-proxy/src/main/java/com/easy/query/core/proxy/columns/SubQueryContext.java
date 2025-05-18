package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2025/3/11 21:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubQueryContext<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final EntitySQLContext entitySQLContext;
    //    private final EntitySQLContext manyJoinEntitySQLContext;
    private final TableAvailable leftTable;
    private final String property;
    private final String fullName;
    private final T1Proxy propertyProxy;
    private final ProxyEntity<?,?> leftTableProxy;
    private SQLActionExpression1<T1Proxy> whereExpression;
    private SQLActionExpression1<T1Proxy> orderByExpression;
    private SQLActionExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression;
    private boolean distinct;
    private int fromIndex;
    private int toIndex;

    public SubQueryContext(EntityExpressionBuilder entityExpressionBuilder, EntitySQLContext entitySQLContext, TableAvailable leftTable, String property, String fullName, T1Proxy propertyProxy,ProxyEntity<?,?> leftTableProxy) {
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.entitySQLContext = entitySQLContext;
        this.leftTable = leftTable;
        this.property = property;
        this.fullName = fullName;
        this.propertyProxy = propertyProxy;
        this.leftTableProxy = leftTableProxy;
        this.distinct = false;
        this.fromIndex = -1;
        this.toIndex = -1;
//        this.manyJoinEntitySQLContext = new ProxyEntitySQLContext(anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder(), anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder().getRuntimeContext());

    }

    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }

    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

//    public EntitySQLContext getManyJoinEntitySQLContext() {
//        return manyJoinEntitySQLContext;
//    }


    public ProxyEntity<?,?> getLeftTableProxy() {
        return leftTableProxy;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return entityExpressionBuilder.getRuntimeContext();
    }

    public String getProperty() {
        return property;
    }

    public T1Proxy getPropertyProxy() {
        return propertyProxy;
    }

    public String getFullName() {
        return fullName;
    }

    public TableAvailable getLeftTable() {
        return leftTable;
    }

    public SQLActionExpression1<T1Proxy> getWhereExpression() {
        return whereExpression;
    }

    public SQLActionExpression1<T1Proxy> getOrderByExpression() {
        return orderByExpression;
    }

    public SQLActionExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> getConfigureExpression() {
        return configureExpression;
    }

    public void setWhereExpression(SQLActionExpression1<T1Proxy> whereExpression) {
        this.whereExpression = whereExpression;
    }

    public void setOrderByExpression(SQLActionExpression1<T1Proxy> orderByExpression) {
        this.orderByExpression = orderByExpression;
    }

    public void setConfigureExpression(SQLActionExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression) {
        this.configureExpression = configureExpression;
    }
    //    public AnonymousManyJoinEntityTableExpressionBuilder getAnonymousManyJoinEntityTableExpressionBuilder() {
//        return anonymousManyJoinEntityTableExpressionBuilder;
//    }

    public void appendWhereExpression(SQLActionExpression1<T1Proxy> whereExpression) {
        if (whereExpression != null) {
            if (this.whereExpression == null) {
                this.whereExpression = whereExpression;
            } else {
                SQLActionExpression1<T1Proxy> preWhereExpression = this.whereExpression;
                this.whereExpression = o -> {
                    preWhereExpression.apply(o);
                    whereExpression.apply(o);
                };
            }
        }
    }

    public void appendOrderByExpression(SQLActionExpression1<T1Proxy> orderByExpression) {
        if (orderByExpression != null) {
            if (this.orderByExpression == null) {
                this.orderByExpression = orderByExpression;
            } else {
                SQLActionExpression1<T1Proxy> preOrderByExpression = this.orderByExpression;
                this.orderByExpression = o -> {
                    preOrderByExpression.apply(o);
                    orderByExpression.apply(o);
                };
            }
        }
    }

    public void appendConfigureExpression(SQLActionExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression) {
        if (configureExpression != null) {
            if (this.configureExpression == null) {
                this.configureExpression = configureExpression;
            } else {
                SQLActionExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> preConfigureExpression = this.configureExpression;
                this.configureExpression = o -> {
                    preConfigureExpression.apply(o);
                    configureExpression.apply(o);
                };
            }
        }
    }

    public void distinct(boolean useDistinct) {
        this.distinct = useDistinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void elements(int begin, int end) {
        this.fromIndex = begin;
        this.toIndex = end;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public boolean hasElements() {
        return fromIndex > -1 && toIndex > -1;
    }

//    public void cleanElements() {
//        this.fromIndex = -1;
//        this.toIndex = -1;
//    }

    public long getOffset() {
        if (hasElements()) {
            return fromIndex;
        }
        return 0;
    }

    public long getLimit() {
        if (hasElements()) {
            return (toIndex - fromIndex) + 1;
        }
        return 1;
    }
}
