package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityPager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.executor.PageAble;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2024/9/10 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityPageAble1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends PageAble<T1>, EntityQueryableAvailable<T1Proxy, T1> {
    default <TPageResult, TR> TPageResult toPageSelectResult(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, Query<TR>> selectExpression, EntityPager<T1Proxy, T1, TPageResult> pager) {
        return pager.toResult(selectExpression, this);
    }

    default <TR> EasyPageResult<TR> toPageSelectResult(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, Query<TR>> selectExpression, long pageIndex, long pageSize) {
        return toPageSelectResult(selectExpression, pageIndex, pageSize, -1L);
    }

    default <TR> EasyPageResult<TR> toPageSelectResult(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, Query<TR>> selectExpression, long pageIndex, long pageSize, long pageTotal) {

        EntityQueryable<T1Proxy, T1> entityQueryable = getQueryable();
        //设置每次获取多少条
        long take = pageSize <= 0 ? 1 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        long total = pageTotal < 0 ? entityQueryable.cloneQueryable().count() : pageTotal;
        QueryRuntimeContext runtimeContext = entityQueryable.getRuntimeContext();

        EasyPageResultProvider easyPageResultProvider = runtimeContext.getEasyPageResultProvider();
        if (total <= offset) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList());
        }//获取剩余条数
        long remainingCount = total - offset;
        //当剩余条数小于take数就取remainingCount
        long realTake = Math.min(remainingCount, take);
        if (realTake <= 0) {
            return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, EasyCollectionUtil.emptyList());
        }
        EntityQueryable<T1Proxy, T1> select = entityQueryable.cloneQueryable().limit(offset, realTake).select(t -> t);
        select.getSQLEntityExpressionBuilder().getExpressionContext().getRelationExtraMetadata().getRelationExtraColumnMap().clear();
        Map<RelationTableKey, ManyConfiguration> manyConfigurations = entityQueryable.getSQLEntityExpressionBuilder().getManyConfigurations();
        TableAvailable fromTable = entityQueryable.getSQLEntityExpressionBuilder().getFromTable().getEntityTable();
        if (manyConfigurations != null) {
            for (Map.Entry<RelationTableKey, ManyConfiguration> kv : manyConfigurations.entrySet()) {
                RelationTableKey key = kv.getKey();
                if (key.getTable() == fromTable && Objects.equals(key.getTable().getEntityMetadata(), fromTable.getEntityMetadata())) {
                    TableAvailable entityTable = select.getSQLEntityExpressionBuilder().getFromTable().getEntityTable();
                    select.getSQLEntityExpressionBuilder().putManyConfiguration(new DefaultRelationTableKey(entityTable,key.getProperty()), kv.getValue());
                }
            }
        }
        Map<RelationTableKey, SubQueryModeEnum> manyJoinConfigurationMaps = entityQueryable.getSQLEntityExpressionBuilder().getSubQueryToGroupJoinConfigurationMaps();
        if (manyJoinConfigurationMaps != null) {
            for (Map.Entry<RelationTableKey, SubQueryModeEnum> kv : manyJoinConfigurationMaps.entrySet()) {
                RelationTableKey key = kv.getKey();
                if (key.getTable() == fromTable&& Objects.equals(key.getTable().getEntityMetadata(),fromTable.getEntityMetadata())) {
                    TableAvailable entityTable = select.getSQLEntityExpressionBuilder().getFromTable().getEntityTable();
                    select.getSQLEntityExpressionBuilder().putSubQueryToGroupJoin(new DefaultRelationTableKey(entityTable,key.getProperty()), kv.getValue());
                }
            }
        }

        Query<TR> apply = selectExpression.apply(select);
        List<TR> list = apply.toList();
        return easyPageResultProvider.createPageResult(pageIndex, pageSize, total, list);
    }
}
