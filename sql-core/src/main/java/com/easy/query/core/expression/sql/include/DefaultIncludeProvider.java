package com.easy.query.core.expression.sql.include;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyOptionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2025/3/1 18:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultIncludeProvider implements IncludeProvider {

    @Override
    public <TProperty> void include(@Nullable TableAvailable table, EntityMetadata entityMetadata, ExpressionContext expressionContext, SQLFuncExpression1<NavigateInclude, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {

        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        IncludeNavigateParams includeNavigateParams = new IncludeNavigateParams();
        Integer groupSize = expressionContext.getGroupSize();
        Boolean printNavSQL = EasyOptionUtil.isPrintNavSQL(expressionContext);
        EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        int relationGroupSize = groupSize != null ? groupSize : easyQueryOption.getRelationGroupSize();
        includeNavigateParams.setRelationGroupSize(relationGroupSize);
        NavigateInclude navigateInclude = getNavigateInclude(table, entityMetadata, includeNavigateParams, expressionContext);
        ClientQueryable<TProperty> clientQueryable = navigateIncludeSQLExpression.apply(navigateInclude);
        boolean hasLimit = clientQueryable.getSQLEntityExpressionBuilder().hasLimit();
        includeNavigateParams.setLimit(hasLimit);
        NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
        if (!Objects.equals(navigateMetadata.getNavigatePropertyType(), clientQueryable.queryClass())) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " include query navigate error return type should:[" + EasyClassUtil.getSimpleName(navigateMetadata.getNavigatePropertyType()) + "] actual:[" + EasyClassUtil.getSimpleName(clientQueryable.queryClass()) + "]");
        }
        boolean directMapping = EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping());
        SQLFuncExpression<ClientQueryable<?>> queryableExpression = () -> {
            return getIncludeQueryable(navigateMetadata, includeNavigateParams, hasLimit, clientQueryable, printNavSQL, directMapping, runtimeContext);
        };
        boolean replace = navigateInclude.getIncludeNavigateParams().isReplace();
        if (replace) {
            expressionContext.getIncludes().put(includeNavigateParams.getNavigateMetadata(), new IncludeNavigateExpression(includeNavigateParams, queryableExpression));
        } else {
            expressionContext.getIncludes().putIfAbsent(includeNavigateParams.getNavigateMetadata(), new IncludeNavigateExpression(includeNavigateParams, queryableExpression));
        }

    }


    private <TProperty> ClientQueryable<TProperty> getIncludeQueryable(NavigateMetadata navigateMetadata, IncludeNavigateParams includeNavigateParams, boolean hasLimit, ClientQueryable<TProperty> clientQueryable, Boolean printNavSQL, boolean directMapping, QueryRuntimeContext runtimeContext) {
        List<List<Object>> relationIds = includeNavigateParams.getRelationIds();
        if (hasLimit) {
            if (EasyCollectionUtil.isNotEmpty(relationIds) && EasyCollectionUtil.isNotSingle(relationIds)) {
                Iterator<List<Object>> iterator = relationIds.iterator();
                List<Object> firstRelationId = iterator.next();
                ClientQueryable<TProperty> firstQueryable = getRelationLimitQueryable(clientQueryable, navigateMetadata, firstRelationId, directMapping, runtimeContext);
                ArrayList<ClientQueryable<TProperty>> otherQueryable = new ArrayList<>();
                while (iterator.hasNext()) {
                    List<Object> nextRelationId = iterator.next();
                    ClientQueryable<TProperty> nextQueryable = getRelationLimitQueryable(clientQueryable, navigateMetadata, nextRelationId, directMapping, runtimeContext);
                    otherQueryable.add(nextQueryable);
                }
                return firstQueryable.configure(s -> {
                    s.setPrintSQL(printNavSQL);
                    s.setPrintNavSQL(printNavSQL);
                }).unionAll(otherQueryable);
            }
        }
        //                        navigateMetadata.predicateFilterApply(o);
        return clientQueryable.cloneQueryable().configure(s -> {
            s.setPrintSQL(printNavSQL);
            s.setPrintNavSQL(printNavSQL);
        }).where(o -> {
            o.and(() -> {
                if (directMapping) {
                    o.relationIn(navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationIds);
                } else {
                    o.relationIn(navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), relationIds);
                }
                navigateMetadata.predicateFilterApply(o);
//                        navigateMetadata.predicateFilterApply(o);
            });
        });
    }


    private <TProperty> ClientQueryable<TProperty> getRelationLimitQueryable(ClientQueryable<TProperty> clientQueryable, NavigateMetadata navigateMetadata, List<Object> relationId, boolean directMapping, QueryRuntimeContext runtimeContext) {

        ClientQueryable<TProperty> firstQueryable = clientQueryable.cloneQueryable();
        firstQueryable.where(o -> {
            o.and(() -> {
                if (directMapping) {
                    o.multiEq(true, navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationId);
                } else {
                    o.multiEq(true, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), relationId);
                }
                navigateMetadata.predicateFilterApply(o);
            });
        });
        return firstQueryable;
    }

}
