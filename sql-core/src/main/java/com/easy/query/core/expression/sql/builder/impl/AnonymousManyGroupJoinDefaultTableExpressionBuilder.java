package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectExpressionBuilder;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.AnonymousManyGroupJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasyMapUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * @Date: 2023/3/3 23:31
 */
public class AnonymousManyGroupJoinDefaultTableExpressionBuilder extends AnonymousDefaultTableExpressionBuilder implements AnonymousManyGroupJoinEntityTableExpressionBuilder {
    private final String[] defaultSelectKeys;

    private final Map<ManyGroupJoinProjectKey, Integer> projectAliasMap = new HashMap<>();

    public AnonymousManyGroupJoinDefaultTableExpressionBuilder(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder, String[] defaultSelectKeys) {
        super(entityTable, multiTableType, entityQueryExpressionBuilder);
        this.defaultSelectKeys = defaultSelectKeys;
    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {


        AnonymousManyGroupJoinEntityTableExpressionBuilder anonymousTableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createAnonymousManyGroupEntityTableExpressionBuilder(entityTable, multiTableType, entityQueryExpressionBuilder.cloneEntityExpressionBuilder(), defaultSelectKeys);
        if (on != null) {
            on.copyTo(anonymousTableExpressionBuilder.getOn());
        }
        anonymousTableExpressionBuilder.setTableLinkAs(this.linkAs);
        anonymousTableExpressionBuilder.getProjectAliasMap().putAll(this.projectAliasMap);
        return anonymousTableExpressionBuilder;
    }

    @Override
    public EntityTableSQLExpression toExpression() {

        EntityTableSQLExpression anonymousTableSQLExpression = runtimeContext.getExpressionFactory().createAnonymousEntityTableSQLExpression(entityTable, multiTableType, entityQueryExpressionBuilder.toExpression(), runtimeContext);
        if (EasySQLSegmentUtil.isNotEmpty(on)) {
            anonymousTableSQLExpression.setOn(on.clonePredicateSegment());
        }
        anonymousTableSQLExpression.setTableNameAs(tableNameAs);
        anonymousTableSQLExpression.setSchemaAs(schemaAs);
        anonymousTableSQLExpression.setLinkAs(linkAs);
        return anonymousTableSQLExpression;
    }

    @Override
    public Integer addManyGroupJoinProjectExpression(ManyGroupJoinProjectKey relationTableKey) {
        int projectIndex = entityQueryExpressionBuilder.getProjects().getSQLSegments().size();
        return projectAliasMap.putIfAbsent(relationTableKey, projectIndex+1);
    }

    @Override
    public String[] getDefaultSelectKeys() {
        return defaultSelectKeys;
    }

    @Override
    public Map<ManyGroupJoinProjectKey, Integer> getProjectAliasMap() {
        return projectAliasMap;
    }

    //    @Override
//    public AnonymousEntityTableExpressionBuilder getManyJoinEntityTableExpressionBuilder() {
//        EntityQueryExpressionBuilder entityQueryExpressionBuilder = this.getEntityQueryExpressionBuilder();
//        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
//        return (AnonymousEntityTableExpressionBuilder) table;
//    }
}
