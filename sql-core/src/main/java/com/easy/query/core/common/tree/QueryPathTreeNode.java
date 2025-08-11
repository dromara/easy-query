package com.easy.query.core.common.tree;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.EntityTableAvailable;
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

    private Object val;
    private EasyWhereCondition condition;
    private Field field;
    public QueryPathTreeNode(String fieldName) {
        this.fieldName = fieldName;
        this.children = new ArrayList<>();
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



    public boolean hasChildren() {
        return EasyCollectionUtil.isNotEmpty(children);
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public EasyWhereCondition getCondition() {
        return condition;
    }

    public void setCondition(EasyWhereCondition condition) {
        this.condition = condition;
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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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
}
