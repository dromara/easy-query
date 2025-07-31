package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.context.EmptyQueryRuntimeContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * create time 2024/6/8 20:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyEntityExpressionBuilder implements EntityExpressionBuilder{
    public static final EmptyEntityExpressionBuilder DEFAULT=new EmptyEntityExpressionBuilder();
    @Override
    public Class<?> getQueryClass() {
        return null;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return null;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return EmptyQueryRuntimeContext.DEFAULT;
    }

    @Override
    public void addSQLEntityTableExpression(EntityTableExpressionBuilder tableExpression) {

    }

    @Override
    public EntityTableExpressionBuilder addRelationEntityTableExpression(RelationTableKey relationTableKey, Function<RelationTableKey, EntityTableExpressionBuilder> tableExpressionSupplier) {
        return null;
    }

    @Override
    public boolean hasManyJoinTable(RelationTableKey relationTableKey) {
        return false;
    }

    @Override
    public ManyConfiguration putManyConfiguration(RelationTableKey relationTableKey, ManyConfiguration manyConfiguration) {
        return null;
    }

    @Override
    public ManyConfiguration getManyConfiguration(RelationTableKey relationTableKey) {
        return null;
    }

    @Override
    public Map<RelationTableKey, ManyConfiguration> getManyConfigurations() {
        return null;
    }

    @Override
    public void putSubQueryToGroupJoinJoin(RelationTableKey relationTableKey, SubQueryModeEnum subQueryMode) {

    }

    @Override
    public SubQueryModeEnum getSubQueryToGroupJoin(RelationTableKey relationTableKey) {
        return null;
    }

    @Override
    public Map<RelationTableKey,SubQueryModeEnum> getManyJoinConfigurationMaps() {
        return null;
    }

    @Override
    public Map<RelationTableKey, EntityTableExpressionBuilder> getRelationTables() {
        return Collections.emptyMap();
    }

    @Override
    public boolean hasRelationTables() {
        return false;
    }

    @Override
    public List<EntityTableExpressionBuilder> getTables() {
        return Collections.emptyList();
    }

    @Override
    public void setLogicDelete(boolean logicDelete) {

    }

    @Override
    public EntitySQLExpression toExpression() {
        return null;
    }

    @Override
    public EntityExpressionBuilder cloneEntityExpressionBuilder() {
        return null;
    }
}
