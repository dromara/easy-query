package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.IncludeRelationIdAvailable;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2024/4/25 08:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyIncludeUtil {

//    public static <TR> List<TR> queryableGroupExecute(int queryRelationGroupSize, ClientQueryable<?> includeQueryable, IncludeRelationIdAvailable includeRelationIdAvailable, List<List<Object>> relationIds, SQLFuncExpression1<ClientQueryable<?>, List<TR>> produce) {
////        if(includeQueryable.getSQLEntityExpressionBuilder().hasLimit()){
////            includeNavigateParams.setLimit(true);
////        }
//        return queryableExpressionGroupExecute(queryRelationGroupSize, () -> includeQueryable, includeRelationIdAvailable, relationIds, produce);
//    }

    public static <TR> List<TR> queryableExpressionGroupExecute(int queryRelationGroupSize, SQLFuncExpression<ClientQueryable<?>> includeQueryableExpression, IncludeRelationIdAvailable includeRelationIdAvailable, List<List<Object>> relationIds, SQLFuncExpression1<ClientQueryable<?>, List<TR>> produce) {
//        int queryRelationGroupSize = includeNavigateParams.getQueryRelationGroupSize(easyQueryOption.getRelationGroupSize());

        if (relationIds.size() <= queryRelationGroupSize) {
            includeRelationIdAvailable.getRelationIds().addAll(relationIds);
            return executeQueryableAndClearParams(includeQueryableExpression.apply(), includeRelationIdAvailable, produce);
        } else {
            ArrayList<TR> result = new ArrayList<>(relationIds.size());
            int i = 0;
            for (List<Object> relationId : relationIds) {
                i++;
                includeRelationIdAvailable.getRelationIds().add(relationId);
                if ((i % queryRelationGroupSize) == 0) {
                    List<TR> r = executeQueryableAndClearParams(includeQueryableExpression.apply(), includeRelationIdAvailable, produce);
                    result.addAll(r);
                }
            }
            if (EasyCollectionUtil.isNotEmpty(includeRelationIdAvailable.getRelationIds())) {
                List<TR> r = executeQueryableAndClearParams(includeQueryableExpression.apply(), includeRelationIdAvailable, produce);
                result.addAll(r);
            }
            return result;
        }
    }

    private static  <T> List<T> executeQueryableAndClearParams(ClientQueryable<?> mappingQueryable, IncludeRelationIdAvailable includeRelationIdAvailable, SQLFuncExpression1<ClientQueryable<?>, List<T>> produce) {
        List<T> result = produce.apply(mappingQueryable);
        includeRelationIdAvailable.getRelationIds().clear();
        return result;
    }
}
