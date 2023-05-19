package com.easy.query.core.expression.executor.parser.descriptor.impl;

import com.easy.query.core.expression.executor.parser.descriptor.TableEntityParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2023/5/18 17:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableEntityParseDescriptorImpl implements TableEntityParseDescriptor {
    private final TableAvailable table;
    private final Set<TableAvailable> tables;
    private final List<Object> entities;

    public TableEntityParseDescriptorImpl(TableAvailable table, List<Object> entities){

        this.table = table;
        this.tables=Collections.singleton(table);
        this.entities = entities;
    }

    @Override
    public List<Object> getEntitiesOrNull(TableAvailable table) {
        if(Objects.equals(this.table,table)){
            return entities;
        }
        return null;
    }

    @Override
    public Set<TableAvailable> getTables() {
        return tables;
    }
}
