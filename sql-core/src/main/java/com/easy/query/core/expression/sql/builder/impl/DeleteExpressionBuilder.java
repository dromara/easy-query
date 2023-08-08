package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnEqualsPropertyPredicate;
import com.easy.query.core.expression.segment.impl.ColumnVersionPropertySegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityDeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @Description: 文件说明
 * @Date: 2023/3/4 16:32
 * @author xuejiaming
 */
public class DeleteExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityDeleteExpressionBuilder {
    protected final PredicateSegment where;
    protected final boolean expressionDelete;
    protected SQLBuilderSegment whereColumns;

    public DeleteExpressionBuilder(ExpressionContext sqlExpressionContext,Class<?> queryClass, boolean expressionDelete) {
        super(sqlExpressionContext,queryClass);
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
                if(entityMetadata.hasVersionColumn()){
                    VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                    String propertyName = versionMetadata.getPropertyName();
                    VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();

                    if(isExpression()){
                        Object version = getExpressionContext().getVersion();
                        if(Objects.nonNull(version)){
                            Object nextVersion = easyVersionStrategy.nextVersion(entityMetadata, propertyName, version);
                            sqlColumnSetter.set(propertyName, nextVersion);
                        }
                    }else{
                        setSQLSegmentBuilder.append(new ColumnVersionPropertySegmentImpl(table.getEntityTable(), versionMetadata.getPropertyName(),easyVersionStrategy,this.getRuntimeContext()));
                    }
                }
                return setSQLSegmentBuilder;
            }
        }
        return null;
    }

    protected PredicateSegment buildWherePredicateSegment(EntityTableExpressionBuilder table){
        EntityMetadata entityMetadata = table.getEntityMetadata();

        PredicateSegment wherePredicate =  getWhere();
        if(!expressionDelete){

            //如果没有指定where那么就使用主键作为更新条件只构建一次where
            if(!hasWhere()){
                if (hasWhereColumns()) {
                    for (SQLSegment sqlSegment : whereColumns.getSQLSegments()) {
                        if (!(sqlSegment instanceof SQLEntitySegment)) {
                            throw new EasyQueryException("where 表达式片段不是SQLEntitySegment");
                        }
                        SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnEqualsPropertyPredicate(table.getEntityTable(), sqlEntitySegment.getPropertyName(), this.getRuntimeContext()));
                        wherePredicate.addPredicateSegment(andPredicateSegment);
                    }
                }
                else {
                    //如果没有指定where那么就使用主键作为更新条件
                    Collection<String> keyProperties = entityMetadata.getKeyProperties();
                    if (keyProperties.isEmpty()) {
                        throw new EasyQueryException("entity:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + "  not found primary key properties");
                    }
                    for (String keyProperty : keyProperties) {
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnEqualsPropertyPredicate(table.getEntityTable(), keyProperty, this.getRuntimeContext()));
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
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        if (expressionDelete) {
            return toDeleteExpression();
        } else {
            return toEntityExpression();
        }
    }
    private EntityPredicateSQLExpression toDeleteExpression(){

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
            EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata, table.toExpression());
            updateSetSQLBuilderSegment.copyTo(easyUpdateSQLExpression.getSetColumns());
            where.copyTo(easyUpdateSQLExpression.getWhere());
            return easyUpdateSQLExpression;
        } else {
            if (expressionContext.isDeleteThrow()) {
                throw new EasyQueryInvalidOperationException("can't execute delete statement");
            }
            EntityDeleteSQLExpression easyDeleteSQLExpression = expressionFactory.createEasyDeleteSQLExpression(entitySQLExpressionMetadata, table.toExpression());
            PredicateSegment where = buildWherePredicateSegment(table);
            where.copyTo(easyDeleteSQLExpression.getWhere());
            return easyDeleteSQLExpression;
        }
    }

    private EntityPredicateSQLExpression toEntityExpression(){

        EntityTableExpressionBuilder table = getTables().get(0);
        PredicateSegment sqlWhere = buildWherePredicateSegment(table);

        UpdateSetSQLBuilderSegment updateSetSQLBuilderSegment = getUpdateSetSQLBuilderSegment(table);

        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        //逻辑删除
        if (updateSetSQLBuilderSegment != null) {
            EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata, table.toExpression());
            updateSetSQLBuilderSegment.copyTo(easyUpdateSQLExpression.getSetColumns());
            sqlWhere.copyTo(easyUpdateSQLExpression.getWhere());
            return easyUpdateSQLExpression;
        } else {
            if (expressionContext.isDeleteThrow()) {
                throw new EasyQueryInvalidOperationException("can't execute delete statement");
            }
            EntityDeleteSQLExpression easyDeleteSQLExpression = expressionFactory.createEasyDeleteSQLExpression(entitySQLExpressionMetadata, table.toExpression());

            sqlWhere.copyTo(easyDeleteSQLExpression.getWhere());
            return easyDeleteSQLExpression;
        }
    }

    @Override
    public EntityDeleteExpressionBuilder cloneEntityExpressionBuilder() {


        EntityDeleteExpressionBuilder deleteExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityDeleteExpressionBuilder(expressionContext,queryClass,expressionDelete);

        if(hasWhere()){
            getWhere().copyTo(deleteExpressionBuilder.getWhere());
        }
        if(hasWhereColumns()){
            getWhereColumns().copyTo(deleteExpressionBuilder.getWhereColumns());
        }
        for (EntityTableExpressionBuilder table : super.tables) {
            deleteExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        return deleteExpressionBuilder;
    }

}
