package com.easy.query.core.common.tree;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
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
    private TableAvailable[] tableArray;
    private EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private NavigateMetadata navigateMetadata;
    private AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder;
    private final List<QueryPathTreeNode> children;
    private final List<ConditionVal> conditions;

    private Filter filter;

    public QueryPathTreeNode(String fieldName) {
        this.fieldName = fieldName;
        this.children = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.tableArray = new TableAvailable[2];
    }

    public String getFieldName() {
        return fieldName;
    }

    public TableAvailable getTable(int tableIndex) {
        ensureCapacity(tableIndex);
        return tableArray[tableIndex];
    }
    public void setTable(int tableIndex, TableAvailable table) {
        ensureCapacity(tableIndex);
        this.tableArray[tableIndex] = table;
    }
    /**
     * 确保数组容量 >= tableIndex + 1
     */
    private void ensureCapacity(int tableIndex) {
        if (tableIndex >= tableArray.length) {
            // 扩容到原长度的 2 倍 或者 能够容纳 tableIndex 的大小
            int newLength = Math.max(tableArray.length * 2, tableIndex + 1);
            TableAvailable[] newArray = new TableAvailable[newLength];
            System.arraycopy(tableArray, 0, newArray, 0, tableArray.length);
            tableArray = newArray;
        }
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

    public void addCondition(ConditionVal condition) {
        conditions.add(condition);
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


    public void setNavigateMetadata(NavigateMetadata navigateMetadata) {
        this.navigateMetadata = navigateMetadata;
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

    public static class ConditionVal {
        public final EasyWhereCondition condition;
        public final Object val;
        public final Field field;
        public final List<FieldCondition> fieldConditions;

        public ConditionVal(EasyWhereCondition condition, Object val, Field field) {
            this.condition = condition;
            this.val = val;
            this.field = field;
            this.fieldConditions = new ArrayList<>();
        }

        public void addFieldCondition(String property, TableAvailable table) {
            fieldConditions.add(new FieldCondition(property, table));
        }

    }

    public static class FieldCondition {
        public final String property;
        public final TableAvailable table;

        public FieldCondition(String property, TableAvailable table) {
            this.property = property;
            this.table = table;
        }

    }
}
