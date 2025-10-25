package com.easy.query.core.proxy.sql;

import com.easy.query.api.proxy.base.MapTypeAvailable;
import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.TypeResultColumnMetadata;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.core.FlatEntitySQLContext;
import com.easy.query.core.proxy.core.draft.proxy.DraftProxy;
import com.easy.query.core.proxy.impl.SQLSelectAs2Impl;
import com.easy.query.core.proxy.part.proxy.PartProxy;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.Objects;

/**
 * create time 2023/12/2 17:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class Select {

    public static SQLSelectExpression of(boolean condition, SQLSelectExpression... selects) {
        if (condition) {
            return of(selects);
        }
        return SQLSelectExpression.empty;
    }

    public static SQLSelectExpression of(SQLSelectExpression... selects) {
        if (EasyArrayUtil.isNotEmpty(selects)) {
            SQLSelectExpression firstSQLSelectAs = selects[0];
            for (int i = 1; i < selects.length; i++) {
                firstSQLSelectAs = firstSQLSelectAs._concat(selects[i]);
            }
            return firstSQLSelectAs;
        }
        return SQLSelectExpression.empty;
    }

    public static SQLSelectAsExpression of(SQLSelectAsExpression... selectAss) {

        if (EasyArrayUtil.isNotEmpty(selectAss)) {
            SQLSelectAsExpression firstSQLSelectAs = selectAss[0];
            if (selectAss.length == 1) {
                return firstSQLSelectAs;
            }
            SQLSelectAsExpression secondSQLSelectAs = selectAss[1];
            SQLSelectAs2Impl resultExpression = new SQLSelectAs2Impl(firstSQLSelectAs, secondSQLSelectAs);
            for (int i = 2; i < selectAss.length; i++) {
                resultExpression = new SQLSelectAs2Impl(resultExpression, selectAss[i]);
            }
            return resultExpression;
        }
        return SQLSelectAsExpression.empty;
    }

    /**
     * 返回元组 Draft1...10
     */
    public static Draft DRAFT = new Draft();
    /**
     * 返回元组 Tuple1...10
     */
    public static Tuple TUPLE = new Tuple();
    public static SQLPart PART = new SQLPart();


    public static <TR, TRProxy> void setDraftPropTypes(ClientQueryable<TR> select, TRProxy trProxy) {
        ExpressionContext expressionContext = select.getSQLEntityExpressionBuilder().getExpressionContext();
        if (trProxy instanceof DraftProxy) {
            DraftProxy draftProxy = (DraftProxy) trProxy;
            expressionContext.setResultPropTypes(draftProxy.getDraftPropTypes());
        } else if (trProxy instanceof MapTypeAvailable) {
            MapTypeAvailable mapTypeAvailable = (MapTypeAvailable) trProxy;
            expressionContext.setResultPropTypes(mapTypeAvailable._getResultPropTypes().entrySet().stream().map(x -> new TypeResultColumnMetadata(x.getKey(), x.getValue())).toArray(ResultColumnMetadata[]::new));
        } else if (trProxy instanceof SQLColumn) {
            SQLColumn sqlColumn = (SQLColumn) trProxy;
            //基本类型支持select(x->x.id())但是枚举类型因为不是基本类型所以需要使用valueConverter
            if (sqlColumn.getTable() != null && Objects.equals(sqlColumn.getPropertyType(), Object.class)) {
                EntityMetadata entityMetadata = sqlColumn.getTable().getEntityMetadata();
                ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(sqlColumn.getValue());
                if (columnMetadata != null) {
                    expressionContext.setResultPropTypes(new EntityResultColumnMetadata[]{new EntityResultColumnMetadata(0, entityMetadata, columnMetadata)});
                }
            }
        } else {
            if (expressionContext.getResultPropTypes() != null) {
                expressionContext.setResultPropTypes(null);
            }
        }
    }

    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectProxy(TRProxy resultProxy, ClientQueryable<?> queryable) {

        Objects.requireNonNull(resultProxy, "select null result class");
        if (resultProxy.getEntitySQLContext() instanceof FlatEntitySQLContext) {
            //[flatElement]方法不允许在select函数里面使用,如果你希望是从[flatElement]函数获取的请在[toList]函数内部使用
            throw new EasyQueryInvalidOperationException("The [flatElement] method is not allowed to be used inside the select function. If you wish to retrieve from the [flatElement] function, please use it within the [toList] function. eg. toList(o->o.users().flatElement()) ");
        }
        QueryRuntimeContext runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
        if (selectAsExpression == null) {//全属性映射
            TableAvailable tableOrNull = resultProxy.getTableOrNull();
            if (tableOrNull == null) {
                ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass());
                Select.setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            } else {
                EntityMetadata resultEntityMetadata = getResultProxyEntityMetadata(resultProxy, runtimeContext);
                if (resultProxy instanceof SQLSelectExpression) {
                    SQLSelectExpression resultProxySelectExpression = (SQLSelectExpression) resultProxy;
                    ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), resultEntityMetadata, columnAsSelector -> {
                        resultProxySelectExpression.accept(columnAsSelector.getAsSelector());
                    });
                    Select.setDraftPropTypes(select, resultProxy);
                    return new EasyEntityQueryable<>(resultProxy, select);
                }
                ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), resultEntityMetadata, columnAsSelector -> {
                    columnAsSelector.getAsSelector().columnAll(tableOrNull);
                });
                Select.setDraftPropTypes(select, resultProxy);
                return new EasyEntityQueryable<>(resultProxy, select);
            }
        } else {
            EntityMetadata resultEntityMetadata = getResultProxyEntityMetadata(resultProxy, runtimeContext);
            ClientQueryable<TR> select = queryable.select(resultProxy.getEntityClass(), resultEntityMetadata, columnAsSelector -> {
                if (resultProxy instanceof PartProxy) {
                    PartProxy resultProxy1 = (PartProxy) resultProxy;
                    resultProxy1.accept(columnAsSelector.getAsSelector());
                }
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            Select.setDraftPropTypes(select, resultProxy);
            return new EasyEntityQueryable<>(resultProxy, select);
        }
    }

    private static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityMetadata getResultProxyEntityMetadata(TRProxy resultProxy, QueryRuntimeContext runtimeContext) {
        EntityMetadata entityMetadata = resultProxy.getEntityMetadata();
        if (entityMetadata != null && entityMetadata.getEntityClass().equals(resultProxy.getEntityClass())) {
            return entityMetadata;
        } else {
            return runtimeContext.getEntityMetadataManager().getEntityMetadata(resultProxy.getEntityClass());
        }
    }


}
