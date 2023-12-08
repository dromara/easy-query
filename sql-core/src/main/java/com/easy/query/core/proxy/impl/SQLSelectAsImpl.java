package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.function.Consumer;

/**
 * create time 2023/12/1 23:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectAsImpl extends SQLSelectImpl implements SQLSelectAsExpression {

    private final Consumer<AsSelector> asSelectConsumer;
    private final Consumer<GroupSelector> groupSelectorConsumer;
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final  String property;

    public SQLSelectAsImpl(Consumer<Selector> selectorConsumer, Consumer<AsSelector> asSelectConsumer, Consumer<GroupSelector> groupSelectorConsumer) {
        this(selectorConsumer,asSelectConsumer,groupSelectorConsumer,null,null,null);
    }

    public SQLSelectAsImpl(Consumer<Selector> selectorConsumer, Consumer<AsSelector> asSelectConsumer, Consumer<GroupSelector> groupSelectorConsumer, EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(selectorConsumer);
        this.asSelectConsumer = asSelectConsumer;
        this.groupSelectorConsumer = groupSelectorConsumer;
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
    }

    @Override
    public void accept(GroupSelector s) {
        groupSelectorConsumer.accept(s);
    }

    @Override
    public void accept(AsSelector s) {
        asSelectConsumer.accept(s);
    }


    @Override
    public String getValue() {
        if(property==null){
            throw new UnsupportedOperationException();
        }
        return property;
    }


    @Override
    public SQLSelectAsExpression alias(TablePropColumn propColumn) {
        return alias(propColumn.getValue());
    }

    @Override
    public SQLSelectAsExpression alias(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            throw new UnsupportedOperationException();
        }, s -> {
            s.columnAs(this.getTable(), this.getValue(), propertyAlias);
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    public TableAvailable getTable() {
        if(table==null){
            throw new UnsupportedOperationException();
        }
        return table;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
}
