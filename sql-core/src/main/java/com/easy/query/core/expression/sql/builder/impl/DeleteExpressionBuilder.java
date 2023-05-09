package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnVersionPropertyPredicate;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyEntityPredicateSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSqlExpression;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.EasyUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * @FileName: EasySqlDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:32
 * @author xuejiaming
 */
public class DeleteExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityDeleteExpressionBuilder {
    protected final PredicateSegment where;
    protected final boolean expressionDelete;
    protected SqlBuilderSegment whereColumns;

    public DeleteExpressionBuilder(ExpressionContext sqlExpressionContext, boolean expressionDelete) {
        super(sqlExpressionContext);
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
    public SqlBuilderSegment getWhereColumns() {
        if (whereColumns == null) {
            whereColumns = new ProjectSqlBuilderSegmentImpl();
        }
        return whereColumns;
    }

    @Override
    public boolean hasWhereColumns() {
        return whereColumns != null && whereColumns.isNotEmpty();
    }




    private UpdateSetSqlBuilderSegment getUpdateSetSqlBuilderSegment(EntityTableExpressionBuilder table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        boolean useLogicDelete = entityMetadata.enableLogicDelete() && sqlExpressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        if (useLogicDelete) {
            SqlExpression<SqlColumnSetter<Object>> logicDeletedSqlExpression = table.getLogicDeletedSqlExpression();
            if (logicDeletedSqlExpression != null) {
                UpdateSetSqlBuilderSegment setSqlSegmentBuilder = new UpdateSetSqlBuilderSegment();
                EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
                SqlColumnSetter<Object> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetter(table.getIndex(), this, setSqlSegmentBuilder);
                logicDeletedSqlExpression.apply(sqlColumnSetter);//获取set的值
                //todo 非表达式添加行版本信息
                if(entityMetadata.hasVersionColumn()){
                    VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                    String propertyName = versionMetadata.getPropertyName();
                    EasyVersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();

                    if(!isExpression()){
                        setSqlSegmentBuilder.append(new ColumnVersionPropertyPredicate(table.getEntityTable(), versionMetadata.getPropertyName(),easyVersionStrategy,this.getRuntimeContext()));
                    }else{
                        Object version = getExpressionContext().getVersion();
                        if(Objects.nonNull(version)){
                            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                            FastBean fastBean = EasyUtil.getFastBean(entityMetadata.getEntityClass());
                            Object nextVersion = easyVersionStrategy.nextVersion(entityMetadata, propertyName, version);
                            sqlColumnSetter.set(fastBean.getBeanGetter(columnMetadata.getProperty()),nextVersion);
                        }
                    }
                }
                return setSqlSegmentBuilder;
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
                    for (SqlSegment sqlSegment : whereColumns.getSqlSegments()) {
                        if (!(sqlSegment instanceof SqlEntitySegment)) {
                            throw new EasyQueryException("where 表达式片段不是SqlEntitySegment");
                        }
                        SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table.getEntityTable(), sqlEntitySegment.getPropertyName(), this.getRuntimeContext()));
                        wherePredicate.addPredicateSegment(andPredicateSegment);
                    }
                }
                else {
                    //如果没有指定where那么就使用主键作为更新条件
                    Collection<String> keyProperties = entityMetadata.getKeyProperties();
                    if (keyProperties.isEmpty()) {
                        throw new EasyQueryException("entity:" + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + "  not found primary key properties");
                    }
                    for (String keyProperty : keyProperties) {
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table.getEntityTable(), keyProperty, this.getRuntimeContext()));
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
    public EasyEntityPredicateSqlExpression toExpression() {

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
    private EasyEntityPredicateSqlExpression  toDeleteExpression(){

        if (!hasWhere()) {
            throw new EasyQueryException("'DELETE' statement without 'WHERE' clears all data in the table");
        }
        if (getTables().isEmpty()) {
            throw new EasyQueryException("not table found for delete");
        }

        EntityTableExpressionBuilder table = getTables().get(0);
        UpdateSetSqlBuilderSegment updateSetSqlBuilderSegment = getUpdateSetSqlBuilderSegment(table);
        EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        //逻辑删除
        if (updateSetSqlBuilderSegment != null) {
            PredicateSegment where = buildWherePredicateSegment(table);
            EasyUpdateSqlExpression easyUpdateSqlExpression = expressionFactory.createEasyUpdateSqlExpression(runtimeContext, table.toExpression(),sqlExpressionContext.getExecuteMethod());
            updateSetSqlBuilderSegment.copyTo(easyUpdateSqlExpression.getSetColumns());
            where.copyTo(easyUpdateSqlExpression.getWhere());
            return easyUpdateSqlExpression;
        } else {
            if (sqlExpressionContext.isDeleteThrow()) {
                throw new EasyQueryInvalidOperationException("can't execute delete statement");
            }
            EasyDeleteSqlExpression easyDeleteSqlExpression = expressionFactory.createEasyDeleteSqlExpression(runtimeContext, table.toExpression(),sqlExpressionContext.getExecuteMethod());
            PredicateSegment where = buildWherePredicateSegment(table);
            where.copyTo(easyDeleteSqlExpression.getWhere());
            return easyDeleteSqlExpression;
        }
    }

    private EasyEntityPredicateSqlExpression toEntityExpression(){

        EntityTableExpressionBuilder table = getTables().get(0);
        PredicateSegment sqlWhere = buildWherePredicateSegment(table);

        UpdateSetSqlBuilderSegment updateSetSqlBuilderSegment = getUpdateSetSqlBuilderSegment(table);

        EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        //逻辑删除
        if (updateSetSqlBuilderSegment != null) {
            EasyUpdateSqlExpression easyUpdateSqlExpression = expressionFactory.createEasyUpdateSqlExpression(runtimeContext, table.toExpression(),sqlExpressionContext.getExecuteMethod());
            updateSetSqlBuilderSegment.copyTo(easyUpdateSqlExpression.getSetColumns());
            sqlWhere.copyTo(easyUpdateSqlExpression.getWhere());
            return easyUpdateSqlExpression;
        } else {
            if (sqlExpressionContext.isDeleteThrow()) {
                throw new EasyQueryInvalidOperationException("can't execute delete statement");
            }
            EasyDeleteSqlExpression easyDeleteSqlExpression = expressionFactory.createEasyDeleteSqlExpression(runtimeContext, table.toExpression(),sqlExpressionContext.getExecuteMethod());

            sqlWhere.copyTo(easyDeleteSqlExpression.getWhere());
            return easyDeleteSqlExpression;
        }
    }

    @Override
    public EntityDeleteExpressionBuilder cloneEntityExpressionBuilder() {

        ExpressionContext sqlExpressionContext = getExpressionContext();
        DeleteExpressionBuilder deleteExpressionBuilder = new DeleteExpressionBuilder(sqlExpressionContext,expressionDelete);

        if(hasWhere()){
            getWhere().copyTo(deleteExpressionBuilder.getWhere());
        }
        if(hasWhereColumns()){
            getWhereColumns().copyTo(deleteExpressionBuilder.getWhereColumns());
        }
        for (EntityTableExpressionBuilder table : super.tables) {
            deleteExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
        }
        return deleteExpressionBuilder;
    }

}
