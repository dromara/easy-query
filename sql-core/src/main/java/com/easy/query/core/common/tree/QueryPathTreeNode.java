package com.easy.query.core.common.tree;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.EntityTableAvailable;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/8/11 08:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryPathTreeNode {
    private final String fieldName;
    private  TableAvailable table;
    private  EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private  ColumnMetadata columnMetadata;
    private  NavigateMetadata navigateMetadata;
    private AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder;
    private final List<QueryPathTreeNode> children;
    private final List<ConditionVal> conditions;

    private Filter filter;
    public QueryPathTreeNode(String fieldName) {
        this.fieldName = fieldName;
        this.children = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }

    public String getFieldName() {
        return fieldName;
    }

    public TableAvailable getTable() {
        return table;
    }

    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    public NavigateMetadata getNavigateMetadata() {
        return navigateMetadata;
    }

    public List<QueryPathTreeNode> getChildren() {
        return children;
    }
    public void addChild(QueryPathTreeNode child) {
        children.add(child);
    }

    public List<ConditionVal> getConditions() {
        return conditions;
    }

    public void addCondition(EasyWhereCondition condition, Object val,Field field) {
        conditions.add(new ConditionVal(condition,val,field));
    }

    public boolean hasChildren() {
        return EasyCollectionUtil.isNotEmpty(children);
    }


    public EntityQueryExpressionBuilder getEntityQueryExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    public void setEntityQueryExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
    }

    public void setColumnMetadata(ColumnMetadata columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

    public void setNavigateMetadata(NavigateMetadata navigateMetadata) {
        this.navigateMetadata = navigateMetadata;
    }

    public void setTable(TableAvailable table) {
        this.table = table;
    }

    public AnonymousManyJoinEntityTableExpressionBuilder getAnonymousManyJoinEntityTableExpressionBuilder() {
        return anonymousManyJoinEntityTableExpressionBuilder;
    }

    public void setAnonymousManyJoinEntityTableExpressionBuilder(AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder) {
        this.anonymousManyJoinEntityTableExpressionBuilder = anonymousManyJoinEntityTableExpressionBuilder;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public static class ConditionVal{
        public final EasyWhereCondition condition;
        public final Object val;
        public final Field field;

        public ConditionVal(EasyWhereCondition condition, Object val,Field field){
            this.condition = condition;
            this.val = val;
            this.field = field;
        }

    }
}
