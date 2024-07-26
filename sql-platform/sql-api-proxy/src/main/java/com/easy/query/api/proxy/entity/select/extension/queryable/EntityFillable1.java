//package com.easy.query.api.proxy.entity.select.extension.queryable;
//
//import com.easy.query.api.proxy.entity.select.EntityQueryable;
//import com.easy.query.core.basic.api.select.ClientQueryable;
//import com.easy.query.core.basic.api.select.Query;
//import com.easy.query.core.expression.lambda.SQLFuncExpression;
//import com.easy.query.core.expression.lambda.SQLFuncExpression2;
//import com.easy.query.core.metadata.ColumnMetadata;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.util.EasyObjectUtil;
//
//import java.util.Collection;
//import java.util.function.BiConsumer;
//
///**
// * create time 2024/7/26 14:16
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface EntityFillable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy, T1> {
//
//
////    @Deprecated
////    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity extends ProxyEntityAvailable<TREntity, TRProxyEntity>, TProperty> EntityQueryable<T1Proxy, T1> fillMany(Class<TREntity> subEntityClass, SQLFuncExpression1<ProxyEntityFill<TRProxyEntity, TREntity>, EntityQueryable<TRProxyEntity, TREntity>> fillSetterExpression, Function<TRProxyEntity, SQLColumn<TRProxyEntity, TProperty>> targetPropertyFunction, Property<T1, TProperty> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
////        TRProxyEntity trProxyEntity = EntityQueryProxyManager.create(subEntityClass);
////        SQLColumn<TRProxyEntity, TProperty> targetProperty = targetPropertyFunction.apply(trProxyEntity);
////        getClientQueryable().fillMany(fillSelector -> {
////            return fillSetterExpression.apply(new ProxyEntityFillImpl<>(fillSelector)).getClientQueryable();
////        }, targetProperty.getValue(), selfProperty, produce);
////        return getQueryable();
////    }
////    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity extends ProxyEntityAvailable<TREntity, TRProxyEntity>, TProperty> EntityQueryable<T1Proxy, T1> fillMany(SQLFuncExpression<ProxyEntityFillResult<TRProxyEntity,TREntity,TProperty,T1>> fillResultExpression,BiConsumer<T1, Collection<TREntity>> produce) {
////        ProxyEntityFillResult<TRProxyEntity, TREntity, TProperty, T1> result = fillResultExpression.apply();
////        getClientQueryable().fillMany(fillSelector -> {
////            ClientQueryable<TREntity> clientQueryable = result.getQueryable().getClientQueryable();
////            return fillSelector.adapter(clientQueryable.queryClass(),clientQueryable);
////        }, result.getTargetSQLColumn().getValue(), result.getSelfProperty(), produce);
////        return getQueryable();
////    }
//
//    /**
//     * <blockquote><pre>
//     * {@code
//     *  .fillMany(() -> {
//     *      return easyEntityQuery.queryable(City.class).where(y -> y.code().eq("3306"));
//     *      },
//     *      (p, c) -> new FillPredicate<>(p.code(), c.provinceCode()),
//     *      (x, y) -> {
//     *           x.setCities(new ArrayList<>(y));
//     *      })
//     *                    }
//     * </pre></blockquote>
//     * @param queryableSQLFuncExpression
//     * @param predicateExpression
//     * @param produce
//     * @return
//     * @param <TRProxyEntity>
//     * @param <TREntity>
//     * @param <TProperty>
//     */
//    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity, TProperty> Query<T1> fillMany(SQLFuncExpression<EntityQueryable<TRProxyEntity, TREntity>> queryableSQLFuncExpression, SQLFuncExpression2<T1Proxy,TRProxyEntity, FillPredicate<TRProxyEntity,TREntity,T1Proxy,T1,TProperty>> predicateExpression, BiConsumer<T1, Collection<TREntity>> produce) {
//        EntityQueryable<TRProxyEntity, TREntity> queryable = queryableSQLFuncExpression.apply();
//        FillPredicate<TRProxyEntity, TREntity, T1Proxy, T1, TProperty> predicate = predicateExpression.apply(getQueryable().get1Proxy(), queryable.get1Proxy());
//        EntityMetadata entityMetadata = queryable.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(getQueryable().queryClass());
//        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(predicate.getSelfSQLColumn().getValue());
//
//        return getClientQueryable().fillMany(fillSelector -> {
//            ClientQueryable<TREntity> clientQueryable = queryable.getClientQueryable();
//            return fillSelector.adapter(clientQueryable.queryClass(),clientQueryable);
//        }, predicate.getTargetSQLColumn().getValue(), EasyObjectUtil.typeCastNullable(columnMetadata.getGetterCaller()), produce);
//    }
//
////    @Deprecated
////    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity extends ProxyEntityAvailable<TREntity, TRProxyEntity>, TProperty> EntityQueryable<T1Proxy, T1> fillOne(Class<TREntity> subEntityClass,SQLFuncExpression1<ProxyEntityFill<TRProxyEntity, TREntity>, EntityQueryable<TRProxyEntity, TREntity>> fillSetterExpression, Function<TRProxyEntity, SQLColumn<TRProxyEntity, TProperty>> targetPropertyFunction, Property<T1, TProperty> selfProperty, BiConsumer<T1, TREntity> produce) {
////        TRProxyEntity trProxyEntity = EntityQueryProxyManager.create(subEntityClass);
////        SQLColumn<TRProxyEntity, TProperty> targetProperty = targetPropertyFunction.apply(trProxyEntity);
////        getClientQueryable().fillOne(fillSelector -> {
////            return fillSetterExpression.apply(new ProxyEntityFillImpl<>(fillSelector)).getClientQueryable();
////        }, targetProperty.getValue(), selfProperty, produce);
////        return getQueryable();
////    }
//
//    /**
//     * <blockquote><pre>
//     * {@code
//     *  .fillOne(() -> {
//     *      return easyEntityQuery.queryable(City.class).where(y -> y.code().eq("3306"));
//     *      },
//     *      (p, c) -> new FillPredicate<>(p.code(), c.provinceCode()),
//     *      (x, y) -> {
//     *           x.setCities(new ArrayList<>(y));
//     *      })
//     *                    }
//     * </pre></blockquote>
//     * @param queryableSQLFuncExpression
//     * @param predicateExpression
//     * @param produce
//     * @return
//     * @param <TRProxyEntity>
//     * @param <TREntity>
//     * @param <TProperty>
//     */
//    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity, TProperty> Query<T1> fillOne(SQLFuncExpression<EntityQueryable<TRProxyEntity, TREntity>> queryableSQLFuncExpression, SQLFuncExpression2<T1Proxy,TRProxyEntity, FillPredicate<TRProxyEntity,TREntity,T1Proxy,T1,TProperty>> predicateExpression, BiConsumer<T1, TREntity> produce) {
//        EntityQueryable<TRProxyEntity, TREntity> queryable = queryableSQLFuncExpression.apply();
//        FillPredicate<TRProxyEntity, TREntity, T1Proxy, T1, TProperty> predicate = predicateExpression.apply(getQueryable().get1Proxy(), queryable.get1Proxy());
//        EntityMetadata entityMetadata = queryable.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(getQueryable().queryClass());
//        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(predicate.getSelfSQLColumn().getValue());
//
//        return getClientQueryable().fillOne(fillSelector -> {
//            ClientQueryable<TREntity> clientQueryable = queryable.getClientQueryable();
//            return fillSelector.adapter(clientQueryable.queryClass(),clientQueryable);
//        }, predicate.getTargetSQLColumn().getValue(), EasyObjectUtil.typeCastNullable(columnMetadata.getGetterCaller()), produce);
//    }
//}
