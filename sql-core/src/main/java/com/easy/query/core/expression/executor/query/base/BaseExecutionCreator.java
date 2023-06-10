package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/4/25 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseExecutionCreator implements ExecutionCreator{
    protected ExecutionUnit createExecutionUnit(String dataSource, EntitySQLExpression expression, List<Object> entities, boolean fillAutoIncrement, SQLRewriteUnit sqlRewriteUnit){
        SQLRouteUnit sqlUnit = createSQLUnit(expression, entities, fillAutoIncrement,sqlRewriteUnit);
        return createExecutionUnit(dataSource, sqlUnit);
    }
    protected SQLRouteUnit createSQLUnit(EntitySQLExpression expression, List<Object> entities, boolean fillAutoIncrement,SQLRewriteUnit sqlRewriteUnit){
        return new SQLRouteUnit(expression,entities, fillAutoIncrement,sqlRewriteUnit);
    }
    protected ExecutionUnit createExecutionUnit(String dataSource, SQLRouteUnit sqlUnit){
        return new  ExecutionUnit(dataSource, sqlUnit);
    }
    protected ExecutionContext createExecutionContext(List<ExecutionUnit> executionUnits){
        return new ExecutionContext(executionUnits,sequenceQuery(),isCrossTable(),isCrossDataSource(),isReverseMerge(),mergeBehavior());
    }


    protected int mergeBehavior(){
        return MergeBehaviorEnum.DEFAULT.getCode();
    }
    protected boolean sequenceQuery(){
        return false;
    }
    protected boolean isCrossTable(){
        return false;
    }
    protected boolean isCrossDataSource(){
        return false;
    }
    protected boolean isReverseMerge(){
        return false;
    }
    @Override
    public ExecutionContext create() {
        List<ExecutionUnit> executionUnits = createExecutionUnits();
        return createExecutionContext(executionUnits);
    }
    protected abstract List<ExecutionUnit> createExecutionUnits();


    protected boolean useEntityBatch(){
        return false;
    }

    /**
     * 将独立的sql进行批处理执行
     * @param executionUnits
     * @return
     */
    protected List<ExecutionUnit> createBatchExecutionUnits(List<ExecutionUnit> executionUnits){
        boolean useBatch = useEntityBatch();
        if(useBatch){
            List<ExecutionUnit> batchExecutionUnits = new ArrayList<>(executionUnits.size());
            HashMap<String/*data source name*/, Map<String/*sql*/,ExecutionUnit>> lastUnitMap = new HashMap<>();
            for (ExecutionUnit executionUnit : executionUnits) {
                String dataSourceName = executionUnit.getDataSourceName();
                SQLRouteUnit sqlRouteUnit = executionUnit.getSQLRouteUnit();
                String sql = sqlRouteUnit.getSQL();
                Map<String, ExecutionUnit> sqlExecutionUnitMap = lastUnitMap.computeIfAbsent(dataSourceName,o->new HashMap<>());
                ExecutionUnit lastUnit = sqlExecutionUnitMap.get(sql);
                if(lastUnit==null||!lastUnit.isSame(executionUnit)){
                   List<Object> entities = new ArrayList<>();
                    entities.addAll(sqlRouteUnit.getEntities());
                    //不使用下面的方法是因为下面的方法会从1开始扩容
                    //ArrayList<Object> entities = new ArrayList<>(sqlRouteUnit.getEntities());
                    lastUnit=new ExecutionUnit(dataSourceName,new SQLRouteUnit(sql,sqlRouteUnit.getParameters(),entities,sqlRouteUnit.isFillAutoIncrement()));
                    batchExecutionUnits.add(lastUnit);
                    sqlExecutionUnitMap.put(sql,lastUnit);
                }else{
                    List<Object> entities = executionUnit.getSQLRouteUnit().getEntities();
                    lastUnit.getSQLRouteUnit().getEntities().addAll(entities);
                }
            }
            lastUnitMap.clear();
            return batchExecutionUnits;
        }
        return executionUnits;
    }
}
