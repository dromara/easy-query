package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnEqualsPropertyPredicate;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityDeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyColumnSegmentUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/4 16:32
 */
public class DeleteExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityDeleteExpressionBuilder {
    protected final PredicateSegment where;
    protected final boolean expressionDelete;
    protected SQLBuilderSegment whereColumns;

    public DeleteExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> queryClass, boolean expressionDelete) {
        super(sqlExpressionContext, queryClass);
        this.expressionDelete = expressionDelete;
        this.where = new AndPredicateSegment(true);
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }

    @Override
    public boolean hasWhere() {
        return where.isNotEmpty();
    }

    @Override
    public SQLBuilderSegment getWhereColumns() {
        if (whereColumns == null) {
            whereColumns = new ProjectSQLBuilderSegmentImpl();
        }
        return whereColumns;
    }

    @Override
    public boolean hasWhereColumns() {
        return whereColumns != null && whereColumns.isNotEmpty();
    }


    private UpdateSetSQLBuilderSegment getUpdateSetSQLBuilderSegment(EntityTableExpressionBuilder table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        boolean useLogicDelete = entityMetadata.enableLogicDelete() && expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        if (useLogicDelete) {
            SQLExpression1<ColumnSetter<Object>> logicDeletedSQLExpression = table.getLogicDeletedSQLExpression();
            if (logicDeletedSQLExpression != null) {
                UpdateSetSQLBuilderSegment setSQLSegmentBuilder = new UpdateSetSQLBuilderSegment();
                SQLExpressionInvokeFactory easyQueryLambdaFactory = getRuntimeContext().getSQLExpressionInvokeFactory();
                ColumnSetter<Object> sqlColumnSetter = easyQueryLambdaFactory.createColumnSetter(table.getEntityTable(), this, setSQLSegmentBuilder);
                logicDeletedSQLExpression.apply(sqlColumnSetter);//获取set的值
                //todo 非表达式添加行版本信息
                if (entityMetadata.hasVersionColumn()) {
                    VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                    String propertyName = versionMetadata.getPropertyName();
                    VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();

                    if (isExpression()) {
                        Object version = getExpressionContext().getVersion();
                        if (Objects.nonNull(version)) {
                            Object nextVersion = easyVersionStrategy.nextVersion(entityMetadata, propertyName, version);
                            sqlColumnSetter.set(propertyName, nextVersion);
                        }
                    } else {
                        InsertUpdateSetColumnSQLSegment updateColumnSegment = sqlSegmentFactory.createUpdateColumnSegment(table.getEntityTable(), versionMetadata.getPropertyName(), getExpressionContext(), easyVersionStrategy);
                        setSQLSegmentBuilder.append(updateColumnSegment);
                    }
                }
                return setSQLSegmentBuilder;
            }
        }
        return null;
    }

    protected PredicateSegment buildWherePredicateSegment(EntityTableExpressionBuilder table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();

        PredicateSegment wherePredicate = getWhere();
        if (!expressionDelete) {

            //如果没有指定where那么就使用主键作为更新条件只构建一次where
            if (!hasWhere()) {
                if (hasWhereColumns()) {
                    for (SQLSegment sqlSegment : whereColumns.getSQLSegments()) {
                        if (!(sqlSegment instanceof SQLEntitySegment)) {
                            throw new EasyQueryException("where 表达式片段不是SQLEntitySegment");
                        }
                        SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                        ColumnMetadata columnMetadata = table.getEntityTable().getEntityMetadata().getColumnNotNull(sqlEntitySegment.getPropertyName());
                        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table.getEntityTable(), columnMetadata, this.getExpressionContext());
                        ColumnValue2Segment columnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(table.getEntityTable(), columnMetadata, this.getExpressionContext(), null);
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnEqualsPropertyPredicate(column2Segment, columnValue2Segment));
                        wherePredicate.addPredicateSegment(andPredicateSegment);
                    }
                } else {
                    //如果没有指定where那么就使用主键作为更新条件
                    Collection<String> keyProperties = entityMetadata.getKeyProperties();
                    if (keyProperties.isEmpty()) {
                        throw new EasyQueryException("entity:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + "  not found primary key properties");
                    }
                    for (String keyProperty : keyProperties) {
                        ColumnMetadata columnMetadata = table.getEntityTable().getEntityMetadata().getColumnNotNull(keyProperty);
                        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table.getEntityTable(), columnMetadata, this.getExpressionContext());
                        ColumnValue2Segment columnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(table.getEntityTable(), columnMetadata, this.getExpressionContext(), null);
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnEqualsPropertyPredicate(column2Segment, columnValue2Segment));
                        wherePredicate.addPredicateSegment(andPredicateSegment);
                    }
                }
            }
        }
        if (wherePredicate.isEmpty()) {
            throw new EasyQueryException("'DELETE' statement without 'WHERE'");
        }
        return sqlPredicateFilter(table, wherePredicate);

    }

    @Override
    public boolean isExpression() {
        return expressionDelete;
    }

    @Override
    public EntityPredicateSQLExpression toExpression() {

        int tableCount = getTables().size();
        if (tableCount == 0) {
            //未找到查询表信息
            throw new EasyQueryException("not found any table in delete expression build.");
        }
        if (tableCount > 1) {
            //找到多张表信息
            throw new EasyQueryInvalidOperationException("not support multi table in delete expression build.");
        }
        if (expressionDelete) {
            return toDeleteExpression();
        } else {
            return toEntityExpression();
        }
    }

    private EntityPredicateSQLExpression toDeleteExpression() {

        if (!hasWhere()) {
            throw new EasyQueryException("'DELETE' statement without 'WHERE' clears all data in the table");
        }
        if (getTables().isEmpty()) {
            throw new EasyQueryException("not table found for delete");
        }

        EntityTableExpressionBuilder table = getTables().get(0);
        UpdateSetSQLBuilderSegment updateSetSQLBuilderSegment = getUpdateSetSQLBuilderSegment(table);
        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        //逻辑删除
        if (updateSetSQLBuilderSegment != null) {
            PredicateSegment where = buildWherePredicateSegment(table);
            EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata,table.toExpression());

            addRelationTables(easyUpdateSQLExpression);
            updateSetSQLBuilderSegment.copyTo(easyUpdateSQLExpression.getSetColumns());
            where.copyTo(easyUpdateSQLExpression.getWhere());
            return easyUpdateSQLExpression;
        } else {
            if (expressionContext.isDeleteThrow()) {
                //无法执行删除命令,因为默认配置了不允许物理删除操作,如有需要物理删除请调用[.allowDeleteStatement(true)]
                throw new EasyQueryInvalidOperationException("The delete operation cannot be executed because physical deletion is not allowed by default configuration. If physical deletion is needed, please call [.allowDeleteStatement(true)].");
            }
            EntityDeleteSQLExpression easyDeleteSQLExpression = expressionFactory.createEasyDeleteSQLExpression(entitySQLExpressionMetadata, table.toExpression());

            addRelationTables(easyDeleteSQLExpression);
            PredicateSegment where = buildWherePredicateSegment(table);
            where.copyTo(easyDeleteSQLExpression.getWhere());
            return easyDeleteSQLExpression;
        }
    }

    private EntityPredicateSQLExpression toEntityExpression() {

        EntityTableExpressionBuilder table = getTables().get(0);
        PredicateSegment sqlWhere = buildWherePredicateSegment(table);

        UpdateSetSQLBuilderSegment updateSetSQLBuilderSegment = getUpdateSetSQLBuilderSegment(table);

        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        //逻辑删除
        if (updateSetSQLBuilderSegment != null) {
            EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata,table.toExpression());
            addRelationTables(easyUpdateSQLExpression);
            updateSetSQLBuilderSegment.copyTo(easyUpdateSQLExpression.getSetColumns());
            sqlWhere.copyTo(easyUpdateSQLExpression.getWhere());
            return easyUpdateSQLExpression;
        } else {
            if (expressionContext.isDeleteThrow()) {
                //无法执行删除命令,因为默认配置了不允许物理删除操作,如有需要物理删除请调用[.allowDeleteStatement(true)]
                throw new EasyQueryInvalidOperationException("The delete operation cannot be executed because physical deletion is not allowed by default configuration. If physical deletion is needed, please call [.allowDeleteStatement(true)].");
            }
            EntityDeleteSQLExpression easyDeleteSQLExpression = expressionFactory.createEasyDeleteSQLExpression(entitySQLExpressionMetadata,table.toExpression());
            addRelationTables(easyDeleteSQLExpression);
            sqlWhere.copyTo(easyDeleteSQLExpression.getWhere());
            return easyDeleteSQLExpression;
        }
    }

    @Override
    public EntityDeleteExpressionBuilder cloneEntityExpressionBuilder() {


        EntityDeleteExpressionBuilder deleteExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityDeleteExpressionBuilder(expressionContext, queryClass, expressionDelete);

        if (hasWhere()) {
            getWhere().copyTo(deleteExpressionBuilder.getWhere());
        }
        if (hasWhereColumns()) {
            getWhereColumns().copyTo(deleteExpressionBuilder.getWhereColumns());
        }
        for (EntityTableExpressionBuilder table : super.tables) {
            deleteExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        if(hasRelationTables()){
            for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> entry : relationTables.entrySet()) {
                deleteExpressionBuilder.getRelationTables().put(entry.getKey(), entry.getValue().copyEntityTableExpressionBuilder());
            }
        }
        if(super.manyConfigurationMaps!=null){
            for (Map.Entry<RelationTableKey, ManyConfiguration> manyJoinConfigurationEntry : super.manyConfigurationMaps.entrySet()) {
                deleteExpressionBuilder.putManyConfiguration(manyJoinConfigurationEntry.getKey(),manyJoinConfigurationEntry.getValue());
            }
        }
        if(super.manyJoinConfigurationSets!=null){
            for (RelationTableKey manyJoinConfigurationSet : super.manyJoinConfigurationSets) {
                deleteExpressionBuilder.addSubQueryToGroupJoinJoin(manyJoinConfigurationSet);
            }
        }

        return deleteExpressionBuilder;
    }


}
