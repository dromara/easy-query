package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/22 22:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateSQLExpressionImpl implements EntityUpdateSQLExpression {
    private static final Log log = LogFactory.getLog(UpdateSQLExpressionImpl.class);
    protected final SQLBuilderSegment setColumns;
    protected final PredicateSegment where;
    protected final List<EntityTableSQLExpression> tables = new ArrayList<>(3);
    protected final EntitySQLExpressionMetadata entitySQLExpressionMetadata;

    public UpdateSQLExpressionImpl(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        this.entitySQLExpressionMetadata = entitySQLExpressionMetadata;
        this.setColumns = new UpdateSetSQLBuilderSegment();
        this.where = new AndPredicateSegment(true);
    }

    @Override
    public SQLBuilderSegment getSetColumns() {
        return setColumns;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }


    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }

    @Override
    public EntitySQLExpressionMetadata getExpressionMetadata() {
        return entitySQLExpressionMetadata;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return entitySQLExpressionMetadata.getRuntimeContext();
    }


    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        if (EasySQLSegmentUtil.isEmpty(setColumns)) {
            log.warn("'UPDATE' statement without 'SET',not generate sql execute");
            return null;
        }
//        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
//        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        buildSQLTableJoinAndWhere(sql, tables, toSQLContext);
        sql.append(" SET ");
        sql.append(setColumns.toSQL(toSQLContext));
        sql.append(" WHERE ");
        sql.append(where.toSQL(toSQLContext));
        return sql.toString();
//        return "UPDATE " + buildSQLTableOrJoin(tables) + " SET " + setColumns.toSQL(toSQLContext) + " WHERE " +
//                where.toSQL(toSQLContext);
    }

    protected void buildSQLTableJoinAndWhere(StringBuilder sql, List<EntityTableSQLExpression> tables, ToSQLContext toSQLContext) {

        if (EasyCollectionUtil.isSingle(tables)) {
            EntityTableSQLExpression firstTable = tables.get(0);
            sql.append(firstTable.toSQL(toSQLContext));
        } else {
            Iterator<EntityTableSQLExpression> iterator = getTables().iterator();
            EntityTableSQLExpression firstTable = iterator.next();
            sql.append(firstTable.toSQL(toSQLContext));
            while (iterator.hasNext()) {
                EntityTableSQLExpression table = iterator.next();
                sql.append(table.toSQL(toSQLContext));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

                PredicateSegment on = table.getOn();
                if (on != null && on.isNotEmpty()) {
                    sql.append(" ON ").append(on.toSQL(toSQLContext));
                }
            }
        }
    }

    @Override
    public EntityUpdateSQLExpression cloneSQLExpression() {

        ExpressionFactory expressionFactory = entitySQLExpressionMetadata.getRuntimeContext().getExpressionFactory();
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata);
        for (EntityTableSQLExpression table : tables) {
            easyUpdateSQLExpression.getTables().add(table.cloneSQLExpression());
        }
        if (EasySQLSegmentUtil.isNotEmpty(where)) {
            where.copyTo(easyUpdateSQLExpression.getWhere());
        }
        if (EasySQLSegmentUtil.isNotEmpty(setColumns)) {
            setColumns.copyTo(easyUpdateSQLExpression.getSetColumns());
        }
        return easyUpdateSQLExpression;
    }


    public static void pgSQLUpdateJoinAndWhere(StringBuilder sql, List<EntityTableSQLExpression> tables, ToSQLContext toSQLContext, PredicateSegment where) {

        if (EasyCollectionUtil.isSingle(tables)) {
            EntityTableSQLExpression entityTableSQLExpression = tables.get(0);
            entityTableSQLExpression.setMultiTableType(MultiTableTypeEnum.FROM);
            sql.append(entityTableSQLExpression.toSQL(toSQLContext));

            sql.append(" WHERE ");

            sql.append(entityTableSQLExpression.getOn().toSQL(toSQLContext));
            if(EasySQLSegmentUtil.isNotEmpty(where)){
                sql.append(" AND ");
                sql.append(where.toSQL(toSQLContext));
            }

        } else {
            StringBuilder whereSQL=new StringBuilder();
            Iterator<EntityTableSQLExpression> iterator = tables.iterator();
            EntityTableSQLExpression firstTable = iterator.next();
            firstTable.setMultiTableType(MultiTableTypeEnum.FROM);
            sql.append(firstTable.toSQL(toSQLContext));
            whereSQL.append(firstTable.getOn().toSQL(toSQLContext));
            while (iterator.hasNext()) {
                EntityTableSQLExpression table = iterator.next();
                table.setMultiTableType(MultiTableTypeEnum.DTO);
                sql.append(table.toSQL(toSQLContext));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

                whereSQL.append(" AND ");
                whereSQL.append(table.getOn().toSQL(toSQLContext));
//                PredicateSegment on = table.getOn();
//                if (on != null && on.isNotEmpty()) {
//                    sql.append(" ON ").append(on.toSQL(toSQLContext));
//                }
            }

            sql.append(" WHERE ");

            sql.append(whereSQL);

            if(EasySQLSegmentUtil.isNotEmpty(where)){
                sql.append(" AND ");
                sql.append(where.toSQL(toSQLContext));
            }

        }
    }
}
