package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.util.SqlExpressionUtil;
import com.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/25 21:35
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseEntityExecutionCreator extends BaseExecutionCreator{
    protected final String dataSource;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final List<Object> entities;

    public BaseEntityExecutionCreator(String dataSource, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities) {

        this.dataSource = dataSource;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.entities = entities;
    }
    @Override
    protected Collection<ExecutionUnit> createExecutionUnits() {
        //是否单个对象运行sql
        boolean isSingleEntityRun = SqlExpressionUtil.sqlExecuteStrategyNonDefault(entityExpressionBuilder.getExpressionContext());
        if (isSingleEntityRun||updateSingleEntityRun()) {
            return createSingleExecutionUnits();
        }

        return createMultiExecutionUnits();
    }
    private boolean updateSingleEntityRun(){
        if(entityExpressionBuilder instanceof EntityUpdateExpressionBuilder){
            EntityUpdateExpressionBuilder entityUpdateExpressionBuilder = (EntityUpdateExpressionBuilder) entityExpressionBuilder;
            TrackManager trackManager = entityUpdateExpressionBuilder.getRuntimeContext().getTrackManager();
            return trackManager.currentThreadTracking();
        }
        return false;
    }

    private Collection<ExecutionUnit> createSingleExecutionUnits() {
        List<ExecutionUnit> routeExecutionUnits = new ArrayList<>(entities.size());
        for (Object entity : entities) {
            EasyEntitySqlExpression expression = createEasySqlExpression(entity);
            ExecutionUnit executionUnit = createExecutionUnit(dataSource,expression, Collections.singletonList(entity), getFillAutoIncrement());
            //开启追踪的情况下update可能没有可以更新的数据那么就不会生成sql
            if(StringUtil.isNotBlank(executionUnit.getSqlRouteUnit().getSqlUnit().getSql())){
                routeExecutionUnits.add(executionUnit);
            }
        }
        return routeExecutionUnits;
    }
    private Collection<ExecutionUnit> createMultiExecutionUnits() {
        EasyEntitySqlExpression expression = entityExpressionBuilder.toExpression();
        ExecutionUnit executionUnit = createExecutionUnit(dataSource,expression, entities, getFillAutoIncrement());
        return Collections.singletonList(executionUnit);
    }
    protected abstract EasyEntitySqlExpression createEasySqlExpression(Object entity);
    protected abstract boolean getFillAutoIncrement();
}
